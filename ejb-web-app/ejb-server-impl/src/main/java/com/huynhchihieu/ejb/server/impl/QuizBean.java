package com.huynhchihieu.ejb.server.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import com.huynhchihieu.ejb.server.api.ILocalQuiz;
import com.huynhchihieu.ejb.server.api.ILocalQuizQuestionGenerator;
import com.huynhchihieu.ejb.server.api.IQuiz;
import com.huynhchihieu.ejb.server.api.IRemoteQuiz;
import com.huynhchihieu.ejb.server.api.entities.LevelQuestion;

/**
 * 
 * @author hieuhuynh
 *
 */
@Stateful
@Remote(IRemoteQuiz.class)
@Local(ILocalQuiz.class) //  Instead of declaring a local interface to expose a local view we could simply annotate by @LocalBean
public class QuizBean implements IQuiz {
	@EJB
	ILocalQuizQuestionGenerator levelQuestionGenerator;
	
	private String playerName;
	private String askedQuestion;
	private Long expectedAnswer;
	private int score=0;
	private int level=1;
 
	@Override
	public void begin(String playerName) {
		this.playerName = playerName;
	}
 
	@Override
	public String generateQuestionAndAnswer() {
		LevelQuestion levelQuestion = levelQuestionGenerator.generateQuestion(level);
		askedQuestion = levelQuestion.getQuestion(); 
		expectedAnswer = levelQuestion.getExpectedAswer();
		return askedQuestion;
	}
 
	@Override
	public boolean verifyAnswerAndReward(int result) {
		if(expectedAnswer == result){
			score++;
			level++;
			return true;
		}
		return false;
	}
 
	@Remove
	@Override
	public void end() {
		System.out.println("QuizBean instance will be removed..");
	}
 
	// Some getters to be able to congratulate the player all with showing him name, score,...
 
	@Override
	public String getPlayerName() {
		return playerName;
	}
 
	public String getAskedQuestion() {
		return askedQuestion;
	}
 
	@Override
	public int getScore() {
		return score;
	}
}
