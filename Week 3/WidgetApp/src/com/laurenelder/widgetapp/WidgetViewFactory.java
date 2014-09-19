/* Name: Devin "Lauren" Elder
 * Date: 09/18/2014
 * Term: 1409
 * Project Name: Widget App
 * Assignment: MDF3 Week 3
 */

package com.laurenelder.widgetapp;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

public class WidgetViewFactory implements RemoteViewsFactory{

	private static final int ID_CONSTANT = 0x0101010;
	private Context context;
	SharedPreferences preferences;
	public ArrayList<String> widgetListData = null;

	public WidgetViewFactory(Context thisContext) {
		context = thisContext;
		Log.i("WidgetViewFactory", "Constructor hit");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		Log.i("WidgetViewFactory", "onCreate hit");
		widgetListData = new ArrayList<String>();
		if(!preferences.toString().isEmpty()) {
			Map<String,?> entries = preferences.getAll();
			for(Map.Entry<String,?> entry : entries.entrySet()){
				Log.i("WidgetViewFactory", entry.getValue().toString());
				String fileName = entry.getValue().toString();
				widgetListData.add(fileName);
			}
		}
	}

	@Override
	public void onDataSetChanged() {
		// TODO Auto-generated method stub
		Log.i("WidgetViewFactory", "onDataSetChanged hit");
		widgetListData.removeAll(widgetListData);
		if(!preferences.toString().isEmpty()) {
			Map<String,?> entries = preferences.getAll();
			for(Map.Entry<String,?> entry : entries.entrySet()){
				Log.i("WidgetViewFactory", entry.getValue().toString());
				String fileName = entry.getValue().toString();
				widgetListData.add(fileName);
			}
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		widgetListData.removeAll(widgetListData);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return widgetListData.size();
	}

	@Override
	public RemoteViews getViewAt(int position) {
		// TODO Auto-generated method stub
		RemoteViews widgetItemView = new RemoteViews(context.getPackageName(), R.layout.widget_item);
		Log.i("WidgetViewFactory", widgetListData.get(position));
		widgetItemView.setTextViewText(R.id.widget_title, widgetListData.get(position).toString());
		Intent intent = new Intent();
		intent.putExtra(WidgetProvider.EXTRA_ITEM, widgetListData.get(position).toString());
		widgetItemView.setOnClickFillInIntent(R.id.widget_item, intent);
		return widgetItemView;
	}

	@Override
	public RemoteViews getLoadingView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return ID_CONSTANT + position;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

}
