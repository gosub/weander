package it.lo.exp.weander.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "adventures")
public class Adventure {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long timestamp;
    public double startLat;
    public double startLng;
    public double destLat;
    public double destLng;
    public String missionCategory;
    public String missionText;
    public String photoPath;
    public String textEntry;
    public String audioPath;
}
