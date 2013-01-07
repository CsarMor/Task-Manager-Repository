package com.polo50.android.taskmanager;

import java.io.IOException;
import java.util.List;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.polo50.android.taskmanager.view.AddressOverlay;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddLocationMapActivity extends MapActivity {

	public static final String ADDRESS_RESULT = "address";
	private EditText addressText;
	private Button mapLocationButton;
	private Button useLocationButton;
	private MapView mapView;
	private Address address;
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.add_location_layout);  
		setUpViews();
	}

	private void setUpViews() {
		addressText = (EditText) findViewById(R.id.task_address);
		useLocationButton = (Button) findViewById(R.id.use_this_location_button);
		useLocationButton.setEnabled(false);
		mapView = (MapView) findViewById(R.id.map);
		mapView.setBuiltInZoomControls(true);
		mapLocationButton = (Button) findViewById(R.id.map_location_button); 
		mapLocationButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mapCurrentAddress();
			}

		});
		
		useLocationButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (address == null) {
					return;
				}
				Intent intent = new Intent();
				intent.putExtra(ADDRESS_RESULT, address);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		
	}

	private void mapCurrentAddress() {
		String addressString = addressText.getText().toString();
		Geocoder g = new Geocoder(this);
		List<Address> addresses = null;
		try {
			addresses = g.getFromLocationName(addressString, 1);
			
			if (addresses == null || addresses.size() < 1){
				//TODO XXX show alert: No Items found by this Address
				//TODO XXX add edit functionality already chosen Location
			} else {
				//TODO XXX remove old points
				address = addresses.get(0);
				List<Overlay> mapOverlayList = mapView.getOverlays();
				AddressOverlay addressOverlay = new AddressOverlay(address);
				mapOverlayList.add(addressOverlay);
				mapView.invalidate();
				final MapController mapController = mapView.getController();
				mapController.animateTo(addressOverlay.getGeoPoint(), new Runnable() {
					
					@Override
					public void run() {
						mapController.setZoom(12);
					}
				});
				useLocationButton.setEnabled(true);				
			}
		} catch (IOException e) {
			// TODO XXX show alert: "Problems with network".
			// TODO XXX - menu - add Belarus Mova for each label, as prove approach
			e.printStackTrace();
		}
		
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected boolean isLocationDisplayed() {
		//return super.isLocationDisplayed();
		return true;
	}
	
}
