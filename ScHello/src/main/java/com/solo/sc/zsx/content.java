package com.solo.sc.zsx;

public class content {
	private String IR_VERIFIED;
	private String IR_UID;
	private String IR_SCREEN_NAME;
	
	public content(String A1,String B1,String C1){
		this.IR_VERIFIED = A1;
		this.IR_UID = B1;
		this.IR_SCREEN_NAME = C1;
	}
	public String getIR_VERIFIED() {
		return IR_VERIFIED;
	}
	public void setIR_VERIFIED(String iR_VERIFIED) {
		IR_VERIFIED = iR_VERIFIED;
	}
	public String getIR_UID() {
		return IR_UID;
	}
	public void setIR_UID(String iR_UID) {
		IR_UID = iR_UID;
	}
	public String getIR_SCREEN_NAME() {
		return IR_SCREEN_NAME;
	}
	public void setIR_SCREEN_NAME(String iR_SCREEN_NAME) {
		IR_SCREEN_NAME = iR_SCREEN_NAME;
	}
	
}
