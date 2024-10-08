package com.example.zerosoles.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.zerosoles.R;
import com.example.zerosoles.utils.RationaleDialog;
import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StoresNearYouActivity extends FragmentActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        OnRequestPermissionsResultCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean permissionDenied = false;

    private GoogleMap m_Map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stores_near_you);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        m_Map = googleMap;

        enableMyLocation();

        UiSettings uiSettings = m_Map.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setIndoorLevelPickerEnabled(true);
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            m_Map.setMyLocationEnabled(true);
            m_Map.setOnMyLocationButtonClickListener(this);
            m_Map.setOnMyLocationClickListener(this);

            // Set markers
            LatLng studentCultureHouse = new LatLng(10.875331376135065, 106.80070183840535);
            m_Map.addMarker(new MarkerOptions().position(studentCultureHouse));

            LatLng fptUni = new LatLng(10.841380458141039, 106.80984005969286);
            m_Map.addMarker(new MarkerOptions().position(fptUni));

            // Pan and zoom camera to current location
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            CurrentLocationRequest curLocRequest = new CurrentLocationRequest.Builder()
                    .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                    .build();

            fusedLocationClient.getCurrentLocation(curLocRequest, null)
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            m_Map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15.0f));
                        }
                    });
        } else
            requestLocationPermissions();
    }

    private void requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Display a dialog with rationale and permissions request.
            RationaleDialog.newInstance(LOCATION_PERMISSION_REQUEST_CODE, true)
                    .show(getSupportFragmentManager(), "Dialog");
        } else {
            // Simple request location permissions.
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)
                || isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Permission granted. Enable the my location layer.
            enableMyLocation();
        } else {
            // Permission denied. Display an error message when the fragments resume.
            permissionDenied = true;
        }
    }

    private boolean isPermissionGranted(@NonNull String[] permissions, @NonNull int[] grantResults, String permission) {
        for (int i = 0; i < grantResults.length; i++) {
            if (permissions[i].equals(permission)) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.permission_required)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> finish())
                    .show();
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "So that's where you are.", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "That's you! Now look for *us*.", Toast.LENGTH_LONG).show();
    }
}
