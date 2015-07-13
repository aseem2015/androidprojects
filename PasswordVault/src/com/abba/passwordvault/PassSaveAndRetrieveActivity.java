package com.abba.passwordvault;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PassSaveAndRetrieveActivity extends ActionBarActivity {
	
	public final static String TAG = PassSaveAndRetrieveActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pass_save_and_retrieve);
		savePaswordListener();
		retrivePasswordListener();
	}

	private void retrivePasswordListener() {
		Button rtvBtn = (Button) findViewById(R.id.retreivePassword);
		rtvBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "Retreiving password - about to fire intent");
				Intent i = new Intent(PassSaveAndRetrieveActivity.this,
						RetreivePassword.class);
				startActivity(i);
			}
		});

	}

	private void savePaswordListener() {
		Button savBtn = (Button) findViewById(R.id.savePassword);
		savBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "Saving password -about to fire intent");
				Intent i = new Intent(PassSaveAndRetrieveActivity.this,
						SavePassword.class);
				startActivity(i);
			}
		});

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pass_save_and_retrieve, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
