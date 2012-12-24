package com.polo50.android.taskmanager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.polo50.android.taskmanager.model.Task;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class TaskManagerApplication extends Application {
	
	private List<Task> currentTaskList = new ArrayList<Task>();;

	@Override
	public void onCreate() {
		super.onCreate();
		loadSavedPreferences();
	}


	private void loadSavedPreferences() {
		SharedPreferences prefs = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
		Map<String, ?> savedTasks = prefs.getAll();
		if (savedTasks == null || savedTasks.size() < 1) {
			return;
		}
		currentTaskList = getCurrentTaskList();
		
		for (Object value : savedTasks.values()) {
			System.out.println((String) value);
			currentTaskList.add(new Task((String) value));
		}
	}


	public void addTask(Task task) {
		if (task == null) {
			throw new IllegalArgumentException();
		}
		
		getCurrentTaskList().add(task);
		saveTaskToPreferences(task);
		
	}
	
	private void saveTaskToPreferences(Task task) {
		SharedPreferences prefs = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(task.getName(), task.getName());
		editor.commit();
	}
	
	private void removeTaskFromPreferences(Task task) {
		SharedPreferences prefs = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.remove(task.getName());
		editor.commit();
	}
	
	private void removeTaskListFromPreferences(List<Task> taskList) {
		if (taskList == null || taskList.size() < 1) {
			return;
		}
		SharedPreferences prefs = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		for (Task task : taskList) {
			if (prefs.contains(task.getName())) {
				editor.remove(task.getName());
			}
		}
		editor.commit();
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


	public void removeCompletedTasks() {
		List<Task> taskListToRemove = new ArrayList<Task>(); 
		List<Task> taskList = getCurrentTaskList();
		for (Iterator<Task> iterator = taskList.iterator(); iterator.hasNext();) {
			Task task =  iterator.next();
			if (task.isComplete()) {
				iterator.remove();
				taskListToRemove.add(task);
			}
		}
		removeTaskListFromPreferences(taskListToRemove);
	}
	
}
