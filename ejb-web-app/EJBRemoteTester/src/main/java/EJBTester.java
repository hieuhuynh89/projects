import javax.enterprise.inject.spi.SessionBeanType;
import javax.naming.NamingException;

import com.huynhchihieu.ejb.jndi.lookup.LookerUp;
import com.huynhchihieu.ejb.server.api.IRemotePlayedQuizzesCounter;

public class EJBTester {
	public static void main(String[] args) throws NamingException {
		String ejbsServerAddress = "127.0.0.1";
		int ejbsServerPort = 8080;
		String earName = "ejb-server-client-ear";
		String moduleName = "ejb-server-client-war";
		String deploymentDistinctName = "";
		String beanName = "PlayedQuizzesCounterBean";
		String interfaceQualifiedName = IRemotePlayedQuizzesCounter.class.getName();

		// No password required <= Component deployed in the same container
		LookerUp wildf9Lookerup = new LookerUp(ejbsServerAddress, ejbsServerPort);

		// We could instead use the following method by giving the exact JNDI name :
		// quizProxy = (IRemoteQuiz) wildf9Lookerup.findSessionBean("ejb:ejb-server-client-ear/ejb-server-client-war/QuizBean!com.huynhchihieu.ejb.server.api.IRemoteQuiz?stateful");
//		IRemotePlayedQuizzesCounter playedQuizzesCounterProxy = (IRemotePlayedQuizzesCounter) wildf9Lookerup.findRemoteSessionBean(SessionBeanType.STATEFUL, earName, moduleName,
//				deploymentDistinctName, beanName, interfaceQualifiedName);
		IRemotePlayedQuizzesCounter playedQuizzesCounterProxy = (IRemotePlayedQuizzesCounter) wildf9Lookerup
				.findSessionBean(
						"ejb:ejb-server-client-ear/ejb-server-client-war/PlayedQuizzesCounterBean!com.huynhchihieu.ejb.server.api.IRemotePlayedQuizzesCounter?stateful");
		System.out.println("The number of played quizzes is : "+playedQuizzesCounterProxy.getNumber());
	}
}
