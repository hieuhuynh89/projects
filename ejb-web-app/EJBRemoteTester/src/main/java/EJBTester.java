import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.huynhchihieu.ejb.server.api.IRemotePlayedQuizzesCounter;

/**
 * 
 * This demo using Remote EJB invocations via JNDI. If you want to explore about
 * remote naming project you can find it out at
 * https://docs.jboss.org/author/display/AS72/Remote+EJB+invocations+via+JNDI+-+EJB+client+API+or+remote-naming+project
 * 
 * @author hieuhuynh
 *
 */
public class EJBTester {
	public static void main(String[] args) throws NamingException, IOException {
        final Context context = new InitialContext();
		IRemotePlayedQuizzesCounter playedQuizzesCounterProxy = (IRemotePlayedQuizzesCounter) context.lookup(
						"ejb:ejb-server-client-ear/ejb-server-client-war/PlayedQuizzesCounterBean!com.huynhchihieu.ejb.server.api.IRemotePlayedQuizzesCounter");
		System.out.println("The number of played quizzes is : " + playedQuizzesCounterProxy.getNumber());
	}
}
