package com.example.minimediaplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {
	
	Context context;
	Intent playerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Set Variables
        context = this;
        playerIntent = new Intent(context, PlayerService.class);
        
        // Set OnClickListeners for Audio Buttons
        Button previousButton = (Button)findViewById(R.id.previousBtn);
        previousButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				PlayerService service = new PlayerService("previous");
//				Intent playerIntent = new Intent(context, PlayerService.class);
				startService(playerIntent.setAction("ACTION_PREVIOUS"));
			}
        	
        });
        final Button playButton = (Button)findViewById(R.id.playBtn);
        playButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("MainActivity", "Play button clicked");
//				Intent playerIntent = new Intent(context, PlayerService.class);
				startService(playerIntent.setAction("ACTION_PLAY"));
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
        Button stopButton = (Button)findViewById(R.id.stopBtn);
        stopButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent playerIntent = new Intent(context, PlayerService.class);
//				PlayerService service = new PlayerService("stop");
				Log.i("MainActivity", "Service Player stopped");
				stopService(playerIntent);
//				startService(playerIntent.setAction("ACTION_PLAY"));
			}
        	
        });
        Button nextButton = (Button)findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				PlayerService service = new PlayerService("next");
//				Intent playerIntent = new Intent(context, PlayerService.class);
				startService(playerIntent.setAction("ACTION_NEXT"));
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
}