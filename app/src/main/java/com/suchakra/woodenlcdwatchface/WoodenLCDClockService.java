package com.suchakra.woodenlcdwatchface;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WoodenLCDClockService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	public void onCreate() {
		super.onCreate();
		System.out.println("service created");
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("service started");
		return super.onStartCommand(intent, flags, startId);
	}

}