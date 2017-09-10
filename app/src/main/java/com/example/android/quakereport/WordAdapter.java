package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by NoraElhariri on 8/14/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    public WordAdapter(Context context, ArrayList<Word> words){super(context,0,words);}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        DecimalFormat magFormatter = new DecimalFormat("0.0");
        String formattedMag = magFormatter.format(currentWord.getMag());

        TextView magView = (TextView) listItemView.findViewById(R.id.mag);
        magView.setText(formattedMag);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentWord.getMag());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        TextView locatOffsetView = (TextView) listItemView.findViewById(R.id.locat_offset);
        locatOffsetView.setText(currentWord.getLocatOffset());

        TextView primaryLocatView = (TextView) listItemView.findViewById(R.id.primary_locat);
        primaryLocatView.setText(currentWord.getPrimaryLocat());

        Date dateObject = new Date(currentWord.getDate());

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        String formattedDate = dateFormat(dateObject);
        dateView.setText(formattedDate);

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        String formattedTime = timeFormat(dateObject);
        timeView.setText(formattedTime);

        return listItemView;
    }

    public int getMagnitudeColor(double mag){
        int magColorResId;
        int magFloor = (int) Math.floor(mag);
        switch(magFloor) {
            case 0:
            case 1:
                magColorResId = R.color.magnitude1;
                break;
            case 2:
                magColorResId = R.color.magnitude2;
                break;
            case 3:
                magColorResId = R.color.magnitude3;
                break;
            case 4:
                magColorResId = R.color.magnitude4;
                break;
            case 5:
                magColorResId = R.color.magnitude5;
                break;
            case 6:
                magColorResId = R.color.magnitude6;
                break;
            case 7:
                magColorResId = R.color.magnitude7;
                break;
            case 8:
                magColorResId = R.color.magnitude8;
                break;
            case 9:
                magColorResId = R.color.magnitude9;
                break;
            default:
                magColorResId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magColorResId);
    }

    private String dateFormat (Date dateObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormatter.format(dateObject);

    }

    private String timeFormat (Date dateObject)
    {

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a");
        return timeFormatter.format(dateObject);
    }
}
