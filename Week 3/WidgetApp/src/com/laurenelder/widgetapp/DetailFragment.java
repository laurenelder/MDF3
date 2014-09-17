package com.laurenelder.widgetapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {
	
	// Establish Variables
	String tag = "DetailsFragment";
	Context context;
	TextView contentView;
	private detailInterface detailActivity;
	
	public interface detailInterface {
		public String getData();
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
		
		contentView = (TextView)detailView.findViewById(R.id.listDetails);
		contentView.setKeyListener(null);
		
//		updateUI();
//		Log.i(tag, detailActivity.getData().toString());
		
		return detailView;
	}
	
	public void updateUI(String content) {
/*		if (detailActivity.getData() != null) {
			contentView.setText(detailActivity.getData());
		}*/
		contentView.setText(content);
	}
}
