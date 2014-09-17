package com.laurenelder.widgetapp;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		// TODO Auto-generated method stub
		return new WidgetViewFactory(getApplicationContext());
	}

}
