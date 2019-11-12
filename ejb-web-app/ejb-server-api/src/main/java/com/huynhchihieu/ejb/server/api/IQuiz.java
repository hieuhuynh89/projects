package com.huynhchihieu.ejb.server.api;

/**
 * 
 * @author hieuhuynh
 *
 */
public interface IQuiz {
	void begin(String userName);
	String generateQuestionAndAnswer();
	boolean verifyAnswerAndReward(int result);
	void end();
	int getScore();
	String getPlayerName();
}
