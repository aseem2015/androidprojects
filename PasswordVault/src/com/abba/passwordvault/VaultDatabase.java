package com.abba.passwordvault;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class VaultDatabase extends SQLiteOpenHelper {
	
	public final static String TAG = VaultDatabase.class.getName();


	private static final String PASSWORD_TABLE = "VAULTPWD";
	private static final String COL_ID = "ID";
	private static final String COL_NAME = "NAME";
	private static final String COL_PASSWORD = "PASSWORD";
	private static final String COL_DESCRIPTION = "DESCRIPTION";

	public VaultDatabase(Context context) {
		super(context, "sitepassword.db", null, 1);
		Log.d(TAG,"cREATED DATABASE ...");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String
				.format("create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL)",
						PASSWORD_TABLE, COL_ID, COL_NAME, COL_PASSWORD, COL_DESCRIPTION);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

	public void storePasswordData(VaultData data) {
		SQLiteDatabase db = getWritableDatabase();

		Log.d(TAG, " about to store data: " + data.toString());
		ContentValues values = new ContentValues();
		values.put(COL_NAME, data.getName());
		values.put(COL_PASSWORD, data.getPassword());
		values.put(COL_DESCRIPTION, data.getDescription());

		db.insert(PASSWORD_TABLE, null, values);
		db.close();
	}

	public String getPassword(String site) {
		
		String result = "ERROR NO RECORDS!!";
		SQLiteDatabase db = getReadableDatabase();

		String sql = String.format("SELECT %s, %s FROM %s WHERE COL_NAME = %s", 
				COL_PASSWORD,COL_DESCRIPTION, PASSWORD_TABLE, site);
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor !=null) {
        result = cursor.getString(cursor.getColumnIndex(COL_PASSWORD));
        String description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION));
		}
		db.close();
		
		return result;
	}
}
