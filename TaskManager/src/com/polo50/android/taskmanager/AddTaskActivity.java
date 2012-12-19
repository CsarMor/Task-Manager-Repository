package com.polo50.android.taskmanager;

import com.polo50.android.taskmanager.model.Task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddTaskActivity extends TaskManagerActivity {

	private Button addNewTaskButton;
	private Button cancelNewTaskButton;
	private EditText taskNameEdit;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_task_layout);
		setUpViews();
	}

	
	
	protected void cancelTask() {
		finish();
	}


	protected void addTask() {
		String newTaskName = taskNameEdit.getText().toString();
		if (newTaskName == null) {
			throw new IllegalArgumentException("Task name must not be empty!");
		}
		
		Task newTask = new Task(newTaskName);
		getTaskManagerApplication().addTask(newTask);
		finish();
	}


	private void setUpViews() {
		addNewTaskButton = (Button) findViewById(R.id.add_new_task_button);
		cancelNewTaskButton = (Button) findViewById(R.id.cancel_add_task_button);
		taskNameEdit = (EditText) findViewById(R.id.edit_task_name);

		addNewTaskButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addTask();
			}
		});
		
		cancelNewTaskButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelTask();
			}
		});
	}

	
	
}
