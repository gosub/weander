package it.lo.exp.weander.ui.complete;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import it.lo.exp.weander.R;
import it.lo.exp.weander.data.model.Adventure;
import it.lo.exp.weander.data.repository.AdventureRepository;
import it.lo.exp.weander.missions.MissionCategory;
import it.lo.exp.weander.ui.journal.JournalActivity;

public class CompleteActivity extends Activity {

    private static final int REQ_CAMERA         = 2001;
    private static final int REQ_GALLERY        = 2002;
    private static final int PERM_CAMERA        = 2003;
    private static final int PERM_AUDIO         = 2004;

    private double startLat, startLng, destLat, destLng;
    private String missionCategory, missionText;

    private String photoPath;
    private String audioPath;
    private File currentPhotoFile;

    private ImageView photoPreview;
    private EditText textEntry;
    private Button recordBtn;
    private TextView audioStatus;
    private MediaRecorder recorder;
    private boolean isRecording = false;

    private AdventureRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        startLat        = getIntent().getDoubleExtra("startLat", 0);
        startLng        = getIntent().getDoubleExtra("startLng", 0);
        destLat         = getIntent().getDoubleExtra("destLat", 0);
        destLng         = getIntent().getDoubleExtra("destLng", 0);
        missionCategory = getIntent().getStringExtra("missionCategory");
        missionText     = getIntent().getStringExtra("missionText");

        repository = new AdventureRepository(this);

        photoPreview = findViewById(R.id.img_photo_preview);
        textEntry    = findViewById(R.id.edit_text_entry);
        recordBtn    = findViewById(R.id.btn_record_audio);
        audioStatus  = findViewById(R.id.text_audio_status);

        MissionCategory cat = MissionCategory.valueOf(missionCategory);
        ((TextView) findViewById(R.id.text_mission_label)).setText(cat.getEmoji() + "  " + cat.getDisplayName());
        ((TextView) findViewById(R.id.text_mission)).setText(missionText);

        findViewById(R.id.btn_take_photo).setOnClickListener(v -> checkCameraPermission());
        findViewById(R.id.btn_pick_photo).setOnClickListener(v -> pickGallery());
        recordBtn.setOnClickListener(v -> toggleRecording());
        findViewById(R.id.btn_save).setOnClickListener(v -> saveAdventure());
    }

    // ---- Camera ----

    private void checkCameraPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERM_CAMERA);
            return;
        }
        takePhoto();
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, "No camera app found.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            currentPhotoFile = createImageFile("CAM");
            Uri uri = FileProvider.getUriForFile(this, "it.lo.exp.weander.fileprovider", currentPhotoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, REQ_CAMERA);
        } catch (IOException e) {
            Toast.makeText(this, "Couldn\u2019t create photo file.", Toast.LENGTH_SHORT).show();
        }
    }

    // ---- Gallery ----

    private void pickGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQ_GALLERY);
    }

    // ---- Audio ----

    private void toggleRecording() {
        if (isRecording) {
            stopRecording();
        } else {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, PERM_AUDIO);
                return;
            }
            startRecording();
        }
    }

    @SuppressWarnings("deprecation")
    private void startRecording() {
        try {
            String ts   = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
            File dir    = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
            File file   = new File(dir, "WEANDER_" + ts + ".3gp");
            audioPath   = file.getAbsolutePath();

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(audioPath);
            recorder.prepare();
            recorder.start();

            isRecording = true;
            recordBtn.setText(R.string.btn_stop_recording);
            audioStatus.setVisibility(View.VISIBLE);
            audioStatus.setText("Recording\u2026");
        } catch (IOException e) {
            audioPath = null;
            Toast.makeText(this, "Recording failed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        try {
            recorder.stop();
            recorder.release();
            recorder = null;
        } catch (Exception e) {
            audioPath = null;
        }
        isRecording = false;
        recordBtn.setText(R.string.btn_record_audio);
        audioStatus.setText(audioPath != null ? "Audio recorded \u2713" : "Recording failed");
    }

    // ---- Activity results ----

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;

        if (requestCode == REQ_CAMERA) {
            photoPath = currentPhotoFile.getAbsolutePath();
            showPhotoPreview(photoPath);
        } else if (requestCode == REQ_GALLERY && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                photoPath = copyUri(uri);
                if (photoPath != null) showPhotoPreview(photoPath);
            }
        }
    }

    private String copyUri(Uri uri) {
        try {
            File dest = createImageFile("PICK");
            ContentResolver cr = getContentResolver();
            try (InputStream in = cr.openInputStream(uri);
                 FileOutputStream out = new FileOutputStream(dest)) {
                byte[] buf = new byte[4096];
                int len;
                while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
            }
            return dest.getAbsolutePath();
        } catch (IOException e) {
            Toast.makeText(this, "Couldn\u2019t import photo.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private File createImageFile(String prefix) throws IOException {
        String ts  = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File dir   = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile("WEANDER_" + prefix + "_" + ts, ".jpg", dir);
    }

    private void showPhotoPreview(String path) {
        Bitmap bm = BitmapFactory.decodeFile(path);
        if (bm != null) {
            photoPreview.setImageBitmap(bm);
            photoPreview.setVisibility(View.VISIBLE);
        }
    }

    // ---- Save ----

    private void saveAdventure() {
        String text = textEntry.getText().toString().trim();
        if (photoPath == null && text.isEmpty() && audioPath == null) {
            Toast.makeText(this, "Add a photo, text, or audio before saving.", Toast.LENGTH_SHORT).show();
            return;
        }

        Adventure adventure    = new Adventure();
        adventure.timestamp    = System.currentTimeMillis();
        adventure.startLat     = startLat;
        adventure.startLng     = startLng;
        adventure.destLat      = destLat;
        adventure.destLng      = destLng;
        adventure.missionCategory = missionCategory;
        adventure.missionText  = missionText;
        adventure.photoPath    = photoPath;
        adventure.textEntry    = text.isEmpty() ? null : text;
        adventure.audioPath    = audioPath;

        findViewById(R.id.btn_save).setEnabled(false);
        repository.insert(adventure, id -> runOnUiThread(() -> {
            Toast.makeText(this, "Adventure saved!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, JournalActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) return;
        if (requestCode == PERM_CAMERA) takePhoto();
        else if (requestCode == PERM_AUDIO) startRecording();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recorder != null) { recorder.release(); recorder = null; }
    }
}
