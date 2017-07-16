package com.bingo.riddle.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bingo.riddle.model.Proverb;

public class DBProverbManager {
	private DBHelper dbHelper = null;
	private SQLiteDatabase sdDatabase = null;
	private final String TABLE_NAME = "proverb";

	public DBProverbManager(Context context) {
		dbHelper = new DBHelper(context);
		sdDatabase = dbHelper.getWritableDatabase();
		Log.i("test", "DBProverbManager");
	}

	public void insert(Proverb proverb) {
		ContentValues cValues = new ContentValues();
		cValues.put("proverbDes", proverb.getProverbDes());
		cValues.put("proverbKey", proverb.getProverbKey());
		cValues.put("proverbKind", proverb.getProverbKind());
		cValues.put("proverbRemark", proverb.getProverbRemark());
		sdDatabase.insert(TABLE_NAME, null, cValues);
	}

	// 删除
	public boolean delProverbById(int id) {
		int nt = sdDatabase.delete(TABLE_NAME, "proverbId=?",
				new String[] { String.valueOf(id) });
		return nt > 0 ? true : false;
	}

	// 按种类获取所有明细的列表
	public List<HashMap<String, String>> queryRAllById(String pId) {
		Cursor cursor = sdDatabase.query(TABLE_NAME, null, "proverbId=?",
				new String[] { pId }, null, null, null);
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("proverbId", cursor.getString(0));
			map.put("proverbDes", cursor.getString(1));
			map.put("proverbKey", cursor.getString(2));
			map.put("proverbKind", cursor.getString(3));
			map.put("proverbRemark", cursor.getString(4));

			resultList.add(map);
		}

		return resultList;
	}

	// 按种类获取所有明细的列表
	public List<HashMap<String, String>> queryAllByKind(String pKind) {
		Cursor cursor = sdDatabase.query(TABLE_NAME, null, "proverbKind=?",
				new String[] { pKind }, null, null, null);
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("proverbId", cursor.getString(0));
			map.put("proverbDes", cursor.getString(1));
			map.put("proverbKey", cursor.getString(2));
			map.put("proverbKind", cursor.getString(3));
			map.put("proverbRemark", cursor.getString(4));

			resultList.add(map);
		}

		return resultList;
	}

	public boolean idValidProverbKey(String pdes, String pkey) {
		Log.i("text", "ki" + pdes);
		Log.i("text", "ki" + pkey);
		Cursor cuserCursor = sdDatabase.query(TABLE_NAME, null, "proverbDes=?",
				new String[] { pdes }, null, null, null);

		Log.i("text", "cuserCursor" + cuserCursor);
		if (cuserCursor.getCount() > 0) {
			cuserCursor.moveToNext();
			Log.i("text", "ki pkey" + String.valueOf(cuserCursor.getString(2)));
			if (pkey.trim().equals(cuserCursor.getString(2).trim()))
				return true;
			else {
				return false;
			}
		} else {
			return false;
		}

	}
}
