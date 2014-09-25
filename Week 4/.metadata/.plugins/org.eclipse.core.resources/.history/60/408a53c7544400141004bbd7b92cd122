package com.laurenelder.photolocationapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailsFragment extends Fragment {
	
	Context context;
	private detailsInterface detailActivity;

	// Set up interface
	public interface detailsInterface {

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		context = getActivity();

		if (activity instanceof detailsInterface) {
			detailActivity = (detailsInterface) activity;
		} else {
			throw new ClassCastException((activity.toString()) + 
					"Did not impliment detailInterface");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View detailsView = inflater.inflate(R.layout.activity_details, container);
		return detailsView;
	}

}
