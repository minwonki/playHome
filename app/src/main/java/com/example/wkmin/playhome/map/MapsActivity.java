package com.example.wkmin.playhome.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.wkmin.playhome.R;
import com.example.wkmin.playhome.cmn.PermissionUtils;
import com.example.wkmin.playhome.cmn.PlayHomeClusterItem;
import com.example.wkmin.playhome.data.PlayHome;
import com.example.wkmin.playhome.data.source.PlayHomeRepository;
import com.example.wkmin.playhome.history.HistoryActivity;
import com.example.wkmin.playhome.list.ListActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import io.realm.RealmResults;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, MapsContract.View, GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnInfoWindowClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMarkerClickListener, ClusterManager.OnClusterItemClickListener<PlayHomeClusterItem>, GoogleMap.OnCameraMoveListener, View.OnClickListener {

    public static final String EXTRA_SEARCH_TEXT = "EXTRA_SEARCH_TEXT";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap mMap;
    private MapsContract.Presenter presenter;

    private boolean mPermissionDenied = false;
    private boolean userGestured = false;
    private float defaultZoom = 14.0f;
    private LatLng defaultLatLng = new LatLng(37.5350844, 127.0080929);

    private ClusterManager<PlayHomeClusterItem> clusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setButton();
        setGPS();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
        setMVP();
    }

    private void setButton() {
        findViewById(R.id.btn_history).setOnClickListener(this);
    }

    private void setGPS() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Check GPS
        assert locationManager != null;
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Setup GPS
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
            finish();
        }
    }

    private void setMVP() {
        new MapsPresenter(this, PlayHomeRepository.getInstance());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlayHomeRepository.destroyInstance();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerClickListener(this);
        enableMyLocation();
        loadMarker();
        setUpCluster();
    }

    private void setUpCluster() {
        clusterManager = new ClusterManager<>(this, mMap);
        clusterManager.setOnClusterItemClickListener(this);
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    public void addCluster(RealmResults<PlayHome> homes) {
        markerAndClusterClearAll();
        for (PlayHome home : homes) {
            PlayHomeClusterItem houseClusterItem = new PlayHomeClusterItem(home);
            clusterManager.addItem(houseClusterItem);
        }
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        clusterManager.cluster();
    }

    private void markerAndClusterClearAll() {
        clusterManager.clearItems();
        mMap.clear();
    }

    @Override
    public void addMarker(RealmResults<PlayHome> list) {
        markerAndClusterClearAll();
        System.out.println("PlayHome size:" + list.size());
        for (PlayHome result : list) {
            if (result.getLatitude() != 0 && result.getLongitude() != 0) {
                LatLng latlng = new LatLng(result.getLatitude(), result.getLongitude());
                Marker marker = mMap.addMarker(new MarkerOptions().position(latlng).title(result.getName()));
                marker.showInfoWindow();
                marker.setTag(result);
            }
        }
        changeMapListener();
        // move default or current position/zoom
        setCenter();
    }

    private void changeMapListener() {
        mMap.setOnCameraIdleListener(this);
        mMap.setOnMarkerClickListener(this);
    }

    private void setCenter() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, defaultZoom));
    }

    private void loadMarker() {
        presenter.loadMarker();
    }

    @Override
    public void setPresenter(MapsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void onCameraIdle() {
        System.out.println("onCameraIdle");
        if (userGestured) {
            userGestured = false;
            defaultZoom = mMap.getCameraPosition().zoom;
            defaultLatLng = mMap.getCameraPosition().target;
            LatLngBounds latLngBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
            presenter.changeMarker(latLngBounds);
        }
    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            userGestured = true;
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        PlayHome playHome = (PlayHome) marker.getTag();
        assert playHome != null;
        addHistory(playHome);

        String[] data = playHome.getAddress().split(" ");
        String searchText = data[2] + " " + playHome.getName();

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(EXTRA_SEARCH_TEXT, searchText);
        startActivity(intent);
    }

    private void addHistory(PlayHome playHome) {
        this.presenter.addHistory(playHome);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // (return false so the camera animates to the user's current position).
        userGestured = true;
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public boolean onClusterItemClick(PlayHomeClusterItem playHomeClusterItem) {
        return false;
    }

    @Override
    public void onCameraMove() {
        CameraPosition cameraPosition = mMap.getCameraPosition();
        if (cameraPosition.zoom >= 14) {
            changeMapListener();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_history:
                System.out.println("btn_history");
                startHistoryActivity();
                break;
            default:
                break;
        }
    }

    private void startHistoryActivity() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}
