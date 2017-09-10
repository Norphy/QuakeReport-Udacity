package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.input;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    /*Return a URL Object from passing in {@link url} string*/
    public static URL getUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        }
        catch(MalformedURLException e) {
            Log.e(LOG_TAG,"Error creating URL", e);
        }
        return url;
    }

    /*Set a helper method to read from an input stream and return a String*/
    private static String readFromStream(InputStream is) throws IOException{
        StringBuilder output = new StringBuilder();
        if(is != null){
            InputStreamReader isReader = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader bReader = new BufferedReader(isReader);
            String line = bReader.readLine();
            while(line != null){
                output.append(line);
                line = bReader.readLine();
            }
        }
        return output.toString();
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }
        catch(IOException e){
            Log.e(LOG_TAG, "Error retrieving earthquake JSON results",e);
        } finally {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if(inputStream != null)
            {
                inputStream.close();
            }
        }
        return jsonResponse;
    }



    /**
     * Return a list of {@link Word} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Word> extractEarthquakes(String stringUrl) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        URL url = QueryUtils.getUrl(stringUrl);
        ArrayList<Word> earthQuakes = new ArrayList<>();

        try {
            String jsonResponse = QueryUtils.makeHttpRequest(url);
            earthQuakes = extractFromJson(jsonResponse);
        } catch (IOException e) {
            Log.e(LOG_TAG,"Error closing inputstream",e);
        }
        return earthQuakes;

    }

    private static ArrayList<Word> extractFromJson(String jsonResponse) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Word> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            JSONObject earthquakeObject = new JSONObject(jsonResponse);
            JSONArray featuresArray = earthquakeObject.getJSONArray("features");
            for (int i = 0;i < featuresArray.length();i++) {
                JSONObject earthquake1 = featuresArray.getJSONObject(i);
                JSONObject propertiesObject = earthquake1.getJSONObject("properties");

                double mag = propertiesObject.getDouble("mag");
                String place = propertiesObject.getString("place");
                long time = propertiesObject.getLong("time");
                String url = propertiesObject.getString("url");

                Word word = new Word(mag,place,time,url);
                earthquakes.add(word);
            }
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}