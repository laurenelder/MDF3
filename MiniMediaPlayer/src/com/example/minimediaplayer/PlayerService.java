package com.example.minimediaplayer;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;

public class PlayerService extends Service{
	
//	public Activity currentActivity; 
	public String state = "stopped";
	public String newState = "unused";
	
/*	public PlayerService(String callingBtn) {
//		this.currentActivity = startingAct;
		if(callingBtn.matches("previous")) {
			
		}
		if(callingBtn.matches("play")) {
			started = "true";
			Log.i("PlayerService", "Play button hit");
		}
		if(callingBtn.matches("pause")) {
			
		}
		if(callingBtn.matches("stop")) {
			started = "false";
		}
		if(callingBtn.matches("next")) {
			
		}
	}
	
	public static String getStatus() {
		// TODO Auto-generated method stub
		return started;
	}*/
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		state = "active";
		Log.i("PlayerService", "Player Service created");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String input = intent.getAction();
		if(input.matches("ACTION_PLAY")) {
			if (state.matches("playing")) {
				Log.i("PlayerService", "Player Service paused");
				newState = "paused";
			} else {
				Log.i("PlayerService", "Player Service playing");
				newState = "playing";
			}
			state = newState;
		}
/*		if(input.matches("ACTION_STOP")) {
			Log.i("PlayerService", "Player Service stopped");
		}*/
		if(input.matches("ACTION_PREVIOUS")) {
			Log.i("PlayerService", "Player Service previous track");
		}
		if(input.matches("ACTION_NEXT")) {
			Log.i("PlayerService", "Player Service next track");
		}
		
		
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

//	Button playButton = (Button)this.currentActivity.findViewById(R.id.playBtn);
}