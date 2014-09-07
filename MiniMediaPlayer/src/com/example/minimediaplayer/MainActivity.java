package com.example.minimediaplayer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        registerReceiver(serviceBroadcast, new IntentFilter("trackTitle"));
        
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
				startService(playerIntent.setAction("ACTION_PREVIOUS"));
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
				startService(playerIntent.setAction("ACTION_PLAY"));
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
				startService(playerIntent.setAction("ACTION_NEXT"));
				playButton.setBackgroundResource(R.drawable.ic_action_pause);
				play = false;
			}
        	
        });
    }
    
    private BroadcastReceiver serviceBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent i) {
            // do stuff to the UI
        	Log.i("MainActivity", "Reciever was hit");
        	String title = (String) i.getExtras().get("title");
        	songName.setText(title);
        }
    };

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
}
