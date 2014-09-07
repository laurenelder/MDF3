package com.example.minimediaplayer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
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
        
        
        // Set Variables
        context = this;
        play = true;
        playerIntent = new Intent(context, PlayerService.class);
        previousButton = (Button)findViewById(R.id.previousBtn);
        playButton = (Button)findViewById(R.id.playBtn);
        stopButton = (Button)findViewById(R.id.stopBtn);
        nextButton = (Button)findViewById(R.id.nextBtn);
        songName = (TextView)findViewById(R.id.songTitle);

        
        // Set OnClickListeners for Audio Buttons
        previousButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				PlayerService service = new PlayerService("previous");
//				Intent playerIntent = new Intent(context, PlayerService.class);
//				startService(playerIntent.setAction("ACTION_PREVIOUS"));
				serviceHandler(playerIntent.setAction("ACTION_PREVIOUS"));
				playButton.setBackgroundResource(R.drawable.ic_action_pause);
				play = false;
			}
        	
        });
        playButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("MainActivity", "Play button clicked");
//				Intent playerIntent = new Intent(context, PlayerService.class);
//				startService(playerIntent.setAction("ACTION_PLAY"));
				serviceHandler(playerIntent.setAction("ACTION_PLAY"));
//				bindService(playerIntent, this, Context.BIND_AUTO_CREATE);
				if (play == true) {
					playButton.setBackgroundResource(R.drawable.ic_action_pause);
					play = false;
				} else {
					playButton.setBackgroundResource(R.drawable.ic_action_play);
					play = true;
				}
				
//				PlayerService serviceStr = new PlayerService();
				
/*				if(PlayerService.getStatus().matches("false")) {
					startService(playerIntent);
					PlayerService service = new PlayerService("play");
					playButton.setBackgroundResource(R.drawable.ic_action_pause);
				} else {
					PlayerService service = new PlayerService("pause");
					playButton.setBackgroundResource(R.drawable.ic_action_play);
				}*/
			}

/*			private void bindService(Intent playerIntent,
					OnClickListener onClickListener, int bindAutoCreate) {
				// TODO Auto-generated method stub
				
			}*/
        	
        });
        stopButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent playerIntent = new Intent(context, PlayerService.class);
//				PlayerService service = new PlayerService("stop");
				Log.i("MainActivity", "Service Player stopped");
				playButton.setBackgroundResource(R.drawable.ic_action_play);
//				startService(playerIntent.setAction("ACTION_PLAY"));
				stopService(playerIntent);
			}
        	
        });
        nextButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				PlayerService service = new PlayerService("next");
//				Intent playerIntent = new Intent(context, PlayerService.class);
//				startService(playerIntent.setAction("ACTION_NEXT"));
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
    
    public void serviceHandler(Intent serviceIntent) {
    	
    	Handler handler = new Handler() { 
    		@Override 
    		public void handleMessage(Message msg) { 
    			if (msg.arg1 == RESULT_OK && msg.obj != null) { 
    				//do stuff here } } };
    				songName.setText(msg.obj.toString());
    			}
    		}
    	};

    	Messenger messenger = new Messenger(handler); 
//    	Intent intent = new Intent(this, LaunchIntentService.class); 
    	serviceIntent.putExtra(PlayerService.MESSENGER_KEY, messenger); 
    	startService(serviceIntent);
    }	
}
