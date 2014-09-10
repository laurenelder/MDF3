/* Name: Devin "Lauren" Elder
 * Date: 09/07/2014
 * Term: 1409
 * Project Name: Mini Media Player
 * Assignment: MDF3 Week 1
 */

package com.example.minimediaplayer;

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
//	boolean playing;
/*	Button previousButton;
	Button repeatButton;
	Button playButton;
	Button stopButton;
	Button nextButton;
	TextView songName;*/
	PlayerService playerService;
//	BoundService boundService;
	boolean boundToService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        
        
        // Initialize Variables
        context = this;
//        playing = false;
        playerIntent = new Intent(context, PlayerService.class);
/*        previousButton = (Button)findViewById(R.id.previousBtn);
        repeatButton = (Button)findViewById(R.id.repeatBtn);
        playButton = (Button)findViewById(R.id.playBtn);
        stopButton = (Button)findViewById(R.id.stopBtn);
        nextButton = (Button)findViewById(R.id.nextBtn);
        songName = (TextView)findViewById(R.id.songTitle);*/
//        serviceHandler("ACTION_INFO");
//        serviceHandler();
        
        // Set FragmentManager to allow calling methods in MainFragment
        fragMgr = getFragmentManager();
		fragment = (MainFragment)fragMgr.findFragmentById(R.id.frag);
		if(fragment == null) {
			fragment = new MainFragment();
		}

        
/*        // Set OnClickListeners for Audio Buttons
        previousButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// Call Handler Method to play previous track
				serviceHandler(playerIntent.setAction("ACTION_PREVIOUS"));
				playButton.setBackgroundResource(R.drawable.ic_action_pause);
				play = false;
			}
        	
        });
        repeatButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				playerHandler("repeat");
			}
        
        });
        playButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				/* Call Handler Method to play/pause track. Conditional 
				 * changes button image based on whether or not media is playing.
				 
//				Log.i("MainActivity", "Play button clicked");
				serviceHandler(playerIntent.setAction("ACTION_PLAY"));
//				bindService(playerIntent, this, Context.BIND_AUTO_CREATE);
				if (play == true) {
					playButton.setBackgroundResource(R.drawable.ic_action_pause);
					play = false;
				} else {
					playButton.setBackgroundResource(R.drawable.ic_action_play);
					play = true;
				}
			}

			private void bindService(Intent playerIntent,
					OnClickListener onClickListener, int bindAutoCreate) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        stopButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// Call stopService to stop and release media player service.

				Log.i("MainActivity", "Service Player stopped");
				playButton.setBackgroundResource(R.drawable.ic_action_play);
				stopService(playerIntent);
			}
        	
        });
        nextButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// Call Handler Method to play next track.
				serviceHandler(playerIntent.setAction("ACTION_NEXT"));
				playButton.setBackgroundResource(R.drawable.ic_action_pause);
				play = false;
			}
        	
        }); 
        */
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
    
/*	@Override
	protected void onStop() {
		super.onStop();
		
		if(isBound) {
			unbindService(this);
			
			// Backup in case the service was started and not stopped.
			// Not needed if only binding was used.
			Intent intent = new Intent(this, PlayerService.class);
			stopService(intent);
		}
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		BoundServiceBinder binder = (BoundServiceBinder)service;
		pService = binder.getService();
		isBound = true;
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		pService = null;
		isBound = false;
	}*/
    
/*    public void playerHandler(String btnAction) {
    	
    	Handler handler = new Handler() { 
    		@Override 
    		public void handleMessage(Message msg) { 
    			if (msg.arg1 == RESULT_OK && msg.obj != null) { 
    				//do stuff here } } };
//    				songName.setText(msg.obj.toString());
//    				playButton.setBackgroundResource(R.drawable.ic_action_pause);
//    				play = false;
    			}
    		}
    	};

//    	Messenger messenger = new Messenger(handler); 
//    	serviceIntent.putExtra(PlayerService.MESSENGER_KEY, messenger); 
//    	startService(serviceIntent);
    	PlayerService player = new PlayerService();
    	player.buttonAction(btnAction);
    }	
    
/*    // Stop Service Method
    public void stopService() {
    	stopService(playerIntent);
    }*/
    
    /* serviceHandler method allows communication from the player service
     * to the activity. The UI is updated from the data passed.
     */
   public void serviceHandler() {
    	
    	Handler handler = new Handler() { 
    		@Override 
    		public void handleMessage(Message msg) { 
    			if (msg.arg1 == RESULT_OK && msg.obj != null) { 
    				//do stuff here } } };
//    				songName.setText(msg.obj.toString());
//    				playButton.setBackgroundResource(R.drawable.ic_action_pause);
//    				play = false;
    				if (fragment != null) {
    					((MainFragment) fragment).updateUI(false, msg.obj.toString());
    				}
    			}
    		}
    	};

    	Messenger messenger = new Messenger(handler); 
    	playerIntent.putExtra(PlayerService.MESSENGER_KEY, messenger); 
    	startService(playerIntent);
    }
    
    public void buttonClick(String selectedButton) {
    	if (selectedButton.matches("previous")) {
    		if (!boundToService) {
    			startService(playerIntent);
    			bindService(playerIntent, this, Context.BIND_AUTO_CREATE);
    		} else {
    			playerService.buttonAction("previous");
    		}
    	}
    	if (selectedButton.matches("repeat")) {
    		if (!boundToService) {
    			startService(playerIntent);
    			bindService(playerIntent, this, Context.BIND_AUTO_CREATE);
    		} else {
    			playerService.buttonAction("repeat");
    		}
    	}
    	if (selectedButton.matches("play")) {
    		if (!boundToService) {
    			startService(playerIntent);
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
    			startService(playerIntent);
    			bindService(playerIntent, this, Context.BIND_AUTO_CREATE);
    		} else {
    			playerService.buttonAction("next");
    		}
    	}
    	if (selectedButton.matches("info")) {
    		if (!boundToService) {
    			startService(playerIntent);
    			bindService(playerIntent, this, Context.BIND_AUTO_CREATE);
    		} else {
    			playerService.buttonAction("info");
    		}
    	}
    }
    
/*    public void updateFragmentUI(boolean isPlaying, String songTitle) {
    	((MainFragment) fragment).updateUI(isPlaying, songTitle);
    }*/


	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		// TODO Auto-generated method stub
		BoundServiceBinder binder = (BoundServiceBinder)service;
		playerService = binder.getService();
		boundToService = true;
//		serviceHandler();
		
/*    	Handler handler = new Handler() { 
    		@Override 
    		public void handleMessage(Message msg) { 
    			if (msg.arg1 == RESULT_OK && msg.obj != null) { 
    				//do stuff here } } };
//    				songName.setText(msg.obj.toString());
//    				playButton.setBackgroundResource(R.drawable.ic_action_pause);
//    				play = false;
    				if (fragment != null) {
    					((MainFragment) fragment).updateUI(false, msg.obj.toString());
    				}
    			}
    		}
    	};

    	Messenger messenger = new Messenger(handler); 
    	playerIntent.putExtra(PlayerService.MESSENGER_KEY, messenger); 
    	playerService.MediaService(playerIntent);*/
	}


	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub
		playerService = null;
		boundToService = false;
	}
}
