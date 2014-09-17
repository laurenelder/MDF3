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
//		mArticles = new ArrayList<NewsArticle>();
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		if(!preferences.toString().isEmpty()) {
			Map<String,?> entries = preferences.getAll();
			for(Map.Entry<String,?> entry : entries.entrySet()){
				
				String fileName = entry.getValue().toString();
				widgetListData.add(fileName);
			}
		}
	}

	@Override
	public void onDataSetChanged() {
		// TODO Auto-generated method stub
		
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
		RemoteViews widgetItemView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		widgetItemView.setTextViewText(R.id.widget_list, widgetListData.get(position).toString());
		Intent intent = new Intent();
		intent.putExtra(WidgetProvider.EXTRA_ITEM, widgetListData.get(position).toString());
		widgetItemView.setOnClickFillInIntent(R.id.widget_list, intent);
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
