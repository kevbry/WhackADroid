package com.siast.whackabot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class ResultsSubmitter extends AsyncTask<Void, Void, Void>
{
	private static final String NAME_FIELD="entry.528351823";
	private static final String SCORE_FIELD="entry.85424877";
	private static final String PAGE_HISTORY_FIELD="pageHistory";
	private static final String DRAFT_RESPONSE_FIELD="draftResponse";
	private static final String SUBMIT_FIELD="submit";
	
	private static final String FORM_URL="https://docs.google.com/forms/d/1wa2aH9l01MnejMOnFTONb9Lr6BUm9xS0MICcThNA82Y/formResponse";
	
	private String username;
	private String score;
	
	public ResultsSubmitter(String name, String score)
	{
		this.username=name;
		this.score=score;
	}
	
	
	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		try
		{
		this.submitVote();
		}
		catch(Exception e)
		{
			
		}
		return null;
	}

	
	private void submitVote() throws ClientProtocolException, IOException
	{
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(FORM_URL);
		
		List<BasicNameValuePair> results = new ArrayList<BasicNameValuePair>();
		results.add(new BasicNameValuePair(NAME_FIELD, this.username));
		results.add(new BasicNameValuePair(SCORE_FIELD, this.score));
		
		results.add(new BasicNameValuePair(DRAFT_RESPONSE_FIELD, "[]"));
		results.add(new BasicNameValuePair(PAGE_HISTORY_FIELD, "0"));
		results.add(new BasicNameValuePair(SUBMIT_FIELD, "Submit"));
		
		post.setEntity(new UrlEncodedFormEntity(results));
		
		client.execute(post);
		
	}
	
}
