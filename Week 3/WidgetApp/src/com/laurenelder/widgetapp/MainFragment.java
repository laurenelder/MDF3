/* Name: Devin "Lauren" Elder
 * Date: 09/18/2014
 * Term: 1409
 * Project Name: Widget App
 * Assignment: MDF3 Week 3
 */

package com.laurenelder.widgetapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainFragment extends Fragment {

	// Establish Variables
	Context context;
	ListView list;
	ArrayAdapter<ListItems> listAdapter;
	List<ListItems> groceryItems = new ArrayList<ListItems>();
	private mainInterface mainActivity;

	// Set up interface to parent activity
	public interface mainInterface {
		public void refreshList();
		public void startAct(Intent actIntent);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		context = this.getActivity();


		if (activity instanceof mainInterface) {
			mainActivity = (mainInterface) activity;
		} else {
			throw new ClassCastException((activity.toString()) + 
					"Did not impliment mainInterface");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View mainView = inflater.inflate(R.layout.activity_main, container);

		// Setup ListView
		list = (ListView)mainView.findViewById(R.id.list);
		listAdapter = new ArrayAdapter<ListItems> 
		(context, android.R.layout.simple_list_item_1, groceryItems);
		list.setAdapter(listAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			// On ListView click data is sent through an intent to the detail view
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent details = new Intent(context, DetailActivity.class);
				details.putExtra("title", groceryItems.get(position).title.toString());
				details.putExtra("source", "app");
				mainActivity.startAct(details);
			}

		});

		return mainView;
	}

	// resetObjects clears the array list used to populate ListView
	public void resetObjects() {
		groceryItems.removeAll(groceryItems);
	}

	// upDateList adds all current objects to the array list and notifies the ListView adapter of the change
	public void upDateList(String name, String content) {
		ListItems newItem = new ListItems(name, content);
		groceryItems.add(newItem);
		listAdapter.notifyDataSetChanged();
	}
}
