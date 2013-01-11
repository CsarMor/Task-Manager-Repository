package com.polo50.android.taskmanager;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.polo50.android.taskmanager.adapter.TaskListAdapter;
import com.polo50.android.taskmanager.model.Task;

public class ViewTasksActivity extends ListActivity implements LocationListener {

	private static final long LOCATION_FILTER_DISTANCE = 10000; //in meters
	private Button addButton;
	private Button removeCompletedButton;
	private TaskManagerApplication application;
	private TaskListAdapter adapter;
	private TextView locationText;
	private LocationManager locationManager;
	private Location latestLocation;
	private ToggleButton localTaskToggleButton;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setUpViews();
		
		application = (TaskManagerApplication) getApplication();
		adapter = new TaskListAdapter(application.getCurrentTaskList(), this);
		setListAdapter(adapter);
		//for quick simple text output in List.
		//setListAdapter(new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, application.getCurrentTaskList()));
		setUpLocation();
	}


	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
				1000, 5, this);
		adapter.notifyDataSetChanged();
		//showTaskList();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		adapter.toggleTaskCompleteAtPosition(position);
		Task taskToUpdate = (Task) adapter.getItem(position);
		application.updateTask(taskToUpdate);
	}
	
	

	private void showTaskList() {
		List<Task> taskLists = getTaskManagerApplication().getCurrentTaskList();
		
	}
	
	protected TaskManagerApplication getTaskManagerApplication() {
		return (TaskManagerApplication) getApplication();
	}
	
	private void setUpLocation() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
				100, 5, this);
	}

	private void setUpViews() {
		addButton = (Button) findViewById(R.id.add_task_button);
 		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ViewTasksActivity.this, AddTaskActivity.class);
				startActivity(intent);
			}
		});
 		
 		locationText = (TextView) findViewById(R.id.location_text);
 		
		removeCompletedButton = (Button) findViewById(R.id.remove_completed_button);
		removeCompletedButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//application.removeCompletedTasks();
				List<Long> taskIdListForDelete = adapter.removeCompletedTasks();
				application.deleteTaskListFromDB(taskIdListForDelete );
			}
		});
		
		
		localTaskToggleButton = (ToggleButton) findViewById(R.id.show_local_tasks_toggle);
		localTaskToggleButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showLocalTasks(localTaskToggleButton.isChecked());
			}

		});
	}
	
	private void showLocalTasks(boolean isLocalOnly) {
		if (latestLocation == null) {
			localTaskToggleButton.toggle();
			//TODO XXX popup no GPS is running
			return;
		}
		if (isLocalOnly) {
			adapter.filterTasksByLocation(latestLocation, LOCATION_FILTER_DISTANCE);
		} else {
			adapter.removeLocationFilter();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onLocationChanged(Location location) {
		latestLocation = location;
		String locationString = String.format("@ %f, %f +/- %fm", 
				location.getLatitude(),
				location.getLongitude(),
				location.getAccuracy());
		locationText.setText(locationString);
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
