package com.polo50.android.taskmanager;

import java.util.ArrayList;
import java.util.List;

import com.polo50.android.taskmanager.model.Task;

import android.app.Application;

public class TaskManagerApplication extends Application {
	
	private List<Task> currentTaskList = new ArrayList<Task>();;

	@Override
	public void onCreate() {
		super.onCreate();
/*		if (currentTaskList == null) {
			currentTaskList = new ArrayList<Task>();
		}*/
	}


	public void addTask(Task task) {
		if (task == null) {
			throw new IllegalArgumentException();
		}
		getCurrentTaskList().add(task);
		
	}
	
	public List<Task> getCurrentTaskList() {
		if (currentTaskList == null) {
			currentTaskList = new ArrayList<Task>();
		}
		return currentTaskList;
	}

	public void setCurrentTaskList(List<Task> currentTaskList) {
		this.currentTaskList = currentTaskList;
	}
	
}
