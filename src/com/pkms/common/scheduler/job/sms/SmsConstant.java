package com.pkms.common.scheduler.job.sms;


public class SmsConstant {

	private static boolean isRun = false;

	public static boolean isRun() {
		return isRun;
	}

	public static void setRun(boolean isRun) {
		SmsConstant.isRun = isRun;
	}
	
	
}
