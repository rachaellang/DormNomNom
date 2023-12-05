package com.cs407.dormnomnom;

import android.os.AsyncTask;
import org.json.JSONException;

import java.util.ArrayList;

public class GetRequestTask extends AsyncTask<String, Void, ArrayList<Station>> {
    // This interface is used to send data back to the calling activity
    public interface AsyncResponse {
        void processFinish(ArrayList<Station> output);
    }

    private AsyncResponse delegate = null;

    public GetRequestTask(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<Station> doInBackground(String... params) {
        // Perform the network request in the background
        try {
            GetRequest request = new GetRequest(params[0], params[1], params[2], params[3], params[4]);
            // Assuming GetRequest has a method to execute the request and return a result
            return request.getStations();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Station> result) {
        // This method is called on the main thread, process the result here
        delegate.processFinish(result);
    }
}

