package it.lo.exp.weander.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;

import it.lo.exp.weander.R;
import it.lo.exp.weander.data.repository.AdventureRepository;
import it.lo.exp.weander.missions.Mission;
import it.lo.exp.weander.missions.MissionPool;
import it.lo.exp.weander.ui.adventure.AdventureActivity;
import it.lo.exp.weander.ui.journal.JournalActivity;
import it.lo.exp.weander.util.LocationUtil;

public class HomeActivity extends Activity {

    private static final int PERM_LOCATION = 1001;
    private static final int MIN_RADIUS = 500;
    private static final int MAX_RADIUS = 2000;

    private FusedLocationProviderClient fusedLocation;
    private Button weanderBtn;
    private TextView statsText;
    private AdventureRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        weanderBtn = findViewById(R.id.btn_weander);
        statsText = findViewById(R.id.text_stats);
        fusedLocation = LocationServices.getFusedLocationProviderClient(this);
        repository = new AdventureRepository(this);

        weanderBtn.setOnClickListener(v -> beginAdventure());
        findViewById(R.id.btn_journal).setOnClickListener(v ->
                startActivity(new Intent(this, JournalActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshStats();
    }

    private void refreshStats() {
        repository.getAll(adventures -> runOnUiThread(() -> {
            int n = adventures.size();
            if (n == 0) {
                statsText.setText("No adventures yet");
            } else {
                statsText.setText(n + (n == 1 ? " adventure" : " adventures") + " completed");
            }
        }));
    }

    private void beginAdventure() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERM_LOCATION);
            return;
        }
        weanderBtn.setEnabled(false);
        weanderBtn.setText("Finding your spot\u2026");
        fetchLocation();
    }

    private void fetchLocation() {
        CancellationTokenSource cts = new CancellationTokenSource();
        fusedLocation.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.getToken())
                .addOnSuccessListener(this, location -> {
                    weanderBtn.setEnabled(true);
                    weanderBtn.setText(R.string.btn_weander);
                    if (location == null) {
                        Toast.makeText(this, "Couldn\u2019t get your location \u2014 try again.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    launchAdventure(location);
                })
                .addOnFailureListener(this, e -> {
                    weanderBtn.setEnabled(true);
                    weanderBtn.setText(R.string.btn_weander);
                    Toast.makeText(this, "Location error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void launchAdventure(Location location) {
        double[] dest = LocationUtil.randomNearbyPoint(
                location.getLatitude(), location.getLongitude(), MIN_RADIUS, MAX_RADIUS);
        Mission mission = MissionPool.random();

        Intent intent = new Intent(this, AdventureActivity.class);
        intent.putExtra("startLat", location.getLatitude());
        intent.putExtra("startLng", location.getLongitude());
        intent.putExtra("destLat", dest[0]);
        intent.putExtra("destLng", dest[1]);
        intent.putExtra("missionCategory", mission.getCategory().name());
        intent.putExtra("missionText", mission.getText());
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERM_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                beginAdventure();
            } else {
                Toast.makeText(this, "Location permission is needed to find nearby spots.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
