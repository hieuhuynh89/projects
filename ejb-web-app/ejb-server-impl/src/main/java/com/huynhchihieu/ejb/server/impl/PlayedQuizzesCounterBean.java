package com.huynhchihieu.ejb.server.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;

import com.huynhchihieu.ejb.server.api.ILocalPlayedQuizzesCounter;
import com.huynhchihieu.ejb.server.api.IPlayedQuizzesCounter;
import com.huynhchihieu.ejb.server.api.IRemotePlayedQuizzesCounter;

/**
 * 
 * @author hieuhuynh
 *
 */
@Singleton
@Remote(IRemotePlayedQuizzesCounter.class)
@Local(ILocalPlayedQuizzesCounter.class)
public class PlayedQuizzesCounterBean implements IPlayedQuizzesCounter {
	long playedQuizzesNumber = 0;

	@Override
	public void increment() {
		playedQuizzesNumber++;
	}

	@Override
	public long getNumber() {
		return playedQuizzesNumber;
	}
}
