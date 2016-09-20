package com.lzg.strongservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lzg.strongservice.service.Service1;
import com.lzg.strongservice.service.Service2;

/**
 * http://blog.csdn.net/liuzg1220
 * 
 * @author henry
 *
 */
public class MainActivity extends BaseActivity {

	private Button btn1;

	// // AIDL,此处用于bindService
	// private String TAG = getClass().getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn1 = getViewById(R.id.button1);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent i1 = new Intent(MainActivity.this, Service1.class);
			startService(i1);

			Intent i2 = new Intent(MainActivity.this, Service2.class);
			startService(i2);
			break;
		}
	}

}
