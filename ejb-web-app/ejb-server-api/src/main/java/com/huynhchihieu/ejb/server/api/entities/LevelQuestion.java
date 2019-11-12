package com.huynhchihieu.ejb.server.api.entities;

/**
 * 
 * @author hieuhuynh
 *
 */
public class LevelQuestion {
	private String question;
	private long expectedAswer;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public long getExpectedAswer() {
		return expectedAswer;
	}

	public void setExpectedAswer(long expectedAswer) {
		this.expectedAswer = expectedAswer;
	}
}
