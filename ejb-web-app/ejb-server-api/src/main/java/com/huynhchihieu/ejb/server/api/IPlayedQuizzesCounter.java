package com.huynhchihieu.ejb.server.api;

/**
 * 
 * @author hieuhuynh
 *
 */
public interface IPlayedQuizzesCounter {
	void increment();
	long getNumber();
}
