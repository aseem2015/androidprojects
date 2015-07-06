package com.abba.passwordvault;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class PasswordVault extends ActionBarActivity implements
		PointCollectorListener {

	private static final String PASSWORD_SET = "PASSWORD_SET";
	private static final int POINT_CLOSENESS = 40;
	private PointCollector pointCollector = new PointCollector();
	private PasswordDatabase db = new PasswordDatabase(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_vault);

		addTouchListener();

		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		Boolean passPointsSet = prefs.getBoolean(PASSWORD_SET, false);

		if (!passPointsSet) {
			showSetPasspointsPrompt();
		}
		pointCollector.setListener(this);
	}

	private void showSetPasspointsPrompt() {
		AlertDialog.Builder builder = new Builder(this);

		builder.setPositiveButton("OK", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		builder.setTitle("Create Your Password");
		builder.setMessage("Touch four points on the image to set the password sequence. You must click the same points in future to gain access to the main screen");

		AlertDialog dlg = builder.create();
		dlg.show();
	}

	private void addTouchListener() {
		ImageView image = (ImageView) findViewById(R.id.touch_image);
		image.setOnTouchListener(pointCollector);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.password_vault, menu);
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

	private void savePassPoints(final List<Point> points) {
		// Toast.makeText(this, "Saved 4 points", Toast.LENGTH_LONG).show();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.storing_pass_points);

		final AlertDialog dlg = builder.create();
		dlg.show();

		AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}

				db.storePasswordPoints(points);
				Log.d(MainActivity.DEBUGTAG, "Points saved:");
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				SharedPreferences prefs = getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();

				editor.putBoolean(PASSWORD_SET, true);
				editor.commit();

				pointCollector.clear();
				dlg.dismiss();
			}
		};
		task.execute();
	}

	private void verifyPassPoints(final List<Point> touchedPoints) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Checking passpoints.....");

		final AlertDialog dlg = builder.create();
		dlg.show();

		AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				List<Point> savedPoints = db.getPoints();
				if (savedPoints.size() != PointCollector.NUM_POINTS
						|| touchedPoints.size() != PointCollector.NUM_POINTS) {
					return false;
				}
				// Go over all the points and compare between the saved and the touched points
				// Apply pythagorus theorem and check if the touched points are acceptable
				for (int i=0; i<PointCollector.NUM_POINTS; i++) {
					Point savedPoint = savedPoints.get(i);
					Point touchedPoint = touchedPoints.get(i);
					
					int xDiff = savedPoint.x - touchedPoint.x;
					int yDiff = savedPoint.y - touchedPoint.y;
					
					int diffSquared = xDiff * xDiff + yDiff * yDiff;
					if (diffSquared > POINT_CLOSENESS * POINT_CLOSENESS) {
						return false;
					}		
				}
				return true;
			}

			@Override
			protected void onPostExecute(Boolean pass) {
				dlg.dismiss();
				pointCollector.clear();

				if (pass == true) {
					Intent i = new Intent(PasswordVault.this,
							PassSaveAndRetrieveActivity.class);
					startActivity(i);
				} else {
					Toast.makeText(PasswordVault.this, "Access denied",
							Toast.LENGTH_LONG).show();
				}
			}
		};

		task.execute();
	}

	@Override
	public void pointsCollected(final List<Point> points) {
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		Boolean passPointsSet = prefs.getBoolean(PASSWORD_SET, false);

		if (!passPointsSet) {
			Toast.makeText(this, "Saved 4 points", Toast.LENGTH_LONG).show();
			savePassPoints(points);
		} else {
			Toast.makeText(this, "Verify 4 points", Toast.LENGTH_LONG).show();
			verifyPassPoints(points);
		}
	}
}
