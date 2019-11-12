package com.huynhchihieu.ejb.jndi.lookup;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * 
 * @author hieuhuynh
 *
 */
// The pattern of the JNDI Name of the EJB to lookup if Local View exposed :
// java:global/${moduleName}/${beanName}!${localInterfaceFullQualifiedName}
public class LookerUp {
	private Properties prop = new Properties();
	private String jndiPrefix;

	// Same WAR
	public LookerUp() {
		prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
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
