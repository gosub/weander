package it.lo.exp.weander.ui.adventure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import it.lo.exp.weander.R;
import it.lo.exp.weander.missions.Mission;
import it.lo.exp.weander.missions.MissionCategory;
import it.lo.exp.weander.missions.MissionPool;
import it.lo.exp.weander.ui.complete.CompleteActivity;
import it.lo.exp.weander.util.LocationUtil;

public class AdventureActivity extends Activity {

    private double startLat, startLng, destLat, destLng;
    private String missionCategory, missionText;

    private MapView mapView;
    private TextView missionLabel;
    private TextView missionView;
    private TextView distanceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure);

        startLat = getIntent().getDoubleExtra("startLat", 0);
        startLng = getIntent().getDoubleExtra("startLng", 0);
        destLat  = getIntent().getDoubleExtra("destLat", 0);
        destLng  = getIntent().getDoubleExtra("destLng", 0);
        missionCategory = getIntent().getStringExtra("missionCategory");
        missionText     = getIntent().getStringExtra("missionText");

        mapView      = findViewById(R.id.map);
        missionLabel = findViewById(R.id.text_mission_label);
        missionView  = findViewById(R.id.text_mission);
        distanceView = findViewById(R.id.text_distance);

        setupMap();
        updateMissionCard();

        findViewById(R.id.btn_reroll_mission).setOnClickListener(v -> rerollMission());
        findViewById(R.id.btn_reroll_all).setOnClickListener(v -> finish());
        findViewById(R.id.btn_complete).setOnClickListener(v -> launchComplete());
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

        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(start);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setTitle("You are here");
        mapView.getOverlays().add(startMarker);

        mapView.invalidate();
    }

    private void updateMissionCard() {
        MissionCategory cat = MissionCategory.valueOf(missionCategory);
        missionLabel.setText(cat.getEmoji() + "  " + cat.getDisplayName());
        missionView.setText(missionText);

        double dist  = LocationUtil.distanceMeters(startLat, startLng, destLat, destLng);
        int minutes  = LocationUtil.walkingMinutes(dist);
        distanceView.setText(LocationUtil.formatDistance(dist) + "  \u00b7  ~" + minutes + " min walk");
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
        startActivity(intent);
        finish();
    }

    @Override protected void onResume() { super.onResume(); mapView.onResume(); }
    @Override protected void onPause()  { super.onPause();  mapView.onPause();  }
}
