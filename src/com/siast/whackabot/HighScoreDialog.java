package com.siast.whackabot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

public class HighScoreDialog extends DialogFragment implements DialogInterface.OnClickListener
{
	private int score = 0;
	//private Dialog dialog;
	private HighScoreSubmissionDelegate delegate;
	private ResultsSubmitter submitter;
	
	public static HighScoreDialog newInstance(int score, HighScoreSubmissionDelegate delegate)
	{
		HighScoreDialog dialog = new HighScoreDialog();
		dialog.score = score;
		dialog.delegate = delegate;
		return dialog;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		//this.dialog=null;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
		builder.setIcon(android.R.drawable.ic_menu_save);
		builder.setTitle(R.string.score_title);
		builder.setView(this.getActivity().getLayoutInflater().inflate(R.layout.high_score, null));
		builder.setPositiveButton(R.string.submit_button, this);
		builder.setNegativeButton(R.string.cancel_button, this);
		
		return builder.create();
	}


	@Override
	public void onClick(DialogInterface dialog, int which) 
	{
		switch(which)
		{
			case DialogInterface.BUTTON_POSITIVE:
				String name = ((EditText)((Dialog)dialog).findViewById(R.id.name)).getText().toString();
				if(this.submitter != null)
				{
					this.submitter.cancel(true);
				}
				this.submitter = new ResultsSubmitter(name, this.score, this.delegate);
				this.submitter.execute();
				break;
				
			case DialogInterface.BUTTON_NEGATIVE:
				this.dismiss();
				break;
		}
	}
}
