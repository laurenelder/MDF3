/* Name: Devin "Lauren" Elder
 * Date: 09/18/2014
 * Term: 1409
 * Project Name: Widget App
 * Assignment: MDF3 Week 3
 */

package com.laurenelder.widgetapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {

	public static final String ACTION_VIEW_DETAILS =
			"com.laurenelder.android.ACTION_VIEW_DETAILS";
	public static final String ACTION_VIEW_FORM =
			"com.laurenelder.android.ACTION_VIEW_FORM";
	public static final String UPDATE_ACTION =
			"com.laurenelder.android.UPDATE_ACTION";
	public static final String EXTRA_ITEM =
			"com.laurenelder.android.WidgetProvider.EXTRA_ITEM";

	SharedPreferences preferences;


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

			Intent formIntent = new Intent(ACTION_VIEW_FORM);
			PendingIntent pfIntent = PendingIntent.getBroadcast(context, 0, formIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			widgetView.setOnClickPendingIntent(R.id.widgetButton, pfIntent);

			Intent detailIntent = new Intent(ACTION_VIEW_DETAILS);
			PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			widgetView.setPendingIntentTemplate(R.id.widget_list, pIntent);

			appWidgetManager.updateAppWidget(widgetId, widgetView);
		}

		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	@Override
	public void onReceive(Context context, Intent intent) {

		// Opens the form view on widget button click
		if(intent.getAction().equals(ACTION_VIEW_FORM)) {
			Intent form = new Intent(context, FormActivity.class);
			form.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			form.putExtra("source", "widget");
			context.startActivity(form);
		}

		// Opens the details view on widget list item click
		if(intent.getAction().equals(ACTION_VIEW_DETAILS)) {
			String title = (String)intent.getSerializableExtra(EXTRA_ITEM);
			if(title != null) {
				// Handle the click here.
				Intent details = new Intent(context, DetailActivity.class);
				details.putExtra("title", title);
				details.putExtra("source", "widget");
				details.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(details);
				Log.i("WidgetProvider", "onReceive hit");
			}
		}

		if(intent.getAction().equals(UPDATE_ACTION)) {
			final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
			final ComponentName cn = new ComponentName(context, WidgetProvider.class);
			mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widget_list);

		}

		super.onReceive(context, intent);
	}
}

