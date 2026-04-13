package it.lo.exp.weander.ui.journal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import it.lo.exp.weander.R;
import it.lo.exp.weander.data.model.Adventure;
import it.lo.exp.weander.data.repository.AdventureRepository;
import it.lo.exp.weander.missions.MissionCategory;

public class AdventureDetailActivity extends Activity {

    private MapView mapView;
    private boolean isNavMode = false;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure_detail);

        long id = getIntent().getLongExtra("adventureId", -1);
        if (id == -1) { finish(); return; }

        mapView = findViewById(R.id.map);

        new AdventureRepository(this).getById(id, adventure ->
                runOnUiThread(() -> {
                    if (adventure == null) { finish(); return; }
                    bind(adventure);
                }));
    }

    private void bind(Adventure a) {
        TextView dateView     = findViewById(R.id.text_date);
        TextView catLabel     = findViewById(R.id.text_category_label);
        TextView missionView  = findViewById(R.id.text_mission);
        TextView entryView    = findViewById(R.id.text_entry);
        ImageView photoView   = findViewById(R.id.img_photo);
        Button playBtn        = findViewById(R.id.btn_play_audio);

        dateView.setText(new SimpleDateFormat("EEEE, d MMMM yyyy \u00b7 HH:mm", Locale.getDefault())
                .format(new Date(a.timestamp)));

        MissionCategory cat = MissionCategory.valueOf(a.missionCategory);
        catLabel.setText(cat.getEmoji() + "  " + cat.getDisplayName());
        missionView.setText(a.missionText);

        if (a.textEntry != null && !a.textEntry.isEmpty()) {
            entryView.setVisibility(View.VISIBLE);
            entryView.setText(a.textEntry);
        } else {
            entryView.setVisibility(View.GONE);
        }

        if (a.photoPath != null) {
            Bitmap bm = decodeSampled(a.photoPath, 1024);
            if (bm != null) {
                photoView.setImageBitmap(bm);
                photoView.setVisibility(View.VISIBLE);
            }
        }

        if (a.audioPath != null) {
            playBtn.setVisibility(View.VISIBLE);
            playBtn.setOnClickListener(v -> playAudio(a.audioPath));
        } else {
            playBtn.setVisibility(View.GONE);
        }

        findViewById(R.id.btn_share).setOnClickListener(v -> shareAdventure(a));

        if (a.navigationText != null) {
            isNavMode = true;
            mapView.setVisibility(View.GONE);
            TextView navView = findViewById(R.id.text_nav_instruction);
            navView.setVisibility(View.VISIBLE);
            navView.setText(a.navigationText);
        } else {
            setupMap(a);
        }
    }

    private void setupMap(Adventure a) {
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        GeoPoint pt = new GeoPoint(a.destLat, a.destLng);
        mapView.getController().setZoom(15.0);
        mapView.getController().setCenter(pt);
        Marker m = new Marker(mapView);
        m.setPosition(pt);
        m.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(m);
        mapView.invalidate();
    }

    private void shareAdventure(Adventure a) {
        MissionCategory cat = MissionCategory.valueOf(a.missionCategory);
        String date = new java.text.SimpleDateFormat("d MMMM yyyy", java.util.Locale.getDefault())
                .format(new java.util.Date(a.timestamp));
        StringBuilder text = new StringBuilder();
        text.append(cat.getEmoji()).append(" ").append(cat.getDisplayName()).append(" · ").append(date).append("\n\n");
        text.append(a.missionText);
        if (a.textEntry != null && !a.textEntry.isEmpty()) {
            text.append("\n\n").append(a.textEntry);
        }

        if (a.photoPath != null) {
            Uri photoUri = FileProvider.getUriForFile(this, "it.lo.exp.weander.fileprovider", new File(a.photoPath));
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_TEXT, text.toString());
            intent.putExtra(Intent.EXTRA_STREAM, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, null));
        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text.toString());
            startActivity(Intent.createChooser(intent, null));
        }
    }

    private static Bitmap decodeSampled(String path, int maxDim) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);
        int sample = 1;
        while (Math.max(opts.outWidth, opts.outHeight) / sample > maxDim) sample *= 2;
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = sample;
        return BitmapFactory.decodeFile(path, opts);
    }

    private void playAudio(String path) {
        if (mediaPlayer != null) { mediaPlayer.stop(); mediaPlayer.release(); mediaPlayer = null; }
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(this, "Playing\u2026", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Couldn\u2019t play audio.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override protected void onResume() { super.onResume(); if (!isNavMode) mapView.onResume(); }
    @Override protected void onPause()  { super.onPause();  if (!isNavMode) mapView.onPause();  }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) { mediaPlayer.release(); mediaPlayer = null; }
    }
}
