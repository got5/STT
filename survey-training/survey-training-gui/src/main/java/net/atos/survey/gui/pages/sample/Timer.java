package net.atos.survey.gui.pages.sample;

import java.text.DateFormat;
import java.util.Date;

public class Timer {

	private String currentTime ;

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public String getCurrentTime() {
		currentTime = DateFormat.getInstance().format(new Date());
		return currentTime;
	} 
}
