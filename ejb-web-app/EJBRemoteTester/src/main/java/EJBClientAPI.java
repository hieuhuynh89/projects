import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.huynhchihieu.ejb.server.api.IRemotePlayedQuizzesCounter;

//EJB invocations via JNDI - EJB client API 
public class EJBClientAPI {
	public static void main(String[] args) throws NamingException {
		final Context context = new InitialContext();
		IRemotePlayedQuizzesCounter playedQuizzesCounterProxy = (IRemotePlayedQuizzesCounter) context.lookup(
				"ejb:ejb-server-client-ear/ejb-server-client-war/PlayedQuizzesCounterBean!com.huynhchihieu.ejb.server.api.IRemotePlayedQuizzesCounter");
		System.out.println("The number of played quizzes is : " + playedQuizzesCounterProxy.getNumber());
	}
}
