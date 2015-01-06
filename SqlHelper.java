package com.spsigt.kalender4g3s2015;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "lemburan.db";
	private static final int DATABASE_VERSION = 1;

	public SQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table catat( id integer primary key autoincrement, nama text null, keterangan text null, shift text null, status text null);";
		Log.d("Data", "onCreate: " + sql);
		db.execSQL(sql);
		// sql =
		// "INSERT INTO catat (id, nama, keterangan) VALUES (1, 'sarimi', 'beli sarimi');";
		// db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
