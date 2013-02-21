package com.siast.whackabot;

public abstract class GameLogic implements Runnable
{
	public static final int DEFAULT_TICK_RATE=1400;

	protected int tick_rate=DEFAULT_TICK_RATE;
	
	protected GameContext context;

	public GameLogic(GameContext context) 
	{
		this.context = context;
	}
	
	public abstract int getCurrentScore();
	public abstract void runLogic();
	public abstract void pause();
	public abstract void resume();
	
	public int getRequestedTickRate()
	{
		return this.tick_rate;
	}
	
	public void reset()
	{
		this.tick_rate = DEFAULT_TICK_RATE;
	}
	
	public final void run()
	{
		this.runLogic();
		this.context.tick_complete();
	}
}
