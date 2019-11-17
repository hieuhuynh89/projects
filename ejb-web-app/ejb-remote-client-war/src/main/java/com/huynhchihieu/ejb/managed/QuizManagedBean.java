package com.huynhchihieu.ejb.managed;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.inject.spi.SessionBeanType;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;

import com.huynhchihieu.ejb.jndi.lookup.LookerUp;
import com.huynhchihieu.ejb.server.api.ILocalPlayedQuizzesCounter;
import com.huynhchihieu.ejb.server.api.ILocalQuiz;
import com.huynhchihieu.ejb.server.api.IRemotePlayedQuizzesCounter;
import com.huynhchihieu.ejb.server.api.IRemoteQuiz;

@ManagedBean(name = "quizManagedBean")
@SessionScoped
public class QuizManagedBean {
	private String playerName;
	private int score = 0;
	private String question = "";
	private int answer;

	// I'm not DI'ing it!
	 @EJB
	private IRemoteQuiz quizProxy;
	
	@EJB
	private IRemotePlayedQuizzesCounter playedQuizzesCounterProxy;

	@PostConstruct
	public void setup() {
		System.out.println("Setting up after creating the JSF managed bean.");
	}

	public String start() throws NamingException {

		playedQuizzesCounterProxy.increment();

		if (quizProxy != null) {
			quizProxy.end();
			quizProxy = null;
			System.out.println("Refreshing Quizz!");
		}

		// --- EJB Lookup in Same EAR (Such configuration could be externalized in a
		// file for example)
		String ejbsServerAddress = "127.0.0.1";
		int ejbsServerPort = 8080;
		String earName = "ejb-server-client-ear";
		String moduleName = "ejb-server-client-war";
		String deploymentDistinctName = "";
		String beanName = "QuizBean";
		String interfaceQualifiedName = IRemoteQuiz.class.getName();

		// No password required <= Component deployed in the same container
		LookerUp wildf9Lookerup = new LookerUp(ejbsServerAddress, ejbsServerPort);

		// We could instead use the following method by giving the exact JNDI name :
		// quizProxy = (IRemoteQuiz) wildf9Lookerup.findSessionBean("ejb:ejb-server-client-ear/ejb-server-client-war/QuizBean!com.huynhchihieu.ejb.server.api.IRemoteQuiz?stateful");
		quizProxy = (IRemoteQuiz) wildf9Lookerup.findRemoteSessionBean(SessionBeanType.STATEFUL, earName, moduleName,
				deploymentDistinctName, beanName, interfaceQualifiedName);

		quizProxy.begin(playerName);
		setQuestion(quizProxy.generateQuestionAndAnswer());

		return "quiz.xhtml";
	}

	public String verifyAnswer() {

		if (quizProxy == null) {
			System.out.println("quizProxy is null");
			return "index.xhtml";
		}

		boolean correct = quizProxy.verifyAnswerAndReward(answer);

		setScore(quizProxy.getScore());

		if (!correct) {
			System.out.println("Failed Quizz!");
			quizProxy.end();
			quizProxy = null;
			return "end.xhtml";

		} else {
			setQuestion(quizProxy.generateQuestionAndAnswer());
			return "quiz.xhtml";
		}

	}

	public long getPlayedQuizzesNumber() {
		return playedQuizzesCounterProxy.getNumber();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	@PreDestroy
	public void cleanUp() {
		System.out.println("Cleaning up before destroying the JSF managed bean.");
		quizProxy.end();
	}
}
