package com.example.minimediaplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;

public class PlayerService extends Service{
	


	//	public Activity currentActivity; 
	public String state = "stopped";
	public String newState = "unused";
	MediaPlayer player;
	int trackNum;
	boolean isPrepared = false;;
	ArrayList<String> resources;

	
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
	public void onDestroy() {
		// TODO Auto-generated method stub
		player.stop();
		player.reset();
		player.release();
		super.onDestroy();
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		state = "active";
		Log.i("PlayerService", "Player Service created");
		
		resources = new ArrayList<String>();
		String packageName = getPackageName();
		resources.add("android.resource://com.example.minimediaplayer/raw/lindsey_stirling0crystallize");
		resources.add("android.resource://com.example.minimediaplayer/raw/lindsey_stirling0elements");
		resources.add("android.resource://com.example.minimediaplayer/raw/lindsey_stirling0zelda_medley");
		
		if(player == null) {
			player = new MediaPlayer();
			isPrepared = false;
		}
		player.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				isPrepared = true;
				mp.start();
//				trackNum++;
				if(player.isPlaying()) {
					String unformattedTitle = resources.get(trackNum)
							.substring(resources.get(trackNum).lastIndexOf("0") + 1);
					unformattedTitle = unformattedTitle.replace("_", " ");
//					unformattedTitle = unformattedTitle.replace("0", " - ");
					String formattedTitle = unformattedTitle.substring(0, 1).toUpperCase() + 
							unformattedTitle.substring(1);
					Log.i("PlayerService", unformattedTitle.toString());
				}
			}

		});
		player.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				player.stop();
				player.reset();
				trackNum++;
				if (trackNum < (resources.size() - 1)) {
					try {
						player.setDataSource(PlayerService.this, Uri.parse(resources.get(trackNum)));
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						player.prepare();
					} catch (IllegalStateException e) {
						isPrepared = false;
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						isPrepared = false;
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					trackNum = 0;
				}
			}
			
		});
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String input = intent.getAction();
		if(input.matches("ACTION_PLAY")) {
			if (player.isPlaying()) {
				Log.i("PlayerService", "Player Service paused");
				newState = "paused";
				player.pause();
			}
			if(state.matches("paused")) {
				newState = "playing";
				player.start();
			} 
			if (!isPrepared){
				Log.i("PlayerService", "Player Service playing");
				newState = "playing";
				trackNum = 0;
					try {
						player.setAudioStreamType(AudioManager.STREAM_MUSIC);
						player.setDataSource(this, Uri.parse(resources.get(trackNum)));
						player.prepare();
						

					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			state = newState;
		}
/*		if(input.matches("ACTION_STOP")) {
			Log.i("PlayerService", "Player Service stopped");
			player.pause();
			player.stop();
			player.reset();
			player.release();
		}*/
		if(input.matches("ACTION_PREVIOUS")) {
			Log.i("PlayerService", "Player Service previous track");
			if (trackNum > 0) {
				trackNum = trackNum - 1;
			}
			player.stop();
			player.reset();
//			player.release();
			try {
				player.setDataSource(PlayerService.this, Uri.parse(resources.get(trackNum)));
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				player.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(input.matches("ACTION_NEXT")) {
			Log.i("PlayerService", "Player Service next track");
			if (trackNum < (resources.size() - 1)) {
				trackNum = trackNum + 1;
			}
			player.stop();
			player.reset();
//			player.release();
			try {
				player.setDataSource(PlayerService.this, Uri.parse(resources.get(trackNum)));
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				player.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
