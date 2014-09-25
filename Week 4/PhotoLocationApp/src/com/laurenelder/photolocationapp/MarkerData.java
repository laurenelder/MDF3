package com.laurenelder.photolocationapp;

public class MarkerData {

	public String title;
	public String info;
	public Double latitude;
	public Double longitude;
	
	public MarkerData(String thisTitle, String thisInfo, Double thisLatitude, 
			Double thisLongitude) {
		title = thisTitle;
		info = thisInfo;
		latitude = thisLatitude;
		longitude = thisLongitude;
	}
	
	public String toString() {
		return title;
	}
}