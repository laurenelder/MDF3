package com.laurenelder.widgetapp;

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
import android.widget.Toast;

public class DetailActivity extends Activity implements DetailFragment.detailInterface{
	
	// Establish Variables
	String tag = "DetailActivity";
	Context context;
	Intent intent;
	String contentData = null;
	String contentTitle = null;
	FragmentManager detailMgr;
	Fragment detailFrag;
	Intent returnedIntent;
	FileManager fileMgr;
	SharedPreferences preferences;

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
		setContentView(R.layout.fragment_details);
		context = getApplicationContext();
		intent = this.getIntent();
		returnedIntent = new Intent();
		fileMgr = FileManager.getInstance();
		contentTitle = intent.getExtras().getString("title").toString();
//		contentData = intent.getExtras().getString("content").toString();
//		Log.i(tag, contentData.toString());
		
        // Set FragmentManager to allow calling methods in FormFragment
        detailMgr = getFragmentManager();
        detailFrag = (DetailFragment)detailMgr.findFragmentById(R.id.detailsFrag);
		if(detailFrag == null) {
			detailFrag = new DetailFragment();
		} else {
			if (contentTitle != null) {
				String fileContent = fileMgr.readFromFile(context, contentTitle);
				((DetailFragment) detailFrag).updateUI(fileContent);
			}
/*			if (contentData != null) {
				((DetailFragment) detailFrag).updateUI(contentData);
			}*/
			
		}
		
		
        // Get Shared Preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		
		menu.findItem(R.id.action_item).setIcon(R.drawable.ic_action_discard);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == R.id.action_item) {
        	String title = intent.getExtras().get("title").toString();
    		if(!preferences.toString().isEmpty()) {
    			Map<String,?> entries = preferences.getAll();
    			for(Map.Entry<String,?> entry : entries.entrySet()){
    				
    				if (entry.getValue().toString().matches(title)) {
    					preferences.edit().remove(entry.getKey()).apply();
    					Toast.makeText(context, "Entry Deleted", Toast.LENGTH_SHORT).show();
    			        setResult(RESULT_OK, returnedIntent);
    					finish();
    				}
    				
    			}
    		}
            return true;
        }
        return super.onOptionsItemSelected(item);
	}

	public String getData() {
		if (contentData != null) {
			Log.i(tag, contentData.toString());
			return contentData;
		} else {
			return null;
		}
	}
}
