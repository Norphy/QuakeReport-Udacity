package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by NoraElhariri on 8/21/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Word>> {

    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.v(LOG_TAG, "onStartLoading accessed");
    }

    @Override
    public ArrayList<Word> loadInBackground() {

        if(mUrl == null){
            return null;
        }

        // Create a fake list of earthquake locations.
        ArrayList<Word> earthquakes = QueryUtils.extractEarthquakes(mUrl);

        Log.v(LOG_TAG, "loadInBackground accessed");
        return earthquakes;
    }
}
