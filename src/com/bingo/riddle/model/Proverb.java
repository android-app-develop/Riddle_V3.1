package com.bingo.riddle.model;

public class Proverb {
	private int proverbId;
	private String proverbDes;
	private String proverbKey;
	private String proverbKind;
	private String proverbRemark;

	/**
	 * 
	 */
	public Proverb() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param proverbId
	 * @param proverbDes
	 * @param proverbKey
	 * @param proverbKind
	 * @param proverbRemark
	 */
	public Proverb(int proverbId, String proverbDes, String proverbKey,
			String proverbKind, String proverbRemark) {
		super();
		this.proverbId = proverbId;
		this.proverbDes = proverbDes;
		this.proverbKey = proverbKey;
		this.proverbKind = proverbKind;
		this.proverbRemark = proverbRemark;
	}

	/**
	 * @return the proverbId
	 */
	public int getProverbId() {
		return proverbId;
	}

	/**
	 * @param proverbId
	 *            the proverbId to set
	 */
	public void setProverbId(int proverbId) {
		this.proverbId = proverbId;
	}

	/**
	 * @return the proverbDes
	 */
	public String getProverbDes() {
		return proverbDes;
	}

	/**
	 * @param proverbDes
	 *            the proverbDes to set
	 */
	public void setProverbDes(String proverbDes) {
		this.proverbDes = proverbDes;
	}

	/**
	 * @return the proverbKey
	 */
	public String getProverbKey() {
		return proverbKey;
	}

	/**
	 * @param proverbKey
	 *            the proverbKey to set
	 */
	public void setProverbKey(String proverbKey) {
		this.proverbKey = proverbKey;
	}

	/**
	 * @return the proverbKind
	 */
	public String getProverbKind() {
		return proverbKind;
	}

	/**
	 * @param proverbKind
	 *            the proverbKind to set
	 */
	public void setProverbKind(String proverbKind) {
		this.proverbKind = proverbKind;
	}

	/**
	 * @return the proverbRemark
	 */
	public String getProverbRemark() {
		return proverbRemark;
	}

	/**
	 * @param proverbRemark
	 *            the proverbRemark to set
	 */
	public void setProverbRemark(String proverbRemark) {
		this.proverbRemark = proverbRemark;
	}

}
