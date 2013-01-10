package com.polo50.android.taskmanager.model;

import android.location.Address;

public class Task {
	
	private String name;
	private boolean isComplete;
	private long id; //WARNING they say, int in SQL could be bigger than int in Java, 
					//so long is recomended.
	private String address;
	private double latitude;
	private double longitude;
	


	public Task(String name) {
		this.name = name;
	}
	
	
	public Task(long id, String name, boolean isComplete) {
		this.name = name;
		this.isComplete = isComplete;
		this.id = id;
	}


	


	public Task(long id, String name, boolean isComplete, String address,
			double latitude, double longitude) {
		this.name = name;
		this.isComplete = isComplete;
		this.id = id;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}
	
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setAddress(Address addressInput) {
		if (null == addressInput) {
			address = null;
			latitude = 0;
			longitude = 0;
		} else {
			int maxAddressLine = addressInput.getMaxAddressLineIndex();
			StringBuffer stringBuffer = new StringBuffer();
			for (int i=0; i<maxAddressLine; i++) {
				stringBuffer.append(addressInput.getAddressLine(i)).append(" ");
			}
			address = stringBuffer.toString();
			latitude = addressInput.getLatitude();
			longitude = addressInput.getLongitude();
		}
	}
	
	public boolean hasAddress() {
		return address != null;
	}
	
	
	public double getLatitude() {
		return latitude;
	}
	
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	
	public double getLongitude() {
		return longitude;
	}
	
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public boolean hasLocation() {
		return (longitude != 0 && latitude != 0);
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


	@Override
	public String toString() {
		return "Task [name=" + name + ", isComplete=" + isComplete + ", id="
				+ id + ", address=" + address + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (isComplete ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (id != other.id)
			return false;
		if (isComplete != other.isComplete)
			return false;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	
	

}
