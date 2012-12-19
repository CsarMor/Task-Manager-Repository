package com.polo50.android.taskmanager;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.polo50.android.taskmanager.model.Task;

public class ViewTasksActivity extends TaskManagerActivity {

	private Button addButton;
	private TextView taskTextList;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setUpViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		showTaskList();
	}
	

	private void showTaskList() {
		StringBuffer resultText = new StringBuffer();
		List<Task> taskLists = getTaskManagerApplication().getCurrentTaskList();
		for (Task task : taskLists) {
			resultText.append(String.format("* %s\n", task.getName()));
		}
		taskTextList.setText(resultText.toString());
		
	}
	
	private void setUpViews() {
		addButton = (Button) findViewById(R.id.add_task_button);
		taskTextList = (TextView) findViewById(R.id.task_list_text);
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ViewTasksActivity.this, AddTaskActivity.class);
				startActivity(intent);
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
