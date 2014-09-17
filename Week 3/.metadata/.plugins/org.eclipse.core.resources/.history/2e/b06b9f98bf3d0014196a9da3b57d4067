package com.laurenelder.widgetapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData.Item;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FormFragment extends Fragment {
	
	// Establish Variables
	Context context;
	private formInterface formActivity;
	
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
		
		return formView;
	}
}
