package com.polo50.android.taskmanager;

import com.polo50.android.taskmanager.model.Task;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddTaskActivity extends TaskManagerActivity {

	private static final int REQUEST_CHOOSE_ADDRESS = 0;
	
	private Button addNewTaskButton;
	private Button cancelNewTaskButton;
	private EditText taskNameEdit;
	private boolean changesPending;
	private AlertDialog unsavedChangesDialog;
	private Address address;

	private Button addLocationButton;

	private TextView addressText;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_task_layout);
		setUpViews();
	}

	public void addLocationButtonClicked(View view) {
		Intent intentToLocation = new Intent(this, AddLocationMapActivity.class);
		startActivityForResult(intentToLocation, REQUEST_CHOOSE_ADDRESS);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (address == null) {
			addLocationButton.setVisibility(View.VISIBLE);
			addressText.setVisibility(View.GONE);
		} else {
			addLocationButton.setVisibility(View.GONE);
			addressText.setVisibility(View.VISIBLE);
			addressText.setText(address.getAddressLine(0));
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (REQUEST_CHOOSE_ADDRESS == requestCode && RESULT_OK == resultCode) {
			address = data.getParcelableExtra(AddLocationMapActivity.ADDRESS_RESULT);
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	
	protected void cancelTask() {
		if (changesPending){
		unsavedChangesDialog = new AlertDialog.Builder(this)
		.setTitle(R.string.unsaved_changes_alert_title)
		.setMessage(R.string.unsaved_changes_alert_message)
		.setPositiveButton(R.string.popup_button_save, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				addTask();
			}
		})
		.setNeutralButton(R.string.popup_button_discard, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		})
		.setNegativeButton(R.string.popup_button_cancel, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				unsavedChangesDialog.cancel();
			}
		})
		.create();
		unsavedChangesDialog.show();
		} else {
			finish();
		}
	}


	protected void addTask() {
		String newTaskName = taskNameEdit.getText().toString();
		if (newTaskName == null || newTaskName.length() < 1) {
			//TODO XXX show pop-up information - that name should not be null;
			throw new IllegalArgumentException("Task name must not be empty!");
		}
		
		Task newTask = new Task(newTaskName);
		newTask.setAddress(address);
		getTaskManagerApplication().addTask(newTask);
		finish();
	}


	private void setUpViews() {
		addNewTaskButton = (Button) findViewById(R.id.add_new_task_button);
		cancelNewTaskButton = (Button) findViewById(R.id.cancel_add_task_button);
		taskNameEdit = (EditText) findViewById(R.id.edit_task_name);
		addLocationButton = (Button) findViewById(R.id.add_location_button);
		addressText = (TextView) findViewById(R.id.address_text);
		
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
		taskNameEdit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				changesPending = true;
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
		
	}

	
	
}
