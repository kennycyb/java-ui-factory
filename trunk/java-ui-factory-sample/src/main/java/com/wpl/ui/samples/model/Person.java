package com.wpl.ui.samples.model;

import java.io.Serializable;

public class Person implements Serializable {

	private String mFirstName;
	private String mLastName;

	public String getmFirstName() {
		return mFirstName;
	}

	public void setmFirstName(String mFirstName) {
		this.mFirstName = mFirstName;
	}

	public String getmLastName() {
		return mLastName;
	}

	public void setmLastName(String mLastName) {
		this.mLastName = mLastName;
	}
}
