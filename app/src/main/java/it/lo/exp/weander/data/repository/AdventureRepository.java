package it.lo.exp.weander.data.repository;

import android.content.Context;

import java.util.Calendar;
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

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final AdventureDao dao;

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

    /** Returns int[2]: [totalCount, currentStreakDays]. */
    public void getStats(Callback<int[]> onDone) {
        executor.execute(() -> {
            List<Long> timestamps = dao.getAllTimestamps();
            int total = timestamps.size();
            int streak = 0;
            if (!timestamps.isEmpty()) {
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                today.set(Calendar.MILLISECOND, 0);
                long dayMs = 86_400_000L;
                long cursor = today.getTimeInMillis();
                for (long ts : timestamps) {
                    if (ts >= cursor) {
                        // this timestamp is within the current expected day window — keep going
                    } else if (ts >= cursor - dayMs) {
                        // falls in expected day
                    } else {
                        // gap found — stop
                        break;
                    }
                    if (ts < cursor) cursor -= dayMs;
                }
                // recompute cleanly: count distinct days from today backwards with no gap
                streak = 0;
                cursor = today.getTimeInMillis();
                int i = 0;
                while (i < timestamps.size()) {
                    long windowStart = cursor - dayMs;
                    boolean found = false;
                    while (i < timestamps.size() && timestamps.get(i) >= windowStart) {
                        if (timestamps.get(i) < cursor) found = true;
                        i++;
                    }
                    if (!found) break;
                    streak++;
                    cursor -= dayMs;
                }
            }
            if (onDone != null) onDone.onResult(new int[]{total, streak});
        });
    }
}
