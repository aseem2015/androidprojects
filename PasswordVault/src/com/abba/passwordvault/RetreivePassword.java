package com.abba.passwordvault;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RetreivePassword extends ActionBarActivity {

	public final static String TAG = RetreivePassword.class.getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retreive_password);
		getPassword();
		handleLock();
		
	}

	
	private void handleLock() {
		Button lckBtn = (Button) findViewById(R.id.btnLock);
		lckBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "Retreiving password - locking screen");
				Intent i = new Intent(RetreivePassword.this,
						PasswordVault.class);
				startActivity(i);
			}
		});

	}


	private void getPassword() {
		Button subBtn = (Button) findViewById(R.id.btnSubmit);
		subBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "Retreiving password from site database");
				
				Log.d(TAG, "Extracting data ....");
				EditText siteTxt = (EditText) findViewById(R.id.siteName);
				String site = siteTxt.getText().toString();
				Log.d(TAG, "Site :" + site);
				
				if (!site.trim().isEmpty()){
					
				} else {
					Toast.makeText(RetreivePassword.this, "Site is not filled!!",
							Toast.LENGTH_LONG).show();
				}

			}
		});

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.retreive_password, menu);
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
