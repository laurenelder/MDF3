package com.laurenelder.widgetapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.DropBoxManager.Entry;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity implements MainFragment.mainInterface {
	
	// Establish Variables
	String tag = "MainActivity";
	Context context;
	FileManager fileMgr;
	FragmentManager mainMgr;
	Fragment mainFrag;
	SharedPreferences preferences;
	
	
	List<ListItems> groceryItems = new ArrayList<ListItems>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        
        context = getApplicationContext();
        fileMgr = FileManager.getInstance();
        
        // Set FragmentManager to allow calling methods in FormFragment
        mainMgr = getFragmentManager();
        mainFrag = (MainFragment)mainMgr.findFragmentById(R.id.mainFrag);
		if(mainFrag == null) {
			mainFrag = new MainFragment();
		}
        
        
        // Get Shared Preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(context);



        refreshList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_item) {
        	Intent form = new Intent(context, FormActivity.class);
        	startAct(form);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                refreshList();
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
    
    public void startAct(Intent actIntent) {
    	startActivityForResult(actIntent, 1);
    }
    
	public void refreshList() {
//		groceryItems.removeAll(groceryItems);
		((MainFragment)mainFrag).resetObjects();
		if(!preferences.toString().isEmpty()) {
			Map<String,?> entries = preferences.getAll();
			for(Map.Entry<String,?> entry : entries.entrySet()){
				
				String fileName = entry.getValue().toString();
				String fileContent = fileMgr.readFromFile(context, fileName);
//				ListItems newItem = new ListItems(fileName, fileContent);
//				groceryItems.add(newItem);

				Log.i(tag, fileName);
				((MainFragment)mainFrag).upDateList(fileName, fileContent);
			}
		}
    }
}
