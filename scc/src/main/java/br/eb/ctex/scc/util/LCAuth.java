/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eb.ctex.scc.util;

import java.security.MessageDigest;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpSession;

import br.eb.ctex.scc.model.Usuario;
import sun.misc.BASE64Encoder;

/**
 *
 * @author ralfh e Marco
 */
public class LCAuth {

	private final static String LDAP_URI = "ldap://10.1.62.127:389";
	private final static String SEARCH_BASE = "ou=Usuarios,dc=ldap,dc=ctex";
	private final static String CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	private final static String SECURITY_CREDENTIALS = "Dem1More";
	private final static String SECURITY_PRINCIPAL = "admin";

	// define controles e configurações iniciais para busca no LDAP
	private static SearchControls getSearchControls() {
		SearchControls cons = new SearchControls();
		cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
//		String[] attrIDs = { "uid", "title", "givenname" };
		String[] attrIDs = { "uid", "userPassword", "title", "givenname" };
		cons.setReturningAttributes(attrIDs);
		return cons;
	}

	// configura e recupera contexto inicial
	private static DirContext getLdapContext() {
//	private static LdapContext getLdapContext() {
				DirContext ctx;
		//		LdapContext ctx;
		try {
			 Properties serviceEnv = new Properties();
			 serviceEnv.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
		     serviceEnv.put(Context.PROVIDER_URL, LDAP_URI);
		     serviceEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
		     serviceEnv.put(Context.SECURITY_PRINCIPAL, "cn=" + SECURITY_PRINCIPAL + ",dc=ldap,dc=ctex");
		     serviceEnv.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);
		     ctx = new InitialDirContext(serviceEnv);
		     
/*			Hashtable<String, String> env = new Hashtable<>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
			env.put(Context.SECURITY_AUTHENTICATION, "Simple");
			env.put(Context.SECURITY_PRINCIPAL, "cn=" + SECURITY_PRINCIPAL + ",dc=ldap,dc=ctex");
			env.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);
			env.put(Context.PROVIDER_URL, LDAP_URI);
			env.put(Context.REFERRAL, "follow");  
			ctx = new InitialLdapContext(env, null);
			System.out.println("---->Connection Successful.");  */
		     
		} catch (NamingException nex) {
			System.out.println(nex.getMessage());
			ctx = null;
		}
		return ctx;
	}

	// recupera usuário desejado no contexto inicial
	private static boolean getUserInfo(String userName, String password, DirContext ctx, SearchControls searchControls, HttpSession session) {
//	private static boolean getUserInfo(String userName, String password, LdapContext ctx, SearchControls searchControls, HttpSession session) {

		boolean response = false;
		String filter = "(uid=" + userName + ")";
//		String filter = "(&(uid=" + userName + ")" + "(givenname=" + "Marco Aurélio" + "))";
//		System.out.println("------>"+filter);
		Usuario user = null;
		String dn = null;
		try {
			NamingEnumeration<SearchResult> answer = ctx.search(SEARCH_BASE, filter, searchControls);
//			NamingEnumeration<SearchResult> answer = ctx.search("uid=emarco,ou=CT,ou=Coordenadorias,ou=Usuarios,dc=ldap,dc=ctex", filter, searchControls);
//			System.out.println("---->Busquei com o filtro");
/*			while(answer.hasMore())
            {
                SearchResult sr = (SearchResult) answer.next();
            	dn = sr.getNameInNamespace();
                Attributes atributos = sr.getAttributes();
                NamingEnumeration todosAtributos = atributos.getAll(); 
                while(todosAtributos.hasMore()) {
                    Attribute attrib = (Attribute) todosAtributos.next();
                    String nomeAtributo = attrib.getID();
                    System.out.println("Atributo:" + nomeAtributo);
                    for (NamingEnumeration e = attrib.getAll(); e.hasMore();) {
                        if(nomeAtributo.equals("userPassword"))
                        {
                            System.out.println("\tvalor:" + new String((byte[])e.next()));
                        }
                        else
                        {
                            System.out.println("\tvalor:" + e.next());
                        }
                    } 
                } 
            } */
//			System.out.println("---->DN = "+ dn);
//			response = checaSenha(dn, password);
//			response = checaSenha(userName, encryptLdapPassword("SHA", password));

			if (answer.hasMore()) {
				SearchResult sr = (SearchResult) answer.next();
            	dn = sr.getNameInNamespace();
				System.out.println("------> Entrei no answer.hasMore");
				Attributes attrs = sr.getAttributes();
				System.out.println(attrs.toString());
				user = new Usuario();
				user.setNome(attrs.get("givenname").get().toString());
				user.setPosto(attrs.get("title").get().toString());
				user.setLogin(attrs.get("uid").get().toString());
				session.setAttribute("usuarioLogado", user);
				response = checaSenha(dn, password);		
//				response = true;				
			} else {
				session.setAttribute("usuarioLogado", user);
			}  
		} catch (Exception ex) {
			session.setAttribute("usuarioLogado", user);

		}
		return response;
	}

	public static boolean autentica(String user, String password, HttpSession session) {
		DirContext ctx = getLdapContext();
//		LdapContext ctx = getLdapContext();
		SearchControls ctrl = getSearchControls();
		return getUserInfo(user, password, ctx, ctrl, session);
	}

	private static boolean checaSenha(String user, String password) throws Exception {
//		System.out.println("------> Estou no checaSenha user = "+user+" password= "+password);
		Properties serviceEnv = new Properties();
		serviceEnv.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
	    serviceEnv.put(Context.PROVIDER_URL, LDAP_URI);
	    serviceEnv.put(Context.SECURITY_PRINCIPAL, user);
	    serviceEnv.put(Context.SECURITY_CREDENTIALS, password);
	     
		
/*		Hashtable<String, String> env = new Hashtable<>();
//		env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
		env.put(Context.SECURITY_AUTHENTICATION, "Simple");
//		env.put(Context.SECURITY_PRINCIPAL, user);
		env.put(Context.SECURITY_PRINCIPAL, "cn=" + user + ",ou=Usuarios,dc=ldap,dc=ctex");
		env.put(Context.SECURITY_CREDENTIALS, password);
//		env.put(Context.PROVIDER_URL, LDAP_URI);
//		env.put(Context.REFERRAL, "follow");  */
/*		
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
		env.put(Context.PROVIDER_URL, LDAP_URI);
//		env.put(Context.SECURITY_PRINCIPAL, "cn=" + user + ",dc=ldap,dc=ctex");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, user);
		env.put(Context.SECURITY_CREDENTIALS, password);
		env.put(Context.REFERRAL, "follow");  */
		  try {
//			  DirContext authContext = new InitialDirContext(env);
//			  new InitialLdapContext(env, null);
			  new InitialDirContext(serviceEnv);
			  } catch (javax.naming.AuthenticationException e) {
				  
				  return false; 
			  }
		 
		return true;
	}
/*
	private static String encryptLdapPassword(String algorithm, String _password) {
        String sEncrypted = _password;
        if ((_password != null) && (_password.length() > 0)) {
            boolean bMD5 = algorithm.equalsIgnoreCase("MD5");
            boolean bSHA = algorithm.equalsIgnoreCase("SHA")
                    || algorithm.equalsIgnoreCase("SHA1")
                    || algorithm.equalsIgnoreCase("SHA-1");
            if (bSHA || bMD5) {
                String sAlgorithm = "MD5";
                if (bSHA) {
                    sAlgorithm = "SHA";
                }
                try {
                    MessageDigest md = MessageDigest.getInstance(sAlgorithm);
                    md.update(_password.getBytes("UTF-8"));
                    sEncrypted = "{" + sAlgorithm + "}" + (new BASE64Encoder()).encode(md.digest());
                } catch (Exception e) {
                    sEncrypted = null;
                    System.out.println("Erro na criptografia!!!!");
                }
            }
        }
        return sEncrypted;
    }

*/
}
