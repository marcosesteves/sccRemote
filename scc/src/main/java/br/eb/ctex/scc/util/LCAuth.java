/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eb.ctex.scc.util;

import br.eb.ctex.scc.model.Usuario;
import java.util.Hashtable;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 *
 * @author ralfh
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
            String[] attrIDs = {"uid", "title", "givenname"};
            cons.setReturningAttributes(attrIDs);
            cons.setReturningAttributes(null);
            return cons;
        }        
        
        // configura e recupera contexto inicial
        private static LdapContext getLdapContext() {
            LdapContext ctx;
            try {
                Hashtable<String, String> env = new Hashtable<>();
                env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
                env.put(Context.SECURITY_AUTHENTICATION, "Simple");
                env.put(Context.SECURITY_PRINCIPAL, "cn="+SECURITY_PRINCIPAL+",dc=ldap,dc=ctex");
                env.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);
                env.put(Context.PROVIDER_URL, LDAP_URI);
                env.put(Context.REFERRAL, "follow");

                ctx = new InitialLdapContext(env, null);
            } catch (NamingException nex) {
                System.out.println(nex.getMessage());
                ctx = null;
            }
            return ctx;
        }        
        
        // recupera usuário desejado no contexto inicial
        private static boolean getUserInfo(String userName, LdapContext ctx, SearchControls searchControls) {

            FacesContext context = FacesContext.getCurrentInstance();
            boolean response = false;
            String filter = "(uid=" + userName + ")";
            
            try {                
                NamingEnumeration<SearchResult> answer = ctx.search(SEARCH_BASE, filter, searchControls); 
                if (answer.hasMore()) {                    

                    Attributes attrs = answer.next().getAttributes();
                    
                    Usuario user = new Usuario();
                    user.setNome(attrs.get("givenname").get().toString());
                    user.setPosto(attrs.get("title").get().toString());
                    user.setUsername(attrs.get("uid").get().toString());
                    user.setAdministrador(false);
                    
                    context.getExternalContext().getSessionMap().put("usuarioLogado",user);
                    response = true;
                } else {
                    context.getExternalContext().getSessionMap().put("usuarioLogado",null);
                }
            } catch (Exception ex) {
                context.getExternalContext().getSessionMap().put("usuarioLogado",null);
            }
            return response;
        }        

	public static boolean autentica (String user, String password) {            
            LdapContext ctx = getLdapContext();                        
            SearchControls ctrl = getSearchControls();            
            
            return getUserInfo(user, ctx, ctrl);
	}

	private static boolean checaSenha (String user, String password) throws Exception {
		Hashtable<String,String> env = new Hashtable <String,String>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, user);
		env.put(Context.SECURITY_CREDENTIALS, password);
/*
		try {
                    ldapContext(env);
		} catch (javax.naming.AuthenticationException e) {
                    return false;
		}
*/                
		return true;
	}

}
