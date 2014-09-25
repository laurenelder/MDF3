/* Name: Devin "Lauren" Elder
 * Date: 09/25/2014
 * Term: 1409
 * Project Name: Photo Location App
 * Assignment: MDF3 Week 4
 */

package com.laurenelder.photolocationapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity implements MainFragment.mainInterface, LocationListener {

	// Establish Variables
	Context context;
	String tag = "MainActivity";
	FileManager fileManager;
	LocationManager locMgr;
	Location loc;
	FragmentManager fragMgr;
	Fragment mapFrag;
	List<Data> savedData = new ArrayList<Data>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		context = this;

		// Setup FragmentManager to call methods in MainFragment
		fragMgr = getFragmentManager();
		mapFrag = (MainFragment)fragMgr.findFragmentById(R.id.mainFrag);
		if(mapFrag == null) {
			mapFrag = new MainFragment();
		}

		// Setup LocationManager for GPS updates
		locMgr = (LocationManager)getSystemService(LOCATION_SERVICE);
		//        locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
		locMgr.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
		loc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		// Check for saved file. If exists, read and parse file
		fileManager = FileManager.getInstance();
		File checkForFile = getBaseContext().getFileStreamPath(getResources()
				.getString(R.string.data_file_name));
		if (checkForFile.exists()) {
			String fileContent = fileManager.readFromFile(context, getResources()
					.getString(R.string.data_file_name));
			if (fileContent != null) {
				parseData(fileContent.toString());
				Log.i(tag, fileContent.toString());
			}
		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		menu.findItem(R.id.action_item).setIcon(R.drawable.ic_action_new);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_item) {
			
			// formIntent opens the FormActivity when action bar button is clicked
			Intent formIntent = new Intent(context, FormActivity.class);
			startAct(formIntent, "formButton");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// setData method adds data to custom object after file is parsed
	public void setData (String thisTitle, String thisInfo, String thisImage, 
			String thisLatitude, String thisLongitude) {

		Data newSavedObj = new Data(thisTitle, thisInfo, thisImage, 
				thisLatitude, thisLongitude);
		savedData.add(newSavedObj);
	}

	// onActivityResult refreshes the MapFragment after the MainActivity is revisited
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			File checkForFile = getBaseContext().getFileStreamPath(getResources()
					.getString(R.string.data_file_name));
			if (checkForFile.exists()) {
				String fileContent = fileManager.readFromFile(context, getResources()
						.getString(R.string.data_file_name));
				if (fileContent != null) {
					parseData(fileContent.toString());
					Log.i(tag, fileContent.toString());
				}
			}

			locMgr.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
			FragmentTransaction transaction = fragMgr.beginTransaction();
			transaction.detach(mapFrag);
			transaction.attach(mapFrag);
			transaction.commit();
		}
	}

	/* startAct method starts new activity by passing the new Intent as argument.
		If form activity is not to be started extras are added to the Intent for the 
		Detail Activity UI.
	 */
	public void startAct(Intent actIntent, String selection) {
		if (!selection.matches("formButton") || !selection.matches("formMap")) {
			for (int k = 0; k < savedData.size(); k++) {
				if (savedData.get(k).title.matches(selection)) {
					actIntent.putExtra("Title", savedData.get(k).title.toString());
					actIntent.putExtra("Information", savedData.get(k).info.toString());
					actIntent.putExtra("Image", savedData.get(k).image.toString());
					actIntent.putExtra("Latitude", savedData.get(k).latitude.toString());
					actIntent.putExtra("Longitude", savedData.get(k).longitude.toString());
				}
			}
		}
		startActivityForResult(actIntent, 1);
	}

	// sendNum method returns the number of saved objects to the MainFragment
	public int sendNum() {
		return savedData.size();
	}

	public ArrayList<String> sendData(int dataObjNum) {
		ArrayList<String> pinData = new ArrayList<String>();
		pinData.add(savedData.get(dataObjNum).title.toString());
		pinData.add(savedData.get(dataObjNum).latitude.toString());
		pinData.add(savedData.get(dataObjNum).longitude.toString());
		pinData.add(savedData.get(dataObjNum).info.toString());
		return pinData;
	}

	// parsedData method is passed raw text from file, parses, and calls setData for each JSON object.
	public void parseData (String rawData) {

		JSONObject mainObject = null;
		JSONArray subObject = null;

		try {
			mainObject = new JSONObject(rawData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (mainObject != null) {
				subObject = mainObject.getJSONArray("data");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < subObject.length(); i++) {

			JSONObject dataObj = null;
			try {
				if (subObject != null) {
					dataObj = subObject.getJSONObject(i);

					String title = dataObj.getString("Title");
					String info = dataObj.getString("Information");
					String image = dataObj.getString("Image");
					String latitude = dataObj.getString("Latitude");
					String longitude = dataObj.getString("Longitude");

					Log.i(tag, title.toString());
					Log.i(tag, info.toString());
					Log.i(tag, latitude.toString());
					Log.i(tag, longitude.toString());
					Log.i(tag, image.toString());

					setData(title, info, image, latitude, longitude);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// onLocationChanged updates the MapFragment with current GPS location.
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		((MainFragment)mapFrag).updateMap(location.getLatitude(), location.getLongitude());
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}
}
