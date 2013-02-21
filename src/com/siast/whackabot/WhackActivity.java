package com.siast.whackabot;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class WhackActivity extends Activity implements GameContext {
	
	public static final String SCORE_FORMAT="Score: %d";
	
	private boolean is_paused=true;
	private Handler tickHandler;
	private GameLogic game_logic;
	
	private TextView score;
	private Button pause_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_whack);
	
		this.score = (TextView)this.findViewById(R.id.score);
		this.pause_button = (Button)this.findViewById(R.id.pause_game);
		
		this.tickHandler = new Handler(); //Because Handler is created in the UI thread its runnables will run in the UI thread
		
		this.game_logic = new WhackADroidLogic(this.getDroidList(), this);
	}
	
	@Override
	protected void onPause()
	{
		this.pauseGame();
		super.onPause();
	}
	
	@Override 
	protected void onStop()
	{
		this.pauseGame();
		super.onStop();
	}
	
	private ArrayList<Droid> getDroidList()
	{
		ArrayList<Droid> droids = new ArrayList<Droid>();
		
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot1)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot2)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot3)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot4)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot5)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot6)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot7)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot8)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot9)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot10)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot11)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot12)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot13)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot14)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot15)));
		droids.add(new Droid((ImageButton)this.findViewById(R.id.robot16)));
		
		return droids;
	}

	public void togglePause(View actionView)
	{
		
		if(this.is_paused) //Game should become active
		{
			this.resumeGame();
		}
		else //Game should become paused
		{
			this.pauseGame();
		}
	}
	 
	private void pauseGame()
	{
		this.is_paused = true;
		
		this.pause_button.setText(R.string.resume);
		
		this.tickHandler.removeCallbacks(this.game_logic);
		
		this.game_logic.pause();
	}
	
	private void resumeGame()
	{
		this.is_paused = false;
		
		this.tickHandler.removeCallbacks(this.game_logic);

		this.pause_button.setText(R.string.pause);
		
		this.game_logic.resume();
		
		this.tickHandler.postDelayed(this.game_logic, this.game_logic.getRequestedTickRate());
	}
	
	public void resetGame(View action)
	{		
		this.pauseGame();

		this.game_logic.reset();
		
		this.pause_button.setVisibility(View.VISIBLE);
		this.pause_button.setText(R.string.start);
		this.score.setText(String.format(SCORE_FORMAT, this.game_logic.getCurrentScore()));
	}

	@Override
	public void game_ended() 
	{
		this.pauseGame();
		
		Toast.makeText(this, R.string.lose_message, Toast.LENGTH_LONG).show();
		
		this.pause_button.setVisibility(View.INVISIBLE);
	}

	@Override
	public void tick_complete() 
	{
		this.score.setText(String.format(SCORE_FORMAT, this.game_logic.getCurrentScore()));
		if(!this.is_paused)
		{
			//Reschedule next tick
			this.tickHandler.postDelayed(this.game_logic, this.game_logic.getRequestedTickRate());
		}	
	}
}
