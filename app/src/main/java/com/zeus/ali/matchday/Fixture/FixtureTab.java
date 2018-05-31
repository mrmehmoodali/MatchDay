package com.zeus.ali.matchday.Fixture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by Ali on 1/4/2018.
 */

public class FixtureTab extends Fragment {

    //private static final String API_URL = "https://koinex.in/api/ticker";
    private static final String API_URL = "http://api.football-data.org/v1/competitions/467/fixtures";
    ArrayList<HashMap<String, String>> cryptoList;

    static final String KEY_24H_VOLUME_INR	           = "24h_volume_inr"
                       ,KEY_24H_VOLUME_USD	           = "24h_volume_usd"
                       ,KEY_AVAILABLE_SUPPLY	       = "available_supply"
                       ,KEY_ID		                   = "id"
                       ,KEY_LAST_UPDATED	           = "last_updated"
                       ,KEY_MARKET_CAP_INR	           = "market_cap_inr"
                       ,KEY_MARKET_CAP_USD	           = "market_cap_usd"
                       ,KEY_MAX_SUPPLY	               = "max_supply"
                       , AWAY_TEAM_NAME = "awayTeamName"
                       ,KEY_PERCENT_CHANGE_1H          = "percent_change_1h"
                       ,KEY_PERCENT_CHANGE_24H         = "percent_change_24h"
                       ,KEY_PERCENT_CHANGE_7D          = "percent_change_7d"
                       ,KEY_PRICE_BTC		           = "price_btc"
                       ,KEY_PRICE_INR		           = "price_inr"
                       ,KEY_PRICE_USD	               = "price_usd"
                       ,KEY_RANK		               = "rank"
                       , HOME_TEAM_NAME = "homeTeamName"
                       ,KEY_TOTAL_SUPPLY	           = "total_supply";

    int[] icons = new int[]{
            R.drawable.ic_btc,
            R.drawable.ic_eth,
            R.drawable.ic_xrp,
            R.drawable.ic_bch,
            R.drawable.ic_ltc,
            R.drawable.ic_miota,
            R.drawable.ic_omg,
            R.drawable.ic_gnt,
    };
    ProgressBar mProgressBar;
    //TextView mResponseView;
    //ListView mListView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.ticker_tab,container,false);
        //mResponseView = v.findViewById(R.id.responseView);
        //mListView = v.findViewById(R.id.list);
        cryptoList = new ArrayList<>();
        mProgressBar = v.findViewById(R.id.progressBar);
        recyclerView = v.findViewById(R.id.my_recycler_view);
        swipeRefreshLayout = v.findViewById(R.id.swiperefresh);

