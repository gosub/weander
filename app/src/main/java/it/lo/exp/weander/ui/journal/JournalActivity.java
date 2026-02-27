package it.lo.exp.weander.ui.journal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import it.lo.exp.weander.R;
import it.lo.exp.weander.data.model.Adventure;
import it.lo.exp.weander.data.repository.AdventureRepository;

public class JournalActivity extends Activity {

    private ListView listView;
    private TextView emptyText;
    private AdventureAdapter adapter;
    private AdventureRepository repository;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAdventures();
    }

    private void loadAdventures() {
        repository.getAll(list -> runOnUiThread(() -> {
            adapter.clear();
            adapter.addAll(list);
            adapter.notifyDataSetChanged();
            boolean empty = list.isEmpty();
            emptyText.setVisibility(empty ? View.VISIBLE : View.GONE);
            listView.setVisibility(empty ? View.GONE : View.VISIBLE);
        }));
    }
}
