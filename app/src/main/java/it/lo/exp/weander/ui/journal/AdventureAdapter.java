package it.lo.exp.weander.ui.journal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.lo.exp.weander.R;
import it.lo.exp.weander.data.model.Adventure;
import it.lo.exp.weander.missions.MissionCategory;

public class AdventureAdapter extends ArrayAdapter<Adventure> {

    private final LayoutInflater inflater;

    public AdventureAdapter(Context context, List<Adventure> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_adventure, parent, false);
        }
        Adventure a = getItem(position);
        if (a == null) return convertView;

        TextView dateText     = convertView.findViewById(R.id.text_date);
        TextView categoryText = convertView.findViewById(R.id.text_category);
        TextView missionSnip  = convertView.findViewById(R.id.text_mission_snippet);
        ImageView thumbnail   = convertView.findViewById(R.id.img_thumbnail);

        dateText.setText(new SimpleDateFormat("EEE d MMM yyyy", Locale.getDefault())
                .format(new Date(a.timestamp)));

        MissionCategory cat = MissionCategory.valueOf(a.missionCategory);
        categoryText.setText(cat.getEmoji() + "  " + cat.getDisplayName());

        missionSnip.setText(a.missionText);

        if (a.photoPath != null) {
            Bitmap bm = thumbnail(a.photoPath);
            if (bm != null) {
                thumbnail.setImageBitmap(bm);
                thumbnail.setVisibility(View.VISIBLE);
            } else {
                thumbnail.setVisibility(View.GONE);
            }
        } else {
            thumbnail.setVisibility(View.GONE);
        }

        return convertView;
    }

    private Bitmap thumbnail(String path) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 4;
        return BitmapFactory.decodeFile(path, opts);
    }
}
