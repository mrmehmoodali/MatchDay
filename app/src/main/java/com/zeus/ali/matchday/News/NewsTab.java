package com.zeus.ali.matchday.News;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zeus.ali.matchday.HttpHandler;
import com.zeus.ali.matchday.R;
import com.zeus.ali.matchday.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by Ali on 1/4/2018.
 */

public class NewsTab extends Fragment {

    String API_KEY = "f64e84a553ae44ba8bdb9e79919eb545"; // ### YOUE NEWS API HERE ###
    String NEWS_SOURCE = "bbc-news";
    RecyclerView listNews;
    ProgressBar loader;
    private RecyclerView.LayoutManager nLayoutManager;
    private RecyclerView.Adapter nAdapter;
    Context thisContext;
    String passedUrl = "";

    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    static final String KEY_AUTHOR = "author";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_URLTOIMAGE = "urlToImage";
    static final String KEY_PUBLISHEDAT = "publishedAt";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.news_tab,container,false);
        thisContext = container.getContext();
        dataList = new ArrayList<>();
        loader = v.findViewById(R.id.progressBar);
        listNews = v.findViewById(R.id.news_recycler_view);
        //new DownloadNews().execute();

        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            new DownloadNews().execute();
        } else {
            Toast.makeText(
                    getActivity().getApplicationContext(), "No internet connection!", Toast.LENGTH_LONG).show();}

        return v;
    }

    private class DownloadNews extends AsyncTask<Void, Void, String> {

        //private Exception exception;

        protected void onPreExecute() {
            //loader.setVisibility(View.VISIBLE);
            super.onPreExecute();

        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String xml = sh.makeServiceCall
                    ("https://newsapi.org/v2/top-headlines?q=cryptocurrency&sortBy=popularity&apiKey="+API_KEY);


            Log.e(TAG, "Response from news: " + xml);

            return xml;
        }

        @SuppressLint("NewApi")
        protected void onPostExecute(String xml) {

            //loader.setVisibility(View.GONE);

            try {
                JSONObject jsonResponse = new JSONObject(xml);
                JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<>();
                    //map.put(KEY_AUTHOR, jsonObject.optString(KEY_AUTHOR));
                    map.put(KEY_TITLE, jsonObject.optString(KEY_TITLE));
                    map.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION));
                    map.put(KEY_URL, jsonObject.optString(KEY_URL));
                    map.put(KEY_URLTOIMAGE, jsonObject.optString(KEY_URLTOIMAGE));
                    map.put(KEY_PUBLISHEDAT, jsonObject.optString(KEY_PUBLISHEDAT));
                    dataList.add(map);
                }
                //listNews.setHasFixedSize(true);
                // use a linear layout manager
                nLayoutManager = new LinearLayoutManager(getActivity());
                listNews.setLayoutManager(nLayoutManager);
                nAdapter = new NewsAdapter(dataList);

                //listNews.addItemDecoration
                 //       (new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                listNews.setAdapter(nAdapter);

                listNews.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                        listNews, new RecyclerTouchListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        //passedUrl = dataList.get(+position).get(KEY_URL);
                        NewsDetails newsDetails = new NewsDetails();

                        Bundle newsBundle = new Bundle();
                        newsBundle.putString("url",dataList.get(+position).get(KEY_URL));
                        newsDetails.setArguments(newsBundle);

                        FragmentManager manager = getFragmentManager();
                        if (manager != null) {
                            manager.beginTransaction()
                                    .replace(R.id.newstab, newsDetails, newsDetails.getTag())
                                    .addToBackStack(null)
                                    .commit();
                        }

                        //Intent i = new Intent(thisContext , NewsDetails.class);
                        //i.putExtra("url", dataList.get(+position).get(KEY_URL));
                        //startActivity(i);
                    }

                }));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}