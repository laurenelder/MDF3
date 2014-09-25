package com.laurenelder.photolocationapp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class FormActivity extends Activity implements FormFragment.formInterface, LocationListener {
	
	Context context;
	String tag = "FormActivity";
	Uri imageUri;
	FileManager fileManager;
	FragmentManager formMgr;
	Fragment formFrag;
	LocationManager locMgr;
	Location loc;
	Intent finishedIntent = new Intent();
	Intent formIntent;
	List<Data> savedData = new ArrayList<Data>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_form);
		
		context = this;
		formIntent = this.getIntent();
		
/*		if (!savedData.isEmpty()) {
			savedData.removeAll(savedData);
		}*/
		
		formMgr = getFragmentManager();
		formFrag = (FormFragment)formMgr.findFragmentById(R.id.formFrag);
		if(formFrag == null) {
			formFrag = new FormFragment();
		}
		
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
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		
		menu.findItem(R.id.action_item).setIcon(R.drawable.ic_action_accept);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.action_item) {
    		locMgr = (LocationManager)getSystemService(LOCATION_SERVICE);
    		locMgr.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
    		loc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    		Log.i(tag, "Action Button Hit");
    		return true;
        }
        return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		setResult(RESULT_CANCELED, finishedIntent);
		finish();
		super.onBackPressed();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		if(requestCode == 1 && resultCode != RESULT_CANCELED) {
			if(data == null) {
				((FormFragment)formFrag).updateImageButton(data, imageUri);
				updateGallery(imageUri);
			} else {
				((FormFragment)formFrag).updateImageButton(data, imageUri);
			}
		}
	}
	
	private void updateGallery(Uri currentUri) {
		Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    scanIntent.setData(currentUri);
	    sendBroadcast(scanIntent);
	}
	
	private Uri getImageUri() {
		
		String imageName = new SimpleDateFormat("MMddyyyy_HHmmss").format(new Date(System.currentTimeMillis()));
		
		File imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File appDir = new File(imageDir, "CameraExample");
		appDir.mkdirs();
		
		File image = new File(appDir, imageName + ".jpg");
		try {
			image.createNewFile();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return Uri.fromFile(image);
	}
	
	public void startCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		imageUri = getImageUri();
		if(imageUri != null) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		}
		startActivityForResult(intent, 1);
	}
	
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
    
    public void setData (String thisTitle, String thisInfo, String thisImage, 
    		String thisLatitude, String thisLongitude) {
    	
    	Data newSavedObj = new Data(thisTitle, thisInfo, thisImage,
    			 thisLatitude, thisLongitude);
    	savedData.add(newSavedObj);
    }
    
    public void saveData() {
    	String preJson = "{'data':[";
    	String contentJson = null;
    	String postJson = "]}";
    	Log.i(tag, "saveData method hit");
    	for (int i = 0; i < savedData.size(); i++) {
    		if (i == 0) {
        		contentJson = "{'Title':" + "'" + savedData.get(i).title.toString() + "'" + "," +
        				"'Information':" + "'" + savedData.get(i).info.toString() + "'" + "," +
        				"'Image':" + "'" + savedData.get(i).image.toString() + "'" + "," +
        				"'Latitude':" + "'" + savedData.get(i).latitude.toString() + "'" + "," +
        				"'Longitude':" + "'" + savedData.get(i).longitude.toString() + "'" + "}";
    		} else {
    			contentJson = contentJson + "{'Title':" + "'" + savedData.get(i).title.toString() + "'" + "," +
        				"'Information':" + "'" + savedData.get(i).info.toString() + "'" + "," +
        				"'Image':" + "'" + savedData.get(i).image.toString() + "'" + "," +
        				"'Latitude':" + "'" + savedData.get(i).latitude.toString() + "'" + "," +
        				"'Longitude':" + "'" + savedData.get(i).longitude.toString() + "'" + "}";
    		}

    		if (i != savedData.size() - 1) {
    			contentJson = contentJson + ",";
    		}	
    	}
    	String fullJson = preJson + contentJson + postJson;
    	Log.i(tag, fullJson);
    	fileManager.writeToFile(context, getResources().getString(R.string.data_file_name), fullJson);
		setResult(RESULT_OK, finishedIntent);
		finish();
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.i(tag, "onLocationChanged method hit");
		ArrayList<String> currentData = new ArrayList<String>();

		if (((FormFragment) formFrag).sendData() != null) {
			Log.i(tag, "sendData method not null");
			currentData = ((FormFragment) formFrag).sendData();
			if (formIntent.getExtras() != null) {
				setData(currentData.get(0).toString(), currentData.get(1).toString(), 
						currentData.get(2).toString(), formIntent.getExtras()
						.getString("Latitude").toString(), formIntent.getExtras()
						.getString("Longitude").toString());
			} else {
				setData(currentData.get(0).toString(), currentData.get(1).toString(), 
						currentData.get(2).toString(), String.valueOf(location.getLatitude()), 
						String.valueOf(location.getLongitude()));
			}

			saveData();
		}
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