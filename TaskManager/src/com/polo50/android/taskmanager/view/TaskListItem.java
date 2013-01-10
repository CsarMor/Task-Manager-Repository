package com.polo50.android.taskmanager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polo50.android.taskmanager.R;
import com.polo50.android.taskmanager.model.Task;

public class TaskListItem extends LinearLayout {

	
	private Task task;
	private CheckedTextView textCheckbox;
	private TextView addressText;
	
	public TaskListItem(Context context, AttributeSet attribtes) {
		super(context, attribtes);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		textCheckbox = (CheckedTextView) findViewById(android.R.id.text1);
		addressText = (TextView) findViewById(R.id.address_text);
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
		textCheckbox.setText(task.getName());
		textCheckbox.setChecked(task.isComplete());
		if (task.hasAddress()) {
			addressText.setText(task.getAddress());
			addressText.setVisibility(View.VISIBLE);
		} else {
			addressText.setVisibility(View.GONE);
		}
	}
}
