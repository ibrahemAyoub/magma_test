package com.example.magmatest.placedirections;

import android.content.Context;
import android.os.AsyncTask;

import com.example.magmatest.DownloadURL;

import java.io.IOException;

public class FetchPath extends AsyncTask<String, Void, String> {
    Context mContext;
    String directionMode = "driving";

    public FetchPath(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = "";
        DownloadURL downloadURL = new DownloadURL();
        try {
            data = downloadURL.readUrl(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        PointsParser parserTask = new PointsParser(mContext, directionMode);
        // Invokes the thread for parsing the JSON data
        parserTask.execute(s);
    }
}
