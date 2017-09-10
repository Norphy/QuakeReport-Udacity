package com.example.android.quakereport;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.res.Resources.getSystem;
import static java.security.AccessController.getContext;

/**
 * Created by NoraElhariri on 8/14/2017.
 */


public class Word {

    /* private variable containing the Magnitude*/
    private double mMag;

    /* private variable containing the Location*/
    private String mLocat;

    private String mPrimaryLocat;

    private String mLocatOffset;

    private static final String LOCATION_SEPARATOR = " of ";

    /* private variable containing the Date*/
    private long mDateMillis;

    private String mDate;

    private String mTime;

    private String mUrl;

    public Word(double mag, String locat, long date, String url) {

        mMag = mag;

        mLocat = locat;
        if(mLocat.contains(LOCATION_SEPARATOR))
        {
            int indOf = mLocat.indexOf('f',mLocat.indexOf(LOCATION_SEPARATOR));
            indOf++;
            mLocatOffset = mLocat.substring(0,indOf);
            mPrimaryLocat= mLocat.substring(indOf,mLocat.length());

        }
        else{
            mLocatOffset = "Near the";
            mPrimaryLocat = mLocat;
        }

        mDateMillis = date;/*
        Date dateObject = new Date(mDateMillis);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a");
        mDate = dateFormatter.format(dateObject);
        mTime = timeFormatter.format(dateObject);*/

        mUrl = url;
    }

    public double getMag() {return mMag;}

    public String getPrimaryLocat() {
        return mPrimaryLocat;
    }

    public String getLocatOffset() {
        return mLocatOffset;
    }

    public Long getDate() {
        return mDateMillis;
    }

    public String getUrl(){
        return mUrl;
    }
}
