package com.polo50.android.taskmanager;

import android.app.Activity;

public class TaskManagerActivity extends Activity {

	protected TaskManagerApplication getTaskManagerApplication() {
		return (TaskManagerApplication) getApplication();
	}

}