        //new RetrieveFeedTask().execute();


        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            new RetrieveFeedTask().execute();
        } else {
            Toast.makeText(
                    getActivity().getApplicationContext(), "No internet connection!", Toast.LENGTH_LONG).show();}


        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        ConnectivityManager cm = (ConnectivityManager)
                                getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        if (null != activeNetwork) {
                        } else {
                            Toast.makeText(
                                    getActivity().getApplicationContext(), "No internet connection!", Toast.LENGTH_LONG).show();}
                        swipeRefreshLayout.setRefreshing(true);
                        cryptoList = new ArrayList<>();
                        new RetrieveFeedTask().execute();

                    }
                }
        );

        return v;

    }

    /*@Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mResponseView = getView().findViewById(R.id.responseView);
    }*/

    private class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        //private Exception exception;

        protected void onPreExecute() {

            mProgressBar.setVisibility(View.VISIBLE);
            //mResponseView.setText(" ");

        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String response = sh.makeServiceCall(API_URL);

            Log.e(TAG, "Response from url: " + response);

            return response;
        }

        @SuppressLint("NewApi")
        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            mProgressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            //Log.i("INFO", response);
            //mResponseView.setText(response);
            //swipeRefreshLayout.setRefreshing(false);
            // TODO: check this.exception
            // TODO: do something with the feed

            try {

                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("fixtures");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<>();
                    /*map.put(KEY_24H_VOLUME_INR	  , jsonObject.optString(KEY_24H_VOLUME_INR	  ));
                    map.put(KEY_24H_VOLUME_USD	  , jsonObject.optString(KEY_24H_VOLUME_USD	  ));
                    map.put(KEY_AVAILABLE_SUPPLY  , jsonObject.optString(KEY_AVAILABLE_SUPPLY));
                    map.put(KEY_ID		          , jsonObject.optString(KEY_ID		          ));
                    map.put(KEY_LAST_UPDATED	  , jsonObject.optString(KEY_LAST_UPDATED	  ));
                    map.put(KEY_MARKET_CAP_INR	  , jsonObject.optString(KEY_MARKET_CAP_INR	  ));
                    map.put(KEY_MARKET_CAP_USD	  , jsonObject.optString(KEY_MARKET_CAP_USD	  ));
                    map.put(KEY_MAX_SUPPLY	      , jsonObject.optString(KEY_MAX_SUPPLY	      ));*/
                    map.put(AWAY_TEAM_NAME, jsonObject.optString(AWAY_TEAM_NAME));
                    /*map.put(KEY_PERCENT_CHANGE_1H , jsonObject.optString(KEY_PERCENT_CHANGE_1H )+"%");
                    //Log.e(TAG, "Percent of " + jsonObject.getInt(KEY_PERCENT_CHANGE_1H));
                    map.put(KEY_PERCENT_CHANGE_24H, jsonObject.optString(KEY_PERCENT_CHANGE_24H)+"%");
                    map.put(KEY_PERCENT_CHANGE_7D , jsonObject.optString(KEY_PERCENT_CHANGE_7D )+"%");
                    map.put(KEY_PRICE_BTC		  , jsonObject.optString(KEY_PRICE_BTC		  ));

                    //DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    //Log.e(TAG, "Percent of " + jsonObject.optString(KEY_PERCENT_CHANGE_7D ));

                    String tempPrice1 = jsonObject.optString(KEY_PRICE_INR);
                    String tempPrice2 = tempPrice1.substring(0,tempPrice1.indexOf(".")+3);

                    map.put(KEY_PRICE_INR		  , tempPrice2);


                    map.put(KEY_PRICE_USD	      , jsonObject.optString(KEY_PRICE_USD	      ));
                    map.put(KEY_RANK		      , jsonObject.optString(KEY_RANK		      ));*/
                    map.put(HOME_TEAM_NAME, jsonObject.optString(HOME_TEAM_NAME));
                    //map.put(KEY_TOTAL_SUPPLY	  , jsonObject.optString(KEY_TOTAL_SUPPLY	  ));
                    cryptoList.add(map);
                }
                /*JSONObject jsonObj = new JSONObject(response);
                JSONObject rate = jsonObj.getJSONObject("prices");
                JSONObject stats = jsonObj.getJSONObject("stats");
                Integer i = 0;
                for(Iterator<String> iter1 = rate.keys(); iter1.hasNext();) {
                    HashMap<String, String> temp = new HashMap<>();
                    String key1 = iter1.next();
                    Object price = rate.get(key1);
                    temp.put("id", key1);
                    temp.put("price", String.valueOf(price));
                    temp.put("icon", Integer.toString(icons[i]) );
                    i++;
                    //Log.i("iter", String.valueOf(i));
                    for(Iterator<String> iter2 = stats.keys(); iter2.hasNext();) {
                        String key2 = iter2.next();

                        if (Objects.equals(key1, key2)) {
                            //Log.i("KEY1+KEY2", key1+"+"+key2);
                            JSONObject coin = stats.getJSONObject(key2);
                            String highestBid = coin.getString("highest_bid");
                            String lastTradedPrice = coin.getString("last_traded_price");
                            String lowestAsk = coin.getString("lowest_ask");
                            String max24hrs = coin.getString("max_24hrs");
                            String min24hrs = coin.getString("min_24hrs");
                            String vol24hrs = coin.getString("vol_24hrs");
                            temp.put("hbid", highestBid);
                            temp.put("ltprice", lastTradedPrice);
                            temp.put("lask", lowestAsk);
                            temp.put("percent1H", max24hrs);
                            temp.put("percent24H", min24hrs);
                            temp.put("vol24", vol24hrs);
                        }
                    }
                    cryptoList.add(temp);

                }*/

                recyclerView.setHasFixedSize(true);
                // use a linear layout manager
                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                mAdapter = new FixtureAdaptor(cryptoList, recyclerView);

                //recyclerView.addItemDecoration
                //        (new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(mAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}