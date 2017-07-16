package com.bingo.riddle.model;

public class Idiom {
	private int idiomId;
	private String idiomDes;
	private String idiomKey;
	private String idiomKind;
	private String idiomRemark;

	/**
	 * 
	 */
	public Idiom() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param idiomId
	 * @param idiomDes
	 * @param idiomKey
	 * @param idiomKind
	 * @param idiomRemark
	 */
	public Idiom(int idiomId, String idiomDes, String idiomKey,
			String idiomKind, String idiomRemark) {
		super();
		this.idiomId = idiomId;
		this.idiomDes = idiomDes;
		this.idiomKey = idiomKey;
		this.idiomKind = idiomKind;
		this.idiomRemark = idiomRemark;
	}

	/**
	 * @return the idiomId
	 */
	public int getIdiomId() {
		return idiomId;
	}

	/**
	 * @param idiomId
	 *            the idiomId to set
	 */
	public void setIdiomId(int idiomId) {
		this.idiomId = idiomId;
	}

	/**
	 * @return the idiomDes
	 */
	public String getIdiomDes() {
		return idiomDes;
	}

	/**
	 * @param idiomDes
	 *            the idiomDes to set
	 */
	public void setIdiomDes(String idiomDes) {
		this.idiomDes = idiomDes;
	}

	/**
	 * @return the idiomKey
	 */
	public String getIdiomKey() {
		return idiomKey;
	}

	/**
	 * @param idiomKey
	 *            the idiomKey to set
	 */
	public void setIdiomKey(String idiomKey) {
		this.idiomKey = idiomKey;
	}

	/**
	 * @return the idiomKind
	 */
	public String getIdiomKind() {
		return idiomKind;
	}

	/**
	 * @param idiomKind
	 *            the idiomKind to set
	 */
	public void setIdiomKind(String idiomKind) {
		this.idiomKind = idiomKind;
	}

	/**
	 * @return the idiomRemark
	 */
	public String getIdiomRemark() {
		return idiomRemark;
	}

	/**
	 * @param idiomRemark
	 *            the idiomRemark to set
	 */
	public void setIdiomRemark(String idiomRemark) {
		this.idiomRemark = idiomRemark;
	}

}
