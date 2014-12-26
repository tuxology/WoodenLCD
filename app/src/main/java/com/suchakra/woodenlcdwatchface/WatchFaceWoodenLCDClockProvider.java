package com.suchakra.woodenlcdwatchface;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.RemoteViews;


public class WatchFaceWoodenLCDClockProvider extends AppWidgetProvider {

	static int numbers[] =  {
		R.drawable._0, R.drawable._1, R.drawable._2, R.drawable._3,
		R.drawable._4, R.drawable._5, R.drawable._6, R.drawable._7,
		R.drawable._8, R.drawable._9,
	};

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		updateWidgets(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		String action = intent.getAction();
		if (Intent.ACTION_TIME_TICK.equals(action)
				|| Intent.ACTION_TIME_CHANGED.equals(action)) {
			updateWidgets(context);
		}
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);

		Intent serviceIntent = new Intent(context, WoodenLCDClockService.class);
		context.startService(serviceIntent);
		
		registerReceivers(context);
	}
	
	/*
	 *
	 */
	private void registerReceivers(Context context) {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_TIME_CHANGED);
		filter.addAction(Intent.ACTION_TIME_TICK);

		context.getApplicationContext().registerReceiver(this, filter);
	}
	
	/*
	 *
	 */
	@SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
	private void updateWidgets(Context context) {
		String timeFormat = "HH:mm";
		// String dateFormat = "E d/M";
		Date date = new Date();

		// String textDate = new SimpleDateFormat(dateFormat).format(date);
		String textTime = new SimpleDateFormat(timeFormat).format(date);

		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.woodenlcd_widget);

		//views.setTextViewText(R.id.date, textDate.toUpperCase());
		views.setImageViewResource(R.id.hours_first_value,
				numbers[Character.getNumericValue(textTime.charAt(0))]);
		
		views.setImageViewResource(R.id.hours_second_value,
				numbers[Character.getNumericValue(textTime.charAt(1))]);
		
		views.setImageViewResource(R.id.minutes_first_value,
				numbers[Character.getNumericValue(textTime.charAt(3))]);
		
		views.setImageViewResource(R.id.minutes_second_value,
				numbers[Character.getNumericValue(textTime.charAt(4))]);

		manager.updateAppWidget(new ComponentName(context,
				WatchFaceWoodenLCDClockProvider.class), views);

		// System.out.println(String.format("time: %s", textTime));
	}
}
