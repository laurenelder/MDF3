/* Name: Devin "Lauren" Elder
 * Date: 09/25/2014
 * Term: 1409
 * Project Name: Photo Location App
 * Assignment: MDF3 Week 4
 */

package com.laurenelder.photolocationapp;

import java.io.File;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

	String tag = "DetailsFragment";
	ImageView imageView;
	TextView titleView;
	TextView infoView;
	TextView latView;
	TextView longView;

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

		// Instantiate Variables
		imageView = (ImageView)detailsView.findViewById(R.id.detailImage);
		titleView = (TextView)detailsView.findViewById(R.id.detailTitle);
		infoView = (TextView)detailsView.findViewById(R.id.detailInfo);
		latView = (TextView)detailsView.findViewById(R.id.detailLatitude);
		longView = (TextView)detailsView.findViewById(R.id.detailLongitude);

		return detailsView;
	}

	// updateDetails method updates the UI with marker data
	public void updateDetails(String title, String info, 
			String image, String latit, String longit) {
		File img = new  File(image);
		if(img.exists()){

			Bitmap imgBitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
			imageView.setImageBitmap(imgBitmap);
		}
		titleView.setText(title);
		infoView.setText(info);
		latView.setText("Latitude: " + latit);
		longView.setText("Longitude: " + longit);
	}
}
