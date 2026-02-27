package it.lo.exp.weander.data.repository;

import android.content.Context;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.lo.exp.weander.data.local.AppDatabase;
import it.lo.exp.weander.data.local.AdventureDao;
import it.lo.exp.weander.data.model.Adventure;

public class AdventureRepository {

    public interface Callback<T> {
        void onResult(T result);
    }

    private final AdventureDao dao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public AdventureRepository(Context context) {
        dao = AppDatabase.getInstance(context).adventureDao();
    }

    public void insert(Adventure adventure, Callback<Long> onDone) {
        executor.execute(() -> {
            long id = dao.insert(adventure);
            if (onDone != null) onDone.onResult(id);
        });
    }

    public void getAll(Callback<List<Adventure>> onDone) {
        executor.execute(() -> {
            List<Adventure> list = dao.getAll();
            if (onDone != null) onDone.onResult(list);
        });
    }

    public void getById(long id, Callback<Adventure> onDone) {
        executor.execute(() -> {
            Adventure a = dao.getById(id);
            if (onDone != null) onDone.onResult(a);
        });
    }
}
