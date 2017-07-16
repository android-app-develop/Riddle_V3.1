package com.bingo.riddle.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bingo.riddle.model.Idiom;

public class DBIdiomManager {
	private DBHelper dbHelper = null;
	private SQLiteDatabase sdDatabase = null;
	private final String TABLE_NAME = "idiom";

	public DBIdiomManager(Context context) {
		dbHelper = new DBHelper(context);
		sdDatabase = dbHelper.getWritableDatabase();
		Log.i("test", "DBIdiomManager");
	}

	public void insert(Idiom idiom) {
		ContentValues cValues = new ContentValues();
		cValues.put("idiomDes", idiom.getIdiomDes());
		cValues.put("idiomKey", idiom.getIdiomKey());
		cValues.put("idiomKind", idiom.getIdiomKind());
		cValues.put("idiomRemark", idiom.getIdiomRemark());
		sdDatabase.insert(TABLE_NAME, null, cValues);
	}

	// 删除
	public boolean delIdiomById(int id) {
		int nt = sdDatabase.delete(TABLE_NAME, "idiomId=?",
				new String[] { String.valueOf(id) });
		return nt > 0 ? true : false;
	}

	// 按种类获取所有明细的列表
	public List<HashMap<String, String>> queryRAllById(String iId) {
		Cursor cursor = sdDatabase.query(TABLE_NAME, null, "idiomId=?",
				new String[] { iId }, null, null, null);
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("idiomId", cursor.getString(0));
			map.put("idiomDes", cursor.getString(1));
			map.put("idiomKey", cursor.getString(2));
			map.put("idiomKind", cursor.getString(3));
			map.put("idiomRemark", cursor.getString(4));

			resultList.add(map);
		}

		return resultList;
	}

	// 按种类获取所有明细的列表
	public List<HashMap<String, String>> queryAllByKind(String iKind) {
		Cursor cursor = sdDatabase.query(TABLE_NAME, null, "idiomKind=?",
				new String[] { iKind }, null, null, null);
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("idiomId", cursor.getString(0));
			map.put("idiomDes", cursor.getString(1));
			map.put("idiomKey", cursor.getString(2));
			map.put("idiomKind", cursor.getString(3));
			map.put("idiomRemark", cursor.getString(4));

			resultList.add(map);
		}

		return resultList;
	}

	public boolean idValidIdiomKey(String ides, String ikey) {
		Log.i("text", "ki" + ides);
		Log.i("text", "ki" + ikey);
		Cursor cuserCursor = sdDatabase.query(TABLE_NAME, null, "idiomDes=?",
				new String[] { ides }, null, null, null);

		Log.i("text", "cuserCursor" + cuserCursor);
		if (cuserCursor.getCount() > 0) {
			cuserCursor.moveToNext();
			Log.i("text", "ki ikey" + String.valueOf(cuserCursor.getString(2)));
			if (ikey.trim().equals(cuserCursor.getString(2).trim()))
				return true;
			else {
				return false;
			}
		} else {
			return false;
		}

	}
}
