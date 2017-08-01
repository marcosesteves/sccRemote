package br.eb.ctex.scc.util;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpSession;
import br.eb.ctex.scc.model.Usuario;

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
		String[] attrIDs = { "uid", "userPassword", "title", "givenname" };
		cons.setReturningAttributes(attrIDs);
		return cons;
	}

	// configura e recupera contexto inicial
	private static DirContext getLdapContext() {
		DirContext ctx;
		try {
			Properties serviceEnv = new Properties();
			serviceEnv.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
			serviceEnv.put(Context.PROVIDER_URL, LDAP_URI);
			serviceEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			serviceEnv.put(Context.SECURITY_PRINCIPAL, "cn=" + SECURITY_PRINCIPAL + ",dc=ldap,dc=ctex");
			serviceEnv.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);
			ctx = new InitialDirContext(serviceEnv);
		} catch (NamingException nex) {
			System.out.println(nex.getMessage());
			ctx = null;
		}
		return ctx;
	}

	// recupera usuário desejado no contexto inicial
	private static boolean getUserInfo(String userName, String password, DirContext ctx, SearchControls searchControls,
			HttpSession session) {

		boolean response = false;
		String filter = "(uid=" + userName + ")";
		Usuario user = null;
		String dn = null;
		try {
			NamingEnumeration<SearchResult> answer = ctx.search(SEARCH_BASE, filter, searchControls);
			if (answer.hasMore()) {
				SearchResult sr = (SearchResult) answer.next();
				dn = sr.getNameInNamespace();
				Attributes attrs = sr.getAttributes();
				user = new Usuario();
				user.setNome(attrs.get("givenname").get().toString());
				user.setPosto(attrs.get("title").get().toString());
				user.setLogin(attrs.get("uid").get().toString());
				session.setAttribute("usuarioLogado", user);
				response = checaSenha(dn, password);
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
		SearchControls ctrl = getSearchControls();
		return getUserInfo(user, password, ctx, ctrl, session);
	}

	private static boolean checaSenha(String user, String password) throws Exception {

		Properties serviceEnv = new Properties();
		serviceEnv.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
		serviceEnv.put(Context.PROVIDER_URL, LDAP_URI);
		serviceEnv.put(Context.SECURITY_PRINCIPAL, user);
		serviceEnv.put(Context.SECURITY_CREDENTIALS, password);
		try {
			new InitialDirContext(serviceEnv);
		} catch (javax.naming.AuthenticationException e) {

			return false;
		}

		return true;
	}
}
