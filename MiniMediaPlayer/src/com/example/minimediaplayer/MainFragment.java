package com.example.minimediaplayer;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainFragment extends Fragment{
	
	// Establish Variables
	Context context;
	boolean play;
	Button previousButton;
	Button repeatButton;
	Button playButton;
	Button stopButton;
	Button nextButton;
	TextView songName;
	private onSelectedButton parentActivity;
	
	public interface onSelectedButton {
//		public void serviceHandler(String intentAction);
//		public void stopService();
		public void buttonClick(String selectedButton);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		context = getActivity();
		
		if(activity instanceof onSelectedButton) {
			parentActivity = (onSelectedButton) activity;

		} else {
			throw new ClassCastException((activity.toString()) + 
					"Did not impliment onSelectedButton interface");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.activity_main, container);
		
		play = true;
        previousButton = (Button)view.findViewById(R.id.previousBtn);
        repeatButton = (Button)view.findViewById(R.id.repeatBtn);
        playButton = (Button)view.findViewById(R.id.playBtn);
        stopButton = (Button)view.findViewById(R.id.stopBtn);
        nextButton = (Button)view.findViewById(R.id.nextBtn);
        songName = (TextView)view.findViewById(R.id.songTitle);
        
        // Set OnClickListeners for Audio Buttons
        previousButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// Call Handler Method to play previous track
				parentActivity.buttonClick("previous");
				playButton.setBackgroundResource(R.drawable.ic_action_pause);
				play = false;
			}
        	
        });
        repeatButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				playerHandler("repeat");
				parentActivity.buttonClick("repeat");
			}
        
        });
        playButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				/* Call Handler Method to play/pause track. Conditional 
				 * changes button image based on whether or not media is playing.
				 */
//				Log.i("MainActivity", "Play button clicked");
				parentActivity.buttonClick("play");
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
				parentActivity.buttonClick("stop");
			}
        	
        });
        nextButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// Call Handler Method to play next track.
				parentActivity.buttonClick("next");
				playButton.setBackgroundResource(R.drawable.ic_action_pause);
				play = false;
			}
        	
        });
		
		return view;
	}
	
	public void updateUI(boolean pBtnStatus, String songTitle) {
		if (!pBtnStatus) {
			play = false;
			playButton.setBackgroundResource(R.drawable.ic_action_pause);
		}
		if (songTitle != null) {
			songName.setText(songTitle.toString());
		}
	}
}