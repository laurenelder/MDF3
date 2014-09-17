package com.laurenelder.widgetapp;

public class ListItems {
	
	public String title;
	public String information;
	
	public ListItems(String listTitle, String ListInformation) {
		this.title = listTitle;
		this.information = ListInformation;
	}
	
	public String toString () {
		return title;
	}
}
