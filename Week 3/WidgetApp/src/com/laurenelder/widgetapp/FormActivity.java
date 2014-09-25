/* Name: Devin "Lauren" Elder
 * Date: 09/18/2014
 * Term: 1409
 * Project Name: Widget App
 * Assignment: MDF3 Week 3
 */

package com.laurenelder.widgetapp;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class FormActivity  extends Activity implements FormFragment.formInterface{

	// Establish Variables
	String tag = "FormActivity";
	Context context;
	FileManager fileMgr;
	FragmentManager formMgr;
	Fragment formFrag;
	Intent intent;
	Intent returnedIntent;
	SharedPreferences preferences;

	// onBackPressed overrides the function of the android back button to close the current view
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		setResult(RESULT_CANCELED, returnedIntent);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_form);

		// Initialize Variables
		context = getApplicationContext();
		fileMgr = FileManager.getInstance();
		Intent returnedIntent = new Intent();

		// Set FragmentManager to allow calling methods in FormFragment
		formMgr = getFragmentManager();
		formFrag = (FormFragment)formMgr.findFragmentById(R.id.formFrag);
		if(formFrag == null) {
			formFrag = new MainFragment();
		}

		// Get Shared Preferences
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);

		menu.findItem(R.id.action_item).setIcon(R.drawable.ic_action_accept);

		return super.onCreateOptionsMenu(menu);
	}

	// Action bar button saves the data that was input by the user on the form view
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (((FormFragment) formFrag).getData() != null) {

			ArrayList<String> data = new ArrayList<String>();
			data = ((FormFragment) formFrag).getData();

			String allData = data.get(0).toString() + "  " + data.get(1).toString() + "\n" +
					data.get(2).toString() + "  " + data.get(3).toString() + "\n" +
					data.get(4).toString() + "  " + data.get(5).toString();
			String title = data.get(1).toString() + " " +
					data.get(3).toString();
			if (title.contains("/")) {
				title = title.replace("/", ".");
			}

			fileMgr.writeToFile(context, title, allData);
			saveTitle(title);

			if (intent.getExtras().getString("source").toString().matches("widget")) {
				Intent mainIntent = new Intent(context, MainActivity.class);
				startActivity(mainIntent);
				finish();
			} else {
				setResult(RESULT_OK, returnedIntent);
				finish();
			}
		}

		return true;
	}

	// saveTitle method stored the entry title in Shared Preferences
	public void saveTitle(String title) {
		Integer listCount = 1;
		if(!preferences.toString().isEmpty()) {
			Map<String,?> entries = preferences.getAll();
			for(Map.Entry<String,?> entry : entries.entrySet()){
				Log.i(tag, listCount.toString());
				listCount++;
				Log.i(tag, entry.getValue().toString());
			}
		}

		preferences.edit().putString("fileName" + listCount.toString(), title).apply();
	}
}
