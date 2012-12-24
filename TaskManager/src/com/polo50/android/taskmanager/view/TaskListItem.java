package com.polo50.android.taskmanager.view;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.polo50.android.taskmanager.model.Task;

public class TaskListItem extends LinearLayout {

	
	private Task task;
	private CheckedTextView textCheckbox;
	
	public TaskListItem(Context context, AttributeSet attribtes) {
		super(context, attribtes);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		textCheckbox = (CheckedTextView) findViewById(R.id.text1);
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
		textCheckbox.setText(task.getName());
		textCheckbox.setChecked(task.isComplete());
	}
}
