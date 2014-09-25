package com.laurenelder.photolocationapp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
//import com.google.android.gms.maps.MapFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class MainFragment extends MapFragment implements OnInfoWindowClickListener, OnMapLongClickListener {
	
	private static final int REQUEST_ENABLE_GPS = 0x02001;
	
	Context context;
	String tag = "MainFragment";
	GoogleMap mainMap;
	String additionalInfo;
	List<MarkerData> markerData = new ArrayList<MarkerData>();
	
	private mainInterface mainActivity;

	// Set up interface
	public interface mainInterface {
		public int sendNum();
		public ArrayList<String> sendData(int dataObj);
		public void startAct(Intent actIntent, String selection);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		context = getActivity();

		if (activity instanceof mainInterface) {
			mainActivity = (mainInterface) activity;
		} else {
			throw new ClassCastException((activity.toString()) + 
					"Did not impliment mainInterface");
		}
	}

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		mainMap = getMap();
		
//		mainMap.animateCamera(CameraUpdateFactory.newLatLngZoom
//				(new LatLng(latit, latit), 10));
		
		int num = mainActivity.sendNum();
		for (int i = 0; i < num; i++) {
			ArrayList<String> pindata = new ArrayList<String>();
			pindata = mainActivity.sendData(i);
			String title = pindata.get(0);
			String latit = pindata.get(1);
			String longit = pindata.get(2);
			String info = pindata.get(3);
			addPin(latit, longit, title, info);
		}
		mainMap.setInfoWindowAdapter(new MarkerAdapter());
		mainMap.setOnInfoWindowClickListener(this);
		mainMap.setOnMapLongClickListener(this);
		
		for (int q = 0; q < markerData.size(); q++) {
			mainMap.addMarker(new MarkerOptions().position
					(new LatLng(markerData.get(q).latitude,markerData.get(q).longitude))
					.title(markerData.get(q).title));
//			additionalInfo = markerData.get(q).info;
			
//			mainMap.setInfoWindowAdapter(new MarkerAdapter());
			
		}
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		Intent detailIntent = new Intent(context, DetailsActivity.class);
		mainActivity.startAct(detailIntent, arg0.getTitle().toString());
	}
	
	@Override
	public void onMapLongClick(LatLng arg0) {
		// TODO Auto-generated method stub
		Intent formIntent = new Intent(context, FormActivity.class);
		formIntent.putExtra("Latitude", String.valueOf(arg0.latitude));
		formIntent.putExtra("Longitude", String.valueOf(arg0.longitude));
		mainActivity.startAct(formIntent, "formMap");
	}

/*	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View mainView = inflater.inflate(R.layout.activity_main, container);
		return mainView;
	}*/
	
	public void addPin(String latit, String longit, String pinTitle, String pinInfo) {
		if (mainMap != null) {
/*			mainMap.addMarker(new MarkerOptions().position
					(new LatLng(Double.parseDouble(latit),Double.parseDouble(longit)))
					.title(pinTitle));
			additionalInfo = pinInfo;
			
			mainMap.setInfoWindowAdapter(new MarkerAdapter());*/
			
	    	MarkerData newMarker = new MarkerData(pinTitle, pinInfo, 
	    			Double.parseDouble(latit), Double.parseDouble(longit));
	    	markerData.add(newMarker);
		}
	}

	
	public void updateMap(double latitude, double longitude) {
		mainMap.animateCamera(CameraUpdateFactory.newLatLngZoom
		(new LatLng(latitude, longitude), 14));
	}
	
	private class MarkerAdapter implements InfoWindowAdapter {
		
		TextView markerTextView;
		

		public MarkerAdapter() {
			markerTextView = new TextView(getActivity());
			markerTextView.setGravity(Gravity.CENTER);
			markerTextView.setWidth(400);
		}

		@Override
		public View getInfoContents(Marker marker) {
			for (int t = 0; t < markerData.size(); t++) {
				Log.i(tag, String.valueOf(marker.getPosition().latitude + "-" 
						+ String.valueOf(markerData.get(t).latitude)));
				Log.i(tag, String.valueOf(marker.getPosition().longitude + "-" 
						+ String.valueOf(markerData.get(t).longitude)));

				DecimalFormat formatter = new DecimalFormat("###.####");
				
				Log.i(tag, String.valueOf(formatter.format(marker.getPosition().latitude) + "-" 
						+ String.valueOf(formatter.format(markerData.get(t).latitude))));
				Log.i(tag, String.valueOf(formatter.format(marker.getPosition().longitude) + "-" 
						+ String.valueOf(formatter.format(markerData.get(t).longitude))));
				
				String markLat = String.valueOf(formatter.format(marker.getPosition().latitude));
				String markLong = String.valueOf(formatter.format(marker.getPosition().longitude));
				String savedLat = String.valueOf(formatter.format(markerData.get(t).latitude));
				String savedLong = String.valueOf(formatter.format(markerData.get(t).longitude));
				
				if (markLat.matches(savedLat) && markLong.matches(savedLong)) {
					markerTextView.setText(markerData.get(t).title + "\r\n" + markerData.get(t).info.toString());
				}
			}
			return markerTextView;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			return null;
		}
	}


}


