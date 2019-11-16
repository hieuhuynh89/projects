package com.huynhchihieu.ejb.jndi.lookup;

import java.util.Properties;

import javax.enterprise.inject.spi.SessionBeanType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * 
 * @author hieuhuynh
 *
 */
public class LookerUp {
	private Properties prop = new Properties();
	private String jndiPrefix;

	public LookerUp() {
	}

	public LookerUp(String address, int httpPort) {
		String protocol;
		protocol = "http-remoting";
		prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		prop.put(Context.PROVIDER_URL, protocol+"://"+address+":"+httpPort+"/");
		prop.put("jboss.naming.client.ejb.context", true);
		prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		jndiPrefix="ejb";
	}
	
	public Object findRemoteSessionBean(SessionBeanType beanType, String earName, String moduleName, String deploymentDistinctName, String beanName, String interfaceFullQualifiedName) throws NamingException{
		 
		String suffix = "";
		if(beanType.equals(SessionBeanType.STATEFUL)){
			suffix = "?stateful";
		}
 
		final Context context = new InitialContext(prop);
		Object object = context.lookup(jndiPrefix+":"+earName+"/"+moduleName+"/"+deploymentDistinctName+"/"+beanName+"!"+interfaceFullQualifiedName+suffix);
		context.close();
 
		return object;
	}

	public Object findLocalSessionBean(String moduleName, String beanName, String interfaceFullQualifiedName)
			throws NamingException {

		final Context context = new InitialContext(prop);
		Object object = context.lookup("java:global/" + moduleName + "/" + beanName + "!" + interfaceFullQualifiedName);
		context.close();

		return object;
	}

	public Object findSessionBean(String jndiName) throws NamingException {

		final Context context = new InitialContext(prop);
		Object object = context.lookup(jndiName);
		context.close();

		return object;
	}
}
