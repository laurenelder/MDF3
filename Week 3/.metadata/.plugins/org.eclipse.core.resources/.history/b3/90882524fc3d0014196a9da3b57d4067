package com.laurenelder.widgetapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailFragment extends Fragment {
	
	// Establish Variables
	Context context;
	private detailInterface detailActivity;
	
	public interface detailInterface {
		
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		context = getActivity();
		
		if (activity instanceof detailInterface) {
			detailActivity = (detailInterface) activity;
		} else {
			throw new ClassCastException((activity.toString()) + 
					"Did not impliment detailInterface");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View detailView = inflater.inflate(R.layout.activity_details, container);
		return detailView;
	}
}
