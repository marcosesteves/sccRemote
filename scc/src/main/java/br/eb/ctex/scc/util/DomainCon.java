package br.eb.ctex.scc.util;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;  
  
public class DomainCon  
{  
  
public static void login(String usuario, String senha)   
{  
Hashtable<String,String> authEnv = new Hashtable<>(11);  
authEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
authEnv.put(Context.PROVIDER_URL,"ldap://10.1.62.127:389");
authEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
authEnv.put(Context.SECURITY_PRINCIPAL, usuario);  
authEnv.put(Context.SECURITY_CREDENTIALS, senha);  
  
  
try  
{  
    DirContext authContext = new InitialDirContext(authEnv);  
  
   System.out.println("Autenticado!");  
}   
catch (AuthenticationException authEx)   
{  
      
System.out.println("Erro na autenticação!");  
authEx.getCause().printStackTrace();  
}  
catch (NamingException namEx)   
{  
System.out.println("Problemas na conexão! ");
namEx.getCause().printStackTrace();  
}  
  
}  
  
}  