package it.lo.exp.weander.ui.journal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

import it.lo.exp.weander.R;
import it.lo.exp.weander.data.model.Adventure;
import it.lo.exp.weander.data.repository.AdventureRepository;
import it.lo.exp.weander.missions.MissionCategory;

public class MapOverviewActivity extends Activity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_overview);

        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(13.0);

        new AdventureRepository(this).getAll(list -> runOnUiThread(() -> plotAdventures(list)));
    }

    private void plotAdventures(List<Adventure> adventures) {
        List<GeoPoint> points = new ArrayList<>();
        for (Adventure a : adventures) {
            if (a.destLat == 0 && a.destLng == 0) continue;
            GeoPoint pt = new GeoPoint(a.destLat, a.destLng);
            points.add(pt);

            MissionCategory cat = MissionCategory.valueOf(a.missionCategory);
            Marker m = new Marker(mapView);
            m.setPosition(pt);
            m.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            m.setTitle(cat.getEmoji() + " " + cat.getDisplayName());
            m.setSnippet(a.missionText.length() > 60
                    ? a.missionText.substring(0, 60) + "\u2026"
                    : a.missionText);

            final long id = a.id;
            m.setOnMarkerClickListener((marker, mapV) -> {
                Intent intent = new Intent(this, AdventureDetailActivity.class);
                intent.putExtra("adventureId", id);
                startActivity(intent);
                return true;
            });
            mapView.getOverlays().add(m);
        }

        if (!points.isEmpty()) {
            if (points.size() == 1) {
                mapView.getController().setCenter(points.get(0));
            } else {
                BoundingBox box = BoundingBox.fromGeoPoints(points);
                mapView.post(() -> mapView.zoomToBoundingBox(box.increaseByScale(1.4f), true));
            }
        }
        mapView.invalidate();
    }

    @Override protected void onResume() { super.onResume(); mapView.onResume(); }
    @Override protected void onPause()  { super.onPause();  mapView.onPause();  }
}
