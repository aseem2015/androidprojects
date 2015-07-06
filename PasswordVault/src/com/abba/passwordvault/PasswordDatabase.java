package com.abba.passwordvault;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;

public class PasswordDatabase extends SQLiteOpenHelper {
	private static final String POINTS_TABLE = "POINTS";
	private static final String COL_ID = "ID";
	private static final String COL_X = "X";
	private static final String COL_Y = "Y";

	public PasswordDatabase(Context context) {
		super(context, "password.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String
				.format("create table %s (%s INTEGER PRIMARY KEY, %s INTEGER NOT NULL, %s INTEGER NOT NULL)",
						POINTS_TABLE, COL_ID, COL_X, COL_Y);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

	// This function stores the point coordinates that are clicked by the user
	public void storePasswordPoints(List<Point> points) {
		SQLiteDatabase db = getWritableDatabase();

		db.delete(POINTS_TABLE, null, null);

		int pointCounter = 0;
		for (Point point : points) {
			ContentValues values = new ContentValues();
			values.put(COL_ID, pointCounter);
			values.put(COL_X, point.x);
			values.put(COL_Y, point.y);

			db.insert(POINTS_TABLE, null, values);
			pointCounter++;
		}
		db.close();
	}

	// This function retrieves the point coordinates from the database
	public List<Point> getPoints() {
		List<Point> points = new ArrayList<Point>();
		SQLiteDatabase db = getReadableDatabase();

		String sql = String.format("SELECT %s, %s FROM %s ORDER BY %s", COL_X,
				COL_Y, POINTS_TABLE, COL_ID);
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			int x = cursor.getInt(0);
			int y = cursor.getInt(1);
			
			points.add(new Point(x, y));
		}
		db.close();
		
		return points;
	}
}
