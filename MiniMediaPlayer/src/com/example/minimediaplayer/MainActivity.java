/* Name: Devin "Lauren" Elder
 * Date: 09/07/2014
 * Term: 1409
 * Project Name: Mini Media Player
 * Assignment: MDF3 Week 1
 */

package com.example.minimediaplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	// Establish Variables
	Context context;
	Intent playerIntent;
	boolean play;
	Button previousButton;
	Button playButton;
	Button stopButton;
	Button nextButton;
	TextView songName;
	PlayerService pService;
	boolean isBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        // Initialize Variables
        context = this;
        play = true;
        playerIntent = new Intent(context, PlayerService.class);
        previousButton = (Button)findViewById(R.id.previousBtn);
        playButton = (Button)findViewById(R.id.playBtn);
        stopButton = (Button)findViewById(R.id.stopBtn);
        nextButton = (Button)findViewById(R.id.nextBtn);
        songName = (TextView)findViewById(R.id.songTitle);
        serviceHandler(playerIntent.setAction("ACTION_INFO"));

        
        // Set OnClickListeners for Audio Buttons
        previousButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// Call Handler Method to play previous track
				serviceHandler(playerIntent.setAction("ACTION_PREVIOUS"));
				playButton.setBackgroundResource(R.drawable.ic_action_pause);
				play = false;
			}
        	
        });
        playButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				/* Call Handler Method to play/pause track. Conditional 
				 * changes button image based on whether or not media is playing.
				 */
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

/*			private void bindService(Intent playerIntent,
					OnClickListener onClickListener, int bindAutoCreate) {
				// TODO Auto-generated method stub
				
			}*/
        	
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
    
    /* serviceHandler method allows communication from the player service
     * to the activity. The UI is updated from the data passed.
     */
    public void serviceHandler(Intent serviceIntent) {
    	
    	Handler handler = new Handler() { 
    		@Override 
    		public void handleMessage(Message msg) { 
    			if (msg.arg1 == RESULT_OK && msg.obj != null) { 
    				//do stuff here } } };
    				songName.setText(msg.obj.toString());
    				playButton.setBackgroundResource(R.drawable.ic_action_pause);
    				play = false;
    			}
    		}
    	};

    	Messenger messenger = new Messenger(handler); 
    	serviceIntent.putExtra(PlayerService.MESSENGER_KEY, messenger); 
    	startService(serviceIntent);
    }	
}
