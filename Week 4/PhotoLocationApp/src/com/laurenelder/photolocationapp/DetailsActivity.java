package com.laurenelder.photolocationapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class DetailsActivity extends Activity implements DetailsFragment.detailsInterface {

	String tag = "DetailsActivity";
	Intent finishedIntent = new Intent();
	Intent thisIntent;
	FragmentManager detailsMgr;
	Fragment detailsFrag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_details);
		
		detailsMgr = getFragmentManager();
		detailsFrag = (DetailsFragment)detailsMgr.findFragmentById(R.id.detailsFrag);
		if(detailsFrag == null) {
			detailsFrag = new DetailsFragment();
		}
		
		thisIntent = this.getIntent();
		String title = thisIntent.getExtras().get("Title").toString();
		String info = thisIntent.getExtras().getString("Information").toString();
		String image = thisIntent.getExtras().getString("Image").toString();
		String latitude = thisIntent.getExtras().getString("Latitude").toString();
		String longitude = thisIntent.getExtras().getString("Longitude").toString();
		
		((DetailsFragment) detailsFrag).updateDetails(title, info, image, latitude, longitude);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		setResult(RESULT_OK, finishedIntent);
		finish();
	}

}
