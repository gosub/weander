package it.lo.exp.weander.ui.adventure;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.Arrays;

import it.lo.exp.weander.R;
import it.lo.exp.weander.missions.Mission;
import it.lo.exp.weander.missions.MissionCategory;
import it.lo.exp.weander.missions.MissionPool;
import it.lo.exp.weander.ui.complete.CompleteActivity;
import it.lo.exp.weander.util.LocationUtil;

public class AdventureActivity extends Activity {

    private double startLat, startLng, destLat, destLng;
    private String missionCategory, missionText;
    private String navName, navInstruction;
    private String constraint;

    private MapView mapView;
    private TextView missionLabel;
    private TextView missionView;
    private TextView distanceView;
    private TextView navNameView;
    private TextView navInstructionView;

    private Marker youAreHereMarker;
    private FusedLocationProviderClient fusedLocation;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure);

        startLat        = getIntent().getDoubleExtra("startLat", 0);
        startLng        = getIntent().getDoubleExtra("startLng", 0);
        destLat         = getIntent().getDoubleExtra("destLat", 0);
        destLng         = getIntent().getDoubleExtra("destLng", 0);
        missionCategory = getIntent().getStringExtra("missionCategory");
        missionText     = getIntent().getStringExtra("missionText");
        navName         = getIntent().getStringExtra("navName");
        navInstruction  = getIntent().getStringExtra("navInstruction");
        constraint      = getIntent().getStringExtra("constraint");

        mapView            = findViewById(R.id.map);
        missionLabel       = findViewById(R.id.text_mission_label);
        missionView        = findViewById(R.id.text_mission);
        distanceView       = findViewById(R.id.text_distance);
        navNameView        = findViewById(R.id.text_nav_name);
        navInstructionView = findViewById(R.id.text_nav_instruction);

        if (constraint != null) {
            findViewById(R.id.text_constraint_label).setVisibility(View.VISIBLE);
            TextView constraintView = findViewById(R.id.text_constraint);
            constraintView.setVisibility(View.VISIBLE);
            constraintView.setText(constraint);
        }

        if (navInstruction != null) {
            setupNavMode();
        } else {
            setupMap();
            setupLocationTracking();
        }
        updateMissionCard();

        findViewById(R.id.btn_reroll_mission).setOnClickListener(v -> rerollMission());
        findViewById(R.id.btn_reroll_all).setOnClickListener(v -> finish());
        findViewById(R.id.btn_share_mission).setOnClickListener(v -> shareMission());
        findViewById(R.id.btn_complete).setOnClickListener(v -> launchComplete());
    }

    private void setupNavMode() {
        mapView.setVisibility(View.GONE);
        distanceView.setVisibility(View.GONE);
        navNameView.setVisibility(View.VISIBLE);
        navInstructionView.setVisibility(View.VISIBLE);
        navNameView.setText(navName);
        navInstructionView.setText(navInstruction);
    }

    private void setupMap() {
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);

        GeoPoint dest  = new GeoPoint(destLat, destLng);
        GeoPoint start = new GeoPoint(startLat, startLng);
        GeoPoint mid   = new GeoPoint((startLat + destLat) / 2, (startLng + destLng) / 2);

        mapView.getController().setZoom(15.0);
        mapView.getController().setCenter(mid);

        Marker destMarker = new Marker(mapView);
        destMarker.setPosition(dest);
        destMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        destMarker.setTitle("Your destination");
        mapView.getOverlays().add(destMarker);

        youAreHereMarker = new Marker(mapView);
        youAreHereMarker.setPosition(start);
        youAreHereMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        youAreHereMarker.setTitle("You are here");
        mapView.getOverlays().add(youAreHereMarker);

        Polyline route = new Polyline();
        route.setPoints(Arrays.asList(start, dest));
        route.getOutlinePaint().setColor(0x995C4A32);
        route.getOutlinePaint().setStrokeWidth(14f);
        mapView.getOverlays().add(0, route);

        mapView.invalidate();
    }

    private void setupLocationTracking() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;

        fusedLocation = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult result) {
                if (result == null || youAreHereMarker == null) return;
                android.location.Location loc = result.getLastLocation();
                if (loc == null) return;
                youAreHereMarker.setPosition(new GeoPoint(loc.getLatitude(), loc.getLongitude()));
                mapView.invalidate();
            }
        };
    }

    private void startLocationUpdates() {
        if (fusedLocation == null || locationCallback == null) return;
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
        LocationRequest req = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 4000)
                .setMinUpdateIntervalMillis(2000)
                .build();
        fusedLocation.requestLocationUpdates(req, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        if (fusedLocation != null && locationCallback != null) {
            fusedLocation.removeLocationUpdates(locationCallback);
        }
    }

    private void updateMissionCard() {
        MissionCategory cat = MissionCategory.valueOf(missionCategory);
        missionLabel.setText(cat.getEmoji() + "  " + cat.getDisplayName());
        missionView.setText(missionText);

        if (navInstruction == null) {
            double dist = LocationUtil.distanceMeters(startLat, startLng, destLat, destLng);
            int minutes = LocationUtil.walkingMinutes(dist);
            distanceView.setText(LocationUtil.formatDistance(dist) + "  \u00b7  ~" + minutes + " min walk");
        }
    }

    private void shareMission() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, missionText);
        startActivity(Intent.createChooser(share, null));
    }

    private void rerollMission() {
        Mission m = MissionPool.random();
        missionCategory = m.getCategory().name();
        missionText     = m.getText();
        updateMissionCard();
    }

    private void launchComplete() {
        Intent intent = new Intent(this, CompleteActivity.class);
        intent.putExtra("startLat", startLat);
        intent.putExtra("startLng", startLng);
        intent.putExtra("destLat", destLat);
        intent.putExtra("destLng", destLng);
        intent.putExtra("missionCategory", missionCategory);
        intent.putExtra("missionText", missionText);
        if (navInstruction != null) intent.putExtra("navInstruction", navInstruction);
        if (constraint != null)     intent.putExtra("constraint", constraint);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (navInstruction == null) {
            mapView.onResume();
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (navInstruction == null) {
            mapView.onPause();
            stopLocationUpdates();
        }
    }
}
