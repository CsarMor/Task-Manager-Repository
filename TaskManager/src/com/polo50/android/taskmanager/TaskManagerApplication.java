package com.polo50.android.taskmanager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.polo50.android.taskmanager.model.Task;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import static com.polo50.android.taskmanager.TaskManagerSQLiteHelper.*;

public class TaskManagerApplication extends Application {
	
	private List<Task> currentTaskList = new ArrayList<Task>(); //TODO XXX looks this one redundant vs Adapter one
	private SQLiteDatabase database;;
	
	@Override
	public void onCreate() {
		super.onCreate();
		//loadSavedTasksFromPreferences();
		loadSavedTasksFromDB();
	}


	private void loadSavedTasksFromDB() {

		TaskManagerSQLiteHelper sqlHelper = new TaskManagerSQLiteHelper(this);
		database = sqlHelper.getWritableDatabase();
		Cursor taskCursor = database.query(
				TASK_TABLE, 
				new String[] {TASK_ID, TASK_NAME, TASK_COMPLETE, TASK_ADDRESS, TASK_LATITUDE, TASK_LONGITUDE}, 
				null, 
				null, 
				null, 
				null, 
				String.format("%s,%s", TASK_COMPLETE, TASK_NAME));
		taskCursor.moveToFirst();
		
		if (! taskCursor.isAfterLast()) {
			do {
				int id = taskCursor.getInt(0);
				String name = taskCursor.getString(1);
				String boolValue = taskCursor.getString(2);
				boolean isComplete = Boolean.parseBoolean(boolValue);
				String address = taskCursor.getString(3);
				float latitude = taskCursor.getFloat(4);
				float longitude = taskCursor.getFloat(5);
				
				
				
				currentTaskList.add(new Task(id, name, isComplete, address, latitude, longitude));
			} while (taskCursor.moveToNext());
		}
		
		taskCursor.close();
		
	}


	/*
	 * Persistence logic into the preferences.
	 */
	private void loadSavedTasksFromPreferences() {
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
			throw new IllegalArgumentException("Task could not be a null object.");
		}
		
		getCurrentTaskList().add(task);
		saveTaskToDb(task);
		//saveTaskToPreferences(task);
		
	}
	
	private void saveTaskToDb(Task task) {
		if (task == null) {
			return;
		}
		ContentValues values = new ContentValues();
		values.put(TASK_NAME, task.getName());
		values.put(TASK_COMPLETE, task.isComplete());	
		values.put(TASK_ADDRESS, task.getAddress());
		values.put(TASK_LATITUDE, task.getLatitude());
		values.put(TASK_LONGITUDE, task.getLongitude());
		long newId = database.insert(TASK_TABLE, null, values);
		task.setId(newId);		
	}


	private void saveTaskToPreferences(Task task) {
		SharedPreferences prefs = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(task.getName(), task.getName());
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
		List<Long> taskIdToRemoveList = new ArrayList<Long>();
		//List<Task> taskListToRemove = new ArrayList<Task>(); 
		List<Task> taskList = getCurrentTaskList();
		for (Iterator<Task> iterator = taskList.iterator(); iterator.hasNext();) {
			Task task =  iterator.next();
			if (task.isComplete()) {
				iterator.remove();
				taskIdToRemoveList.add(task.getId());
				//taskListToRemove.add(task);
			}
		}
		//removeTaskListFromPreferences(taskListToRemove);
		//deleteTaskListFromDB(taskIdToRemoveList);
	}


	public void deleteTaskListFromDB(List<Long> taskIdListToRemove) {
		if (taskIdListToRemove == null || taskIdListToRemove.size() < 1) {
			return;
		}
		StringBuffer idList = new StringBuffer();
		
		for (int i = 0; i < taskIdListToRemove.size(); i++) {
			idList.append(taskIdListToRemove.get(i));
			if (i != taskIdListToRemove.size() - 1) {
				idList.append(",");
			}
		}
		String whereClause = String.format("%s in (%s)", TASK_ID, idList.toString());
		database.delete(TASK_TABLE, whereClause, null);
	}


	public void updateTask(Task taskToUpdate) {
		if (taskToUpdate == null) {
			return;
		}
		
		ContentValues values = new ContentValues();
		values.put(TASK_NAME, taskToUpdate.getName());
		values.put(TASK_COMPLETE, Boolean.toString(taskToUpdate.isComplete()));
		values.put(TASK_ADDRESS, taskToUpdate.getAddress());
		values.put(TASK_LATITUDE, taskToUpdate.getLatitude());
		values.put(TASK_LONGITUDE, taskToUpdate.getLongitude());
		
		long id =taskToUpdate.getId();
		String whereClause = String.format("%s = ?", TASK_ID);
		database.update(TASK_TABLE, values, whereClause, new String[] {Long.toString(id)});
	}
	
}
