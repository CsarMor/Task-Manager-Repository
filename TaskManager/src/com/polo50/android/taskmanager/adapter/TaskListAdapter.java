package com.polo50.android.taskmanager.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.polo50.android.taskmanager.R;
import com.polo50.android.taskmanager.model.Task;
import com.polo50.android.taskmanager.view.TaskListItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TaskListAdapter extends BaseAdapter {

	private List<Task> taskList;
	private Context context;
	
	public TaskListAdapter(List<Task> taskList, Context context) {
		super();
		this.taskList = taskList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return taskList == null ? 0 : taskList.size();
	}

	@Override
	public Object getItem(int index) {
		return taskList == null ? null : taskList.get(index);
	}

	@Override
	public long getItemId(int indexId) {
		return indexId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TaskListItem taskListItem;
		if (null == convertView) {
			taskListItem = (TaskListItem) View.inflate(context, R.layout.task_list_item, null);
			System.out.println("Create new View for an item: " + taskList.get(position).getName());
		} else {
			taskListItem = (TaskListItem) convertView;
			System.out.println("Used cached item: " + taskList.get(position).getName());
		}
		taskListItem.setTask(taskList.get(position));
		return taskListItem;
	}

	public void toggleTaskCompleteAtPosition(int position) {
		Task selectedTask = (Task) getItem(position);
		if (selectedTask == null) {
			return;
		}
		selectedTask.toggleComplete();
		notifyDataSetChanged();
	}

	public List<Long> removeCompletedTasks() {
		
		if (taskList == null || taskList.isEmpty()) {
			return new ArrayList<Long>();
		}
		
		List<Long> resultIdList = new ArrayList<Long>();
		for (Iterator<Task> iterator = taskList.iterator(); iterator.hasNext();) {
			Task task = iterator.next();
			if (task.isComplete()) {
				resultIdList.add(task.getId());
				iterator.remove();
			}
		}
		
		this.notifyDataSetChanged();
		
		return resultIdList;
	}

}
