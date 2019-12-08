import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.huynhchihieu.ejb.server.api.IRemotePlayedQuizzesCounter;

/**
 * EJB invocations via JNDI - remote-naming project
 *
 */
public class RemoteNamingClient {
	public static void main(String[] args) throws NamingException {
		Properties jndiProps = new Properties();
		jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		// This is an important property to set if you want to do EJB invocations via
		// the remote-naming project
		jndiProps.put("jboss.naming.client.ejb.context", false);
		Context context = new InitialContext(jndiProps);
		IRemotePlayedQuizzesCounter playedQuizzesCounterProxy = (IRemotePlayedQuizzesCounter) context.lookup(
				"ejb-server-client-ear/ejb-server-client-war/PlayedQuizzesCounterBean!com.huynhchihieu.ejb.server.api.IRemotePlayedQuizzesCounter");
		System.out.println("The number of played quizzes is : " + playedQuizzesCounterProxy.getNumber());
	}
}
