/* Name: Devin "Lauren" Elder
 * Date: 09/11/2014
 * Term: 1409
 * Project Name: Mini Media Player
 * Assignment: MDF3 Week 2
 */

package com.example.minimediaplayer;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.view.Menu;
import android.view.MenuItem;

import com.example.minimediaplayer.PlayerService.BoundServiceBinder;


public class MainActivity extends Activity implements MainFragment.onSelectedButton, ServiceConnection {
	
	// Establish Variables
	Context context;
	Intent playerIntent;
	FragmentManager fragMgr;
	Fragment fragment;
	PlayerService playerService;
	boolean boundToService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        
        
        // Initialize Variables
        context = this;
        playerIntent = new Intent(context, PlayerService.class);
        
        // Start and Bind to Service if not bound on load.
		if (!boundToService) {
			serviceHandler();
   			startService(playerIntent);
			bindService(playerIntent, this, Context.BIND_AUTO_CREATE);
		} else {
			playerService.buttonAction("info");
		}
        
        // Set FragmentManager to allow calling methods in MainFragment
        fragMgr = getFragmentManager();
		fragment = (MainFragment)fragMgr.findFragmentById(R.id.frag);
		if(fragment == null) {
			fragment = new MainFragment();
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /* serviceHandler method allows communication from the player service
     * to the activity. The UI is updated from the data passed.
     */
   public void serviceHandler() {
    	
    	Handler handler = new Handler() { 
    		@Override 
    		public void handleMessage(Message msg) { 
    			if (msg.arg1 == RESULT_OK && msg.obj != null) { 
    				if (fragment != null) {
    					ArrayList<String> data = (ArrayList<String>) msg.obj;
    					((MainFragment) fragment).updateUI(data);
    				}
    			}
    		}
    	};

    	Messenger messenger = new Messenger(handler); 
    	playerIntent.putExtra(PlayerService.MESSENGER_KEY, messenger); 
    	startService(playerIntent);
    }
    
   /* Call method in service and pass selected button as argument.
    * If not bound to service, start service and bind activity.
    */
    public void buttonClick(String selectedButton) {
    	if (selectedButton.matches("previous")) {
    		if (!boundToService) {
    			serviceHandler();
    			bindService(playerIntent, this, Context.BIND_AUTO_CREATE);
    		} else {
    			playerService.buttonAction("previous");
    		}
    	}
    	if (selectedButton.matches("repeat")) {
    		if (!boundToService) {
    			serviceHandler();
    			bindService(playerIntent, this, Context.BIND_AUTO_CREATE);
    		} else {
    			playerService.buttonAction("repeat");
    		}
    	}
    	if (selectedButton.matches("play")) {
    		if (!boundToService) {
    			serviceHandler();
    			bindService(playerIntent, this, Context.BIND_AUTO_CREATE);
    		} else {
    			playerService.buttonAction("play");
    		}
    	}
    	if (selectedButton.matches("stop")) {
    		stopService(playerIntent);
			if(boundToService) {
				unbindService(this);
				boundToService = false;
				playerService = null;
			}
    	}
    	if (selectedButton.matches("next")) {
    		if (!boundToService) {
    			serviceHandler();
    			bindService(playerIntent, this, Context.BIND_AUTO_CREATE);
    		} else {
    			playerService.buttonAction("next");
    		}
    	}
    }

    // Binder Method to pass data to service
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		// TODO Auto-generated method stub
		BoundServiceBinder binder = (BoundServiceBinder)service;
		playerService = binder.getService();
		boundToService = true;
	}

	// Bind Disconnected Method to Release Service and Binder.
	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub
		playerService = null;
		boundToService = false;
	}
}