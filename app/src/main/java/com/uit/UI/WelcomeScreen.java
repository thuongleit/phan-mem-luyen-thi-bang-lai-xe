package com.uit.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.uit.R;
import com.uit.Providers.Database;

public class WelcomeScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_screen);

		Thread logoTimer = new Thread() {
			public void run() {
				try {
					long logoTimer = 0;
					while (logoTimer < 5000) {
						sleep(100);
						logoTimer += 100;
					}
					startMain();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		};
		logoTimer.start();
		Database db = new Database(this);
		db.OpenDatabase();

	}

	private void startMain() {
		Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.gc();
	}
}
