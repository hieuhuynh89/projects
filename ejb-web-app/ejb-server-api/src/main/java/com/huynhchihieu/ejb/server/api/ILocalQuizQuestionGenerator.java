package com.huynhchihieu.ejb.server.api;

import com.huynhchihieu.ejb.server.api.entities.LevelQuestion;
/**
 * 
 * @author hieuhuynh
 *
 */
public interface ILocalQuizQuestionGenerator {
	LevelQuestion generateQuestion(int level);
}
