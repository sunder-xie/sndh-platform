package com.nhry.model.stud;

import java.io.Serializable;

import com.nhry.model.basic.BaseQueryModel;

/**
 * @author zhaoxijun
 */
public class OrderStudQueryModel extends BaseQueryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String keyWord;

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	
}
