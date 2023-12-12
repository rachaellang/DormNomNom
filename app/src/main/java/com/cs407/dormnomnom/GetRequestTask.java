package com.cs407.dormnomnom;

import android.os.AsyncTask;
import org.json.JSONException;

import java.util.ArrayList;

public class GetRequestTask extends AsyncTask<String, Void, ArrayList<Station>> {
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
            return request.getStations();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    protected ArrayList<FoodItem> getMenu(String... params) {
        try {
            GetRequest request = new GetRequest(params[0], params[1], params[2], params[3], params[4]);
            return request.getMenuItems();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onPostExecute(ArrayList<Station> result) {
        delegate.processFinish(result);
    }
}

