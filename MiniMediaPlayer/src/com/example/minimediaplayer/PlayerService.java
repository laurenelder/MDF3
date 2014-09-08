/* Name: Devin "Lauren" Elder
 * Date: 09/07/2014
 * Term: 1409
 * Project Name: Mini Media Player
 * Assignment: MDF3 Week 1
 */

package com.example.minimediaplayer;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class PlayerService extends Service{
	
	// Establish Variables
	public String state = "stopped";
	public String newState = "unused";
	MediaPlayer player;
	int trackNum;
	boolean isPrepared = false;;
	ArrayList<String> resources;
	public static final String MESSENGER_KEY = "messenger";
	Intent thisIntent;
//	BoundServiceBinder binder;
	public static final int STANDARD_NOTIFICATION = 0x01001;
	NotificationManager notifyMgr;
	NotificationCompat.Builder notifyBuilder;
	String formattedTitle;
	Bundle extras;
	Messenger theMessenger;
	Message message;
	Notification notification;
	
	/* Called when the user selects the stop button.
	 * This method stops and releases the media player service.
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		notifyMgr.cancelAll();
		player.stop();
		player.reset();
		player.release();
		super.onDestroy();
	}
	
	/* onCreate method is called when the user selects the play, previous,
	 * or next buttons when the service is not yet created. The media player,
	 * notifications, and media resources are initialized in this method. Media
	 * player listeners are also created in this method.
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
//		binder = new BoundServiceBinder();
		
		// Set up notification
		notifyMgr = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		notifyBuilder = new NotificationCompat.Builder(this);
		notifyBuilder.setSmallIcon(R.drawable.ic_notification);
		notifyBuilder.setLargeIcon(BitmapFactory.decodeResource(
			getResources(), R.drawable.ic_launcher));
		Intent notificationIntent = new Intent(this, MainActivity.class);
		PendingIntent clickIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notifyBuilder.setContentIntent(clickIntent);

		state = "active";
//		Log.i("PlayerService", "Player Service created");
		
		// Set up resources
		resources = new ArrayList<String>();
		resources.add("android.resource://com.example.minimediaplayer/raw/lindsey_stirling0crystallize");
		resources.add("android.resource://com.example.minimediaplayer/raw/lindsey_stirling0elements");
		resources.add("android.resource://com.example.minimediaplayer/raw/lindsey_stirling0zelda_medley");
		
		// Create media player
		if(player == null) {
			player = new MediaPlayer();
			isPrepared = false;
		}
		
		// Set Player OnPrepared Listener
		player.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				isPrepared = true;
				mp.start();
				if(player.isPlaying()) {
					
					// Format track name for display in UI
					String unformattedTitle = resources.get(trackNum)
							.substring(resources.get(trackNum).lastIndexOf("0") + 1);
					unformattedTitle = unformattedTitle.replace("_", " ");
//					unformattedTitle = unformattedTitle.replace("0", " - ");
					formattedTitle = unformattedTitle.substring(0, 1).toUpperCase() + 
							unformattedTitle.substring(1);
					Log.i("PlayerService", unformattedTitle.toString());
					
					// Finalize and start notification
					notifyBuilder.setContentTitle("Playing Music");
					notifyBuilder.setContentText(formattedTitle.toString());
					notification = notifyBuilder.build();
					startForeground(STANDARD_NOTIFICATION, notification);
					
					/* Set up service side handler to allow sending the track name
					 * to MainActivity for display in UI
					 */
					extras = thisIntent.getExtras();
					theMessenger = (Messenger)extras.get(MESSENGER_KEY);
					message = Message.obtain(); 
					message.arg1 = Activity.RESULT_OK; 
					message.arg2 = 0;
					message.obj = formattedTitle; 
					if (theMessenger != null) { 
						try { 
							theMessenger.send(message); 
							} 
						catch (RemoteException e) 
						{ 
							e.printStackTrace(); 
						} 
					}
				}
			}

		});
		
		// Set Player OnCompletion Listener
		player.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// Add auto skip functionality when previous track is over
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

	/* The onStartCommand method is called when the user selects the previous,
	 * play, and next buttons. Conditionals are in place so that the appropriate
	 * actions take place depending on which button is selected.
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String input = intent.getAction();
		thisIntent = intent;
		
		/* This conditional is hit when the application is loaded 
		 * from the notification bar. The track information is then
		 * sent back to the MainActivity to update the UI.
		 */
		if (input.matches("ACTION_INFO") && player.isPlaying()) {
			extras = thisIntent.getExtras();
			theMessenger = (Messenger)extras.get(MESSENGER_KEY);
			message = Message.obtain(); 
			message.arg1 = Activity.RESULT_OK; 
			message.arg2 = 1;
			message.obj = formattedTitle; 
			if (theMessenger != null) { 
				try { 
					theMessenger.send(message); 
					} 
				catch (RemoteException e) 
				{ 
					e.printStackTrace(); 
				} 
			}
		}
		
		/* This conditional is hit when the user selects the play button.
		 * If the media is not playing the default track is loaded and prepared.
		 * If the media is already playing then the media play back is paused till
		 * the user selects the play button again.
		 */
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

		/* This conditional is hit when the user selects the previous
		 * button. The previous track is loaded and prepared.
		 */
		if(input.matches("ACTION_PREVIOUS")) {
			Log.i("PlayerService", "Player Service previous track");
			if (trackNum > 0) {
				trackNum = trackNum - 1;
			}
			player.stop();
			player.reset();
			
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
		
		/* This conditional is hit when the user selects the next
		 * button. The next track is loaded and prepared.
		 */
		if(input.matches("ACTION_NEXT")) {
			Log.i("PlayerService", "Player Service next track");
			if (trackNum < (resources.size() - 1)) {
				trackNum = trackNum + 1;
			}
			player.stop();
			player.reset();
			
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
		return Service.START_STICKY;
	}
	
/*	@Override
	public boolean onUnbind(Intent intent) {
//		Toast.makeText(this, "Service Unbound", Toast.LENGTH_SHORT).show();
		return super.onUnbind(intent);
	}*/

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
