package com.polo50.android.taskmanager.model;

public class Task {
	
	private String name;
	private boolean isComplete;
	private long id; //WARNING they say, int in SQL could be bigger than int in Java, 
					//so long is recomended.

	
	public Task(String name) {
		this.name = name;
	}
	
	
	public Task(long id, String name, boolean isComplete) {
		this.name = name;
		this.isComplete = isComplete;
		this.id = id;
	}




	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Override
	public String toString() {
		return "Task [name=" + name + ", isComplete=" + isComplete + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isComplete ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (isComplete != other.isComplete)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public boolean isComplete() {
		return isComplete;
	}
	
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}


	public void toggleComplete() {
		isComplete = !isComplete;		
	}


	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	

}
