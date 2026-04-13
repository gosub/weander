package it.lo.exp.weander.ui.journal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.lo.exp.weander.R;
import it.lo.exp.weander.data.model.Adventure;
import it.lo.exp.weander.data.repository.AdventureRepository;
import it.lo.exp.weander.missions.MissionCategory;

public class JournalActivity extends Activity {

    private ListView listView;
    private TextView emptyText;
    private AdventureAdapter adapter;
    private AdventureRepository repository;

    private List<Adventure> allAdventures = new ArrayList<>();
    private String activeFilter = null; // null = All
    private Button selectedChip = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        listView  = findViewById(R.id.list_adventures);
        emptyText = findViewById(R.id.text_empty);

        adapter = new AdventureAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Adventure a = adapter.getItem(position);
            if (a == null) return;
            Intent intent = new Intent(this, AdventureDetailActivity.class);
            intent.putExtra("adventureId", a.id);
            startActivity(intent);
        });

        repository = new AdventureRepository(this);
        buildChips();
        findViewById(R.id.btn_map_overview).setOnClickListener(v ->
                startActivity(new Intent(this, MapOverviewActivity.class)));
    }

    private void buildChips() {
        LinearLayout chips = findViewById(R.id.filter_chips);
        addChip(chips, "All", null);
        for (MissionCategory cat : MissionCategory.values()) {
            addChip(chips, cat.getEmoji() + " " + cat.getDisplayName(), cat.name());
        }
    }

    private void addChip(LinearLayout parent, String label, String filter) {
        Button chip = new Button(this);
        chip.setText(label);
        chip.setTextSize(12f);
        chip.setAllCaps(false);
        chip.setPadding(dp(14), dp(6), dp(14), dp(6));
        chip.setStateListAnimator(null);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMarginEnd(dp(8));
        chip.setLayoutParams(lp);

        if (filter == null) {
            // "All" is selected by default
            chip.setBackground(getDrawable(R.drawable.bg_chip_selected));
            chip.setTextColor(Color.WHITE);
            selectedChip = chip;
        } else {
            chip.setBackground(getDrawable(R.drawable.bg_btn_secondary));
            chip.setTextColor(getColor(R.color.primary));
        }

        chip.setOnClickListener(v -> selectFilter(chip, filter));
        parent.addView(chip);
    }

    private void selectFilter(Button chip, String filter) {
        if (selectedChip != null) {
            selectedChip.setBackground(getDrawable(R.drawable.bg_btn_secondary));
            selectedChip.setTextColor(getColor(R.color.primary));
        }
        chip.setBackground(getDrawable(R.drawable.bg_chip_selected));
        chip.setTextColor(Color.WHITE);
        selectedChip = chip;
        activeFilter = filter;
        applyFilter();
    }

    private void applyFilter() {
        List<Adventure> shown = new ArrayList<>();
        for (Adventure a : allAdventures) {
            if (activeFilter == null || activeFilter.equals(a.missionCategory)) shown.add(a);
        }
        adapter.clear();
        adapter.addAll(shown);
        adapter.notifyDataSetChanged();
        boolean empty = shown.isEmpty();
        emptyText.setVisibility(empty ? View.VISIBLE : View.GONE);
        listView.setVisibility(empty ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAdventures();
    }

    private void loadAdventures() {
        repository.getAll(list -> runOnUiThread(() -> {
            allAdventures = list;
            applyFilter();
        }));
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
