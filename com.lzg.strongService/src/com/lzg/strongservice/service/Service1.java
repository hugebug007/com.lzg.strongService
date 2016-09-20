package com.lzg.strongservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.widget.Toast;

import com.lzg.strongservice.utils.Utils;

/**
 * 
 * @author henry
 *
 */
public class Service1 extends Service {
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				startService2();
				break;

			default:
				break;
			}

		};
	};

	/**
	 * 使用aidl 启动Service2
	 */
	private StrongService startS2 = new StrongService.Stub() {
		@Override
		public void stopService() throws RemoteException {
			Intent i = new Intent(getBaseContext(), Service2.class);
			getBaseContext().stopService(i);
		}

		@Override
		public void startService() throws RemoteException {
			Intent i = new Intent(getBaseContext(), Service2.class);
			getBaseContext().startService(i);
		}
	};

	/**
	 * 在内存紧张的时候，系统回收内存时，会回调OnTrimMemory， 重写onTrimMemory当系统清理内存时从新启动Service2
	 */
	@Override
	public void onTrimMemory(int level) {
		/*
		 * 启动service2
		 */
		startService2();

	}

	@Override
	public void onCreate() {
		Toast.makeText(Service1.this, "Service1 正在启动...", Toast.LENGTH_SHORT)
				.show();
		startService2();
		/*
		 * 此线程用监听Service2的状态
		 */
		new Thread() {
			public void run() {
				while (true) {
					boolean isRun = Utils.isServiceWork(Service1.this,
							"com.lzg.strongservice.service.Service2");
					if (!isRun) {
						Message msg = Message.obtain();
						msg.what = 1;
						handler.sendMessage(msg);
					}
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	/**
	 * 判断Service2是否还在运行，如果不是则启动Service2
	 */
	private void startService2() {
		boolean isRun = Utils.isServiceWork(Service1.this,
				"com.lzg.strongservice.service.Service2");
		if (isRun == false) {
			try {
				startS2.startService();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return (IBinder) startS2;
	}
}
