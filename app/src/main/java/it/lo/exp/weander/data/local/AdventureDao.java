package it.lo.exp.weander.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import it.lo.exp.weander.data.model.Adventure;

@Dao
public interface AdventureDao {

    @Insert
    long insert(Adventure adventure);

    @Query("SELECT * FROM adventures ORDER BY timestamp DESC")
    List<Adventure> getAll();

    @Query("SELECT * FROM adventures WHERE id = :id LIMIT 1")
    Adventure getById(long id);
}
