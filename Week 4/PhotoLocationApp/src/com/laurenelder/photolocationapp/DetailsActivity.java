/* Name: Devin "Lauren" Elder
 * Date: 09/25/2014
 * Term: 1409
 * Project Name: Photo Location App
 * Assignment: MDF3 Week 4
 */

package com.laurenelder.photolocationapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

public class DetailsActivity extends Activity implements DetailsFragment.detailsInterface {

	// Establish Variables
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

		// Setup FragmentManager to call methods in DetailFragment
		detailsMgr = getFragmentManager();
		detailsFrag = (DetailsFragment)detailsMgr.findFragmentById(R.id.detailsFrag);
		if(detailsFrag == null) {
			detailsFrag = new DetailsFragment();
		}

		// Get marker data from Intent
		thisIntent = this.getIntent();
		String title = thisIntent.getExtras().get("Title").toString();
		String info = thisIntent.getExtras().getString("Information").toString();
		String image = thisIntent.getExtras().getString("Image").toString();
		String latitude = thisIntent.getExtras().getString("Latitude").toString();
		String longitude = thisIntent.getExtras().getString("Longitude").toString();

		// Update Detail View UI with marker data
		((DetailsFragment) detailsFrag).updateDetails(title, info, image, latitude, longitude);
	}

	// override android back button to set result and finish activity
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		setResult(RESULT_OK, finishedIntent);
		finish();
	}

}
