package com.siast.whackabot;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;


public class Droid implements View.OnClickListener
{
	private static final int HAPPY_IMG = R.drawable.happy;
	private static final int MAD_IMG = R.drawable.angry;
	private static final int ANIMATION_DURATION=500;
	
	private ImageButton button;
	private boolean isAngry = false;
	private boolean frozen = true;
	private Animation happyAnimation;
	
	public Droid(ImageButton button)
	{
		this.button = button;
		
		button.setOnClickListener(this);
		
		this.happyAnimation = new RotateAnimation(0.0f,359.9f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		this.happyAnimation.setDuration(ANIMATION_DURATION);
	}
	
	public void becomeAngry()
	{
		this.isAngry = true;
		
		this.button.setImageResource(MAD_IMG);
	}
	
	public void becomeHappy()
	{
		if(this.isAngry)
		{
			this.isAngry = false;
			
			this.button.setImageResource(HAPPY_IMG);
			this.button.startAnimation(this.happyAnimation);
		}
	}
	
	public boolean isAngry()
	{
		return this.isAngry;
	}

	@Override
	public void onClick(View v) 
	{
		if(!this.frozen)
		{
			this.becomeHappy();
		}
	}
	
	public void reset()
	{
		this.isAngry = false;
		
		this.button.setImageResource(HAPPY_IMG);
		
		this.frozen=false;
	}
	
	public void pause()
	{
		this.frozen = true;
	}

	public void resume()
	{
		this.frozen = false;
	}
}
