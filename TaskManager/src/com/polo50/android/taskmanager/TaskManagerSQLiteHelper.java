package com.polo50.android.taskmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskManagerSQLiteHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "task_db.sqlite";
	public static final int VERSION = 2;
	public static final String TASK_TABLE = "TASK_TBL";
	public static final String TASK_ID = "ID";
	public static final String TASK_NAME = "NM";
	public static final String TASK_COMPLETE = "CMPLT";
	public static final String TASK_ADDRESS = "ADDRESS";
	public static final String TASK_LATITUDE = "LATITUDE";
	public static final String TASK_LONGITUDE = "LONGITUDE";
	
	
	public TaskManagerSQLiteHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	/*
	 * Run on the first create of the DB.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);
	}

	
	private void createTable(SQLiteDatabase db) {
		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer
		.append("create table ").append(TASK_TABLE).append(" (")
		.append(TASK_ID).append(" integer primary key autoincrement not null,")
		.append(TASK_NAME).append(" text,")
		.append(TASK_COMPLETE).append(" text,")
		.append(TASK_ADDRESS).append(" text,")
		.append(TASK_LATITUDE).append(" real,")
		.append(TASK_LONGITUDE).append(" real);");
		
		db.execSQL(queryBuffer.toString());
	}

	/*
	 * Run on the DB changed version.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("alter table " + TASK_TABLE + " add column " + TASK_ADDRESS + " text");
		db.execSQL("alter table " + TASK_TABLE + " add column " + TASK_LATITUDE + " real");
		db.execSQL("alter table " + TASK_TABLE + " add column " + TASK_LONGITUDE + " real");
	}

}
