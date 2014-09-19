/* Name: Devin "Lauren" Elder
 * Date: 09/18/2014
 * Term: 1409
 * Project Name: Widget App
 * Assignment: MDF3 Week 3
 */

package com.laurenelder.widgetapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FormFragment extends Fragment {

	// Establish Variables
	Context context;
	TextView storeInput;
	TextView dateInput;
	TextView listInput;
	private formInterface formActivity;

	// Set up interface
	public interface formInterface {

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		context = getActivity();

		if (activity instanceof formInterface) {
			formActivity = (formInterface) activity;
		} else {
			throw new ClassCastException((activity.toString()) + 
					"Did not impliment formInterface");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View formView = inflater.inflate(R.layout.activity_form, container);

		// Initialize Variables
		storeInput = (TextView)formView.findViewById(R.id.storeInput);
		dateInput = (TextView)formView.findViewById(R.id.dateInput);
		listInput = (TextView)formView.findViewById(R.id.listInput);

		return formView;
	}

	// Retrieves data from UI elements to send back to FormActivity
	public ArrayList<String> getData() {
		if (!storeInput.getText().toString().matches("") &&
				!dateInput.getText().toString().matches("") &&
				!listInput.getText().toString().matches("")) {
			ArrayList<String> allData = new ArrayList<String>();
			allData.add("Store:");
			allData.add(storeInput.getText().toString());
			allData.add("Date:");
			allData.add(dateInput.getText().toString());
			allData.add("Grocery List:");
			allData.add(listInput.getText().toString());

			Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show();

			return allData;
		} else {
			Toast.makeText(context, "Please input information in all " +
					"fields before saving", Toast.LENGTH_SHORT).show();
			return null;
		}
	}
}
