package com.polo50.android.taskmanager.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Address;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class AddressOverlay extends Overlay {
	private static final float CONTAINER_RADIUS = 4;
	private static final int CONTAINER_SHADOW_OFFSET = 1;
	private Address address;
	private GeoPoint geoPoint;

	public AddressOverlay(Address address) {
		super();
		setAddress(address);
		Double convertedLongitude = address.getLongitude() * 1E6;
		Double convertedLatitude = address.getLatitude() * 1E6;
		setGeoPoint(
				new GeoPoint(convertedLatitude.intValue(), convertedLongitude.intValue()));
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		Point locationPoint = new Point();
		Projection projection = mapView.getProjection();
		projection.toPixels(geoPoint, locationPoint);
		Paint containerPaint = new Paint();
		containerPaint.setAntiAlias(true);
		int containerX = locationPoint.x;
		int containerY = locationPoint.y;
		if (shadow) {
			containerX += CONTAINER_SHADOW_OFFSET;
			containerY += CONTAINER_SHADOW_OFFSET;
			containerPaint.setARGB(90, 0, 0, 0);
			canvas.drawCircle(containerX, containerY, CONTAINER_RADIUS, containerPaint);
		} else {
			containerPaint.setColor(Color.RED);
			canvas.drawCircle(containerX, containerY, CONTAINER_RADIUS, containerPaint);
		}
		
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
		
	public void setGeoPoint(GeoPoint geoPoint) {
		this.geoPoint = geoPoint;
	}

	public GeoPoint getGeoPoint() {
		return geoPoint;
	}
}
