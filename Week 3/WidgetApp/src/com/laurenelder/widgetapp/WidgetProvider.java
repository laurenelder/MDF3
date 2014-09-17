package com.laurenelder.widgetapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {

	public static final String ACTION_VIEW_DETAILS =
			"com.laurenelder.android.ACTION_VIEW_DETAILS";
	public static final String EXTRA_ITEM =
			"com.laurenelder.android.CollectionWidgetProvider.EXTRA_ITEM";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		for(int i = 0; i < appWidgetIds.length; i++) {

			int widgetId = appWidgetIds[i];

			Intent intent = new Intent(context, WidgetService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
			RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			widgetView.setRemoteAdapter(R.id.widget_list, intent);
			widgetView.setEmptyView(R.id.widget_list, R.id.empty);

			Intent detailIntent = new Intent(ACTION_VIEW_DETAILS);
			PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			widgetView.setPendingIntentTemplate(R.id.widget_list, pIntent);

			appWidgetManager.updateAppWidget(widgetId, widgetView);
		}

		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	@Override
	public void onReceive(Context context, Intent intent) {

		if(intent.getAction().equals(ACTION_VIEW_DETAILS)) {
			String title = (String)intent.getSerializableExtra(EXTRA_ITEM);
			if(title != null) {
				// Handle the click here.
	        	Intent details = new Intent(context, DetailActivity.class);
	        	details.putExtra("title", title);
/*	        	details.putExtra("content", groceryItems.get(position).information.toString());
	        	details.putExtra("source", "app");*/
	        	PendingIntent.getActivity(context, 0, details, 0);
			}
		}

		super.onReceive(context, intent);
	}
}

