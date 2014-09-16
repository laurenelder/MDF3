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
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
	ImageView image;
	ProgressBar progress;
	Integer trackProgress = 0;
	CountDownTimer timer;
	private onSelectedButton parentActivity;
	
	// Set interface to parentActivity
	public interface onSelectedButton {
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
		
		// Establish connection to UI Elements
        previousButton = (Button)view.findViewById(R.id.previousBtn);
        repeatButton = (Button)view.findViewById(R.id.repeatBtn);
        playButton = (Button)view.findViewById(R.id.playBtn);
        stopButton = (Button)view.findViewById(R.id.stopBtn);
        nextButton = (Button)view.findViewById(R.id.nextBtn);
        songName = (TextView)view.findViewById(R.id.songTitle);
        image = (ImageView)view.findViewById(R.id.thumbnailImage);
        progress = (ProgressBar)view.findViewById(R.id.progressBar);
        
        // Stop timer during orientation change
        if (timer != null) {
        	stopTimer("dontRepeat");
        }
        
        // Set OnClickListeners for Audio Buttons
        previousButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// Call Handler Method to play previous track
				parentActivity.buttonClick("previous");
				if (timer != null) {
					stopTimer("dontRepeat");
				}
			}
        	
        });
        repeatButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parentActivity.buttonClick("repeat");
			}
        
        });
        playButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				/* Call Handler Method to play/pause track. Conditional 
				 * changes button image based on whether or not media is playing.
				 */
				parentActivity.buttonClick("play");
			}
        });
        stopButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// Call stopService to stop and release media player service.

				Log.i("MainActivity", "Service Player stopped");
				playButton.setBackgroundResource(R.drawable.ic_action_play);
				parentActivity.buttonClick("stop");
				if (timer != null) {
					stopTimer("dontRepeat");
				}
			}
        	
        });
        nextButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// Call Handler Method to play next track.
				parentActivity.buttonClick("next");
				if (timer != null) {
					stopTimer("dontRepeat");
				}
			}
        	
        });
		
		return view;
	}
	
	// On view destroy stop timer
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (timer != null) {
			stopTimer("dontRepeat");
		}
		super.onDestroy();
	}
	
	/* StopTimer method stops timer unless the player is on repeat.
	 * If player is on repeat the timer is reset.
	 */
	public void stopTimer(String doThis) {
		if (doThis.matches("repeat")) {
			progress.setProgress(0);
			trackProgress = 0;
			timer.start();
		} else {
			timer.cancel();
			timer = null;
			progress.setProgress(0);
		}
	}
	
	/* updateUI method updates song title, UI image, repeat button
	 * alpha, progress bar, and sets the timer for the progress bar.
	 */
	public void updateUI(final ArrayList<String> data) {
		
		songName.setText(data.get(0).toString());
		
		if (data.get(1).toString().matches("crystallize")) {
			image.setImageResource(R.drawable.crystallize);
		}
		if (data.get(1).toString().matches("elements")) {
			image.setImageResource(R.drawable.elements);
		}
		if (data.get(1).toString().matches("zeldamedley")) {
			image.setImageResource(R.drawable.zeldamedley);
		}
		if (data.get(2).toString().matches("repeating")) {
			repeatButton.setAlpha((float) 0.5);
		} else {
			repeatButton.setAlpha(1);
		}
		if (data.get(3).toString().matches("playing")) {
			playButton.setBackgroundResource(R.drawable.ic_action_pause);
		} else {
			playButton.setBackgroundResource(R.drawable.ic_action_play);
			timer.cancel();
			timer = null;
			progress.setProgress(trackProgress);
		}
		if (!data.get(4).toString().matches("none") && !data.get(4).toString().matches("none")) {
			if (timer == null) {
				String durationStr = data.get(4).toString();
				Integer durationNum = Integer.parseInt(durationStr);
				String progressStr = data.get(5).toString();
				Integer progressNum = Integer.parseInt(progressStr);
				Log.i("MainFragment", durationNum.toString());
				Log.i("MainFragment", progressNum.toString());
				
				if (data.get(6).toString().matches("true") && timer != null) {
					Log.i("MainFragment", "New Track Playing");
					trackProgress = 0;
					timer.cancel();
					timer = null;
					progress.setProgress(0);
					progress.setMax(durationNum / 1000);
				} else {
					trackProgress = progressNum / 1000;
					progress.setMax(durationNum / 1000);
				}
				
				timer = new CountDownTimer((durationNum - progressNum), 1000) {

					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						trackProgress++;
						progress.setProgress(trackProgress);
						Log.i("MainFragment", trackProgress.toString());
					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						/* If player is repeating then the timer and progress bar
						 * are reset. If the player isn't repeating the timer is canceled.
						 */
						if (data.get(2).toString().matches("repeating")) {
							stopTimer("repeat");
						} else {
							stopTimer("dontRepeat");
						}
					} 
				}.start();
			}
		}
	}
}