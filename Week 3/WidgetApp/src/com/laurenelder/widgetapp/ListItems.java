/* Name: Devin "Lauren" Elder
 * Date: 09/18/2014
 * Term: 1409
 * Project Name: Widget App
 * Assignment: MDF3 Week 3
 */

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
