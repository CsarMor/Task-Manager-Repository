package com.polo50.android.taskmanager;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.polo50.android.taskmanager.adapter.TaskListAdapter;
import com.polo50.android.taskmanager.model.Task;

public class ViewTasksActivity extends ListActivity {

	private Button addButton;
	private Button removeCompletedButton;
	private TaskManagerApplication application;
	private TaskListAdapter adapter;
	


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
	}

	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
		//showTaskList();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		adapter.toggleTaskCompleteAtPosition(position);
		
	}
	

	private void showTaskList() {
		List<Task> taskLists = getTaskManagerApplication().getCurrentTaskList();
		
	}
	
	protected TaskManagerApplication getTaskManagerApplication() {
		return (TaskManagerApplication) getApplication();
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
		removeCompletedButton = (Button) findViewById(R.id.remove_completed_button);
		removeCompletedButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				application.removeCompletedTasks();
				adapter.removeCompletedTasks();
				adapter.notifyDataSetChanged();
			}
		});
		
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
