package com.huynhchihieu.ejb.jndi.lookup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.inject.spi.SessionBeanType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * EJBCLIENT000025: No EJB receiver available for handling?
 * 
 * you are using a pure "ejb-client" approach, in which the InitialContext that
 * the EJB proxy was looked up from is no longer valid (i.e., it was closed or
 * gc'ed). This is what the "no EJB receiver" exception means. When the property
 * jboss.naming.client.ejb.context is true, it indicates that InitialContext
 * should be used for all EJB calls. Keeping a reference to the InitialContext
 * as mentioned earlier isn't a good idea, however.
 * 
 * The solution is to set jboss.naming.client.ejb.context to false, which
 * enforces EJB calls to be managed by the "remote-naming" framework. In order
 * for this to work, you need both jboss-ejb-client.properties and
 * jndi.properties files on your classpath (or configure programmatically). The
 * latter should contain the following properties:
 * 
 * 
 * https://stackoverflow.com/questions/21748172/ejbclient000025-no-ejb-receiver-available-for-handling
 * 
 * @author hieuhuynh
 *
 */
public class LookerUp {
	private Properties prop = new Properties();

	public LookerUp() throws IOException {
	}

	/**
	 * only lookup local session bean using JNDI
	 * 
	 * @param appName
	 * @param moduleName
	 * @param beanName
	 * @param interfaceFullQualifiedName
	 * @return
	 * @throws NamingException
	 */
	public Object findLocalSessionBean(String appName, String moduleName, String beanName, String interfaceFullQualifiedName)
			throws NamingException {
		final Context context = new InitialContext(prop);
		Object object = context.lookup("java:global/" + appName + "/" + moduleName + "/" + beanName + "!" + interfaceFullQualifiedName);
		context.close();
		return object;
	}

	/**
	 * 
	 * @param jndiName: depend on remote or local session bean
	 * @return
	 * @throws NamingException
	 */
	public Object findSessionBean(String jndiName) throws NamingException {
		final Context context = new InitialContext();
		Object object = context.lookup(jndiName);
		context.close();
		return object;
	}
}
