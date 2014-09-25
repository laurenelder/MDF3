/* Name: Devin "Lauren" Elder
 * Date: 09/25/2014
 * Term: 1409
 * Project Name: Photo Location App
 * Assignment: MDF3 Week 4
 */

package com.laurenelder.photolocationapp;

public class Data {
	
	public String title;
	public String info;
	public String latitude;
	public String longitude;
	public String image;
	
	public Data(String thisTitle, String thisInfo, String thisImage, String thisLatitude, 
			String thisLongitude) {
		title = thisTitle;
		info = thisInfo;
		latitude = thisLatitude;
		longitude = thisLongitude;
		image = thisImage;
	}
	
	public String toString() {
		return title;
	}
}
