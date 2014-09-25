/* Name: Devin "Lauren" Elder
 * Date: 09/25/2014
 * Term: 1409
 * Project Name: Photo Location App
 * Assignment: MDF3 Week 4
 */

package com.laurenelder.photolocationapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class FormFragment extends Fragment {

	// Establish Variables
	Context context;
	String tag = "FormFragment";
	ImageButton addImage;
	EditText titleInput;
	EditText infoInput;
	String imageInput = null;
	private formInterface formActivity;

	// Set up interface
	public interface formInterface {
		public void startCamera();
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
		titleInput = (EditText)formView.findViewById(R.id.formTitleInput);
		infoInput = (EditText)formView.findViewById(R.id.formInfoInput);
		addImage = (ImageButton)formView.findViewById(R.id.addImage);
		addImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Start Camera when image button is clicked
				formActivity.startCamera();
			}

		});

		return formView;
	}

	// sendData method is called to send UI data back to the FormActivity
	public ArrayList<String> sendData() {
		if (!titleInput.getText().toString().matches("") && 
				!infoInput.getText().toString().matches("") && 
				imageInput != null) {

			ArrayList<String> thisData = new ArrayList<String>();
			thisData.add(titleInput.getText().toString());
			thisData.add(infoInput.getText().toString());
			thisData.add(imageInput);
			return thisData;
		} else {
			Toast.makeText(context, "Please Enter Data in all Fields Before Saving", Toast.LENGTH_SHORT).show();
			return null;
		}
	}

	/* updateImageButton method is called after the camera returns an image.
	 * This method updates the image button to show the image that was taken.
	 */
	public void updateImageButton(Intent data, Uri iUri) {
		if (data == null) {
			addImage.setImageBitmap(BitmapFactory.decodeFile(iUri.getPath()));
			Log.i(tag, iUri.getPath().toString());
			imageInput = iUri.getPath().toString();
		} else {
			addImage.setImageBitmap((Bitmap)data.getParcelableExtra("data"));
		}
	}
}
