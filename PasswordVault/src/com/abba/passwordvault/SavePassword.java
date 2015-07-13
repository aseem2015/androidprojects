package com.abba.passwordvault;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SavePassword extends ActionBarActivity {

	public final static String TAG = SavePassword.class.getName();
	
	private VaultDatabase db = new VaultDatabase(this);


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_password);
		getUserInput();
		handleLock();
	}

	private void handleLock() {
		
		
		Button lckBtn = (Button) findViewById(R.id.btnLock);
		lckBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "Saving password - locking screen");
				Intent i = new Intent(SavePassword.this,
						PasswordVault.class);
				startActivity(i);
			}
		});

	}
	
	private void getUserInput() {
		
		Button subBtn = (Button) findViewById(R.id.btnSubmit);
		subBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "Extracting data ....");
				String desc = "No description";
				EditText siteTxt = (EditText) findViewById(R.id.siteName);
				String site = siteTxt.getText().toString();
				Log.d(TAG, "Site :" + site);
				
				EditText passTxt = (EditText) findViewById(R.id.textp);
				String pass = passTxt.getText().toString();
				Log.d(TAG, "Password :" + pass);

				EditText descTxt = (EditText) findViewById(R.id.desc);
				desc = descTxt.getText().toString();
				Log.d(TAG, "Description :" + desc);
				
				if ((!site.trim().isEmpty()) && (!pass.trim().isEmpty())){
					
					VaultData data = new VaultData(site, pass, desc);
					Log.d(TAG , "saving Site/Password");
					saveSiteDetails(data);
				} else {
					
					Toast.makeText(SavePassword.this, "Password or Site is not filled!!",
							Toast.LENGTH_LONG).show();

				}
				
				


			}
		});
		
	}

	private void saveSiteDetails(final VaultData data) {

		AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}

				db.storePasswordData(data);
				Log.d(TAG , "Site/Password saved:");
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				Toast.makeText(SavePassword.this, "Saved Password",
						Toast.LENGTH_LONG).show();
			}
		};
		task.execute();

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save_password, menu);
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
