package com.siast.whackabot;

import java.util.ArrayList;

class GameLogicHandler implements Runnable
{
	private ArrayList<Droid> all_droids;
	private GameContext context;
	
	public GameLogicHandler(ArrayList<Droid> droids, GameContext context)
	{
		this.all_droids = droids;
		this.context = context;
	}
	
	public void pause()
	{
		for (Droid droid : this.all_droids) 
		{
			droid.pause();
		}
	}
	
	public void resume()
	{
		for (Droid droid : this.all_droids) 
		{
			droid.resume();
		}
	}
	
	public void reset()
	{
		for (Droid droid : this.all_droids) 
		{
			droid.reset();
		}
	}
	
	@Override
	public void run() {
		boolean popped = false;
		int attempts = 0;
		while(attempts++ < 8 && !popped)
		{
			int index =  (int)Math.floor(Math.random() * 16);
			Droid droid = this.all_droids.get(index);
			if(!droid.isAngry())
			{
				popped = true;
				droid.becomeAngry();
			}
		}
		
		int angry_count = 0;
		for (Droid current_droid : this.all_droids) 
		{
			if(current_droid.isAngry())
			{
				angry_count++;
			}
		}
		if(angry_count > WhackActivity.MAX_ANGRY_DROIDS)
		{
			this.context.game_ended();
		}
		else
		{
			this.context.tick_complete();
		}
	}
	
	
	
}