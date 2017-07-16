package com.bingo.util;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;

import com.bingo.lantern.R;

public class MyDialog {
	public static void myDialog(Context context, String message) {

		final AlertDialog adRef = new AlertDialog.Builder(context,
				R.style.CustomAlertDialog).create();
		// adRef.setTitle("ÌבÊ¾");
		adRef.setMessage(message);
		adRef.show();

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			public void run() {
				adRef.dismiss();
			}
		}, 2000);
	}
}
