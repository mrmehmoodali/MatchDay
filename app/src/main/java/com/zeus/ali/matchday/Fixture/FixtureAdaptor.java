package com.zeus.ali.matchday.Fixture;

import android.content.Context;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcinorlowski.datetimetemplate.DateTimeTemplate;
import com.zeus.ali.matchday.R;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by Ali on 1/11/2018.
 */

public class FixtureAdaptor extends RecyclerView.Adapter<FixtureAdaptor.ViewHolder> {

    private Context context;
    private ArrayList<HashMap<String,String>> values;
    private int mExpandedPosition = -1;
    private RecyclerView passedRView;
    private ConstraintLayout rootConst;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    /*public FixtureAdaptor(Context context) {
        this.context = context;
    }*/


    // Provide a suitable constructor (depends on the kind of dataset)
    public FixtureAdaptor(ArrayList<HashMap<String,String>> myDataset) {
        values = myDataset;
    }

    public FixtureAdaptor(ArrayList<HashMap<String,String>> myDataset, RecyclerView passedRView) {
        values = myDataset;
        this.passedRView = passedRView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView homeTeamName;
        public TextView price;
        public TextView awayTeamName;
        public TextView percent1H;
        public TextView percent24H;
        public TextView percent7D;

        public TextView  AVAILABLE_SUPPLY
                        ,LAST_UPDATED
                        ,MARKET_CAP_INR
                        ,MARKET_CAP_USD
                        ,MAX_SUPPLY
                        ,PRICE_USD
                        ,TOTAL_SUPPLY
                        ,VOLUME_24INR
                        ,VOLUME_24USD;

        public ImageView imageView;
        public View layout;
        CardView cardViewTicker;
        public ConstraintLayout constraintLayout;
        public ViewGroup testView;

        public ViewHolder(View v) {
            super(v);
            layout      = v;
            homeTeamName = v.findViewById(R.id.homeTeamName);
            price       = v.findViewById(R.id.price);
            awayTeamName = v.findViewById(R.id.awayTeamName);
            /*percent1H   = v.findViewById(R.id.per1H);
            percent24H  = v.findViewById(R.id.per24H);
            percent7D   = v.findViewById(R.id.per7day);*/

            AVAILABLE_SUPPLY   = v.findViewById(R.id.AVAILABLE_SUPPLY);
            LAST_UPDATED	   = v.findViewById(R.id.LAST_UPDATED	);
            MARKET_CAP_INR	   = v.findViewById(R.id.MARKET_CAP_INR	);
            MARKET_CAP_USD	   = v.findViewById(R.id.MARKET_CAP_USD	);
            MAX_SUPPLY         = v.findViewById(R.id.MAX_SUPPLY      );
            PRICE_USD          = v.findViewById(R.id.PRICE_USD       );
            TOTAL_SUPPLY       = v.findViewById(R.id.TOTAL_SUPPLY    );
            VOLUME_24INR       = v.findViewById(R.id.VOLUME_24INR    );
            VOLUME_24USD       = v.findViewById(R.id.VOLUME_24USD    );

            imageView   = v.findViewById(R.id.imageView);
            cardViewTicker = v.findViewById(R.id.cardViewTicker);
            constraintLayout = v.findViewById(R.id.constraintLayout);
            testView = v.findViewById(R.id.my_recycler_view);
            rootConst = v.findViewById(R.id.rootConst);
        }
    }


    @Override
    public FixtureAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View view =
                inflater.inflate(R.layout.ticker_list_items, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //YoYo.with(Techniques.FadeIn).playOn(holder.cardViewTicker);
        HashMap<String,String> map = values.get(position);

        if (map.get(FixtureTab.HOME_TEAM_NAME).equals("")){
            holder.homeTeamName.setText(R.string.TBD);
        } else {
            holder.homeTeamName.setText(map.get(FixtureTab.HOME_TEAM_NAME));
        }

        if (map.get(FixtureTab.AWAY_TEAM_NAME).equals("")){
            holder.awayTeamName.setText(R.string.TBD);
        } else {
            holder.awayTeamName.setText(map.get(FixtureTab.AWAY_TEAM_NAME));
        }

        TimeZone tz = TimeZone.getDefault();
        Calendar c = new GregorianCalendar(tz);

        Instant instant = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            instant = Instant.parse(map.get(FixtureTab.MATCH_DATE));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            c.setTime(Date.from(instant));
        }

        String formatted = DateTimeTemplate.format(c, "%d% %MM% %DD% %hh%:%ii%");

        //holder.price.setText(map.get(FixtureTab.KEY_PRICE_INR));
        holder.price.setText(formatted);





        /*holder.percent1H.setText(map.get(FixtureTab.KEY_PERCENT_CHANGE_1H));

        if (map.get(FixtureTab.KEY_PERCENT_CHANGE_1H).substring(0,1).equals("-")){
            holder.percent1H.setTextColor(Color.RED);
        }
        else{
            holder.percent1H.setTextColor(Color.GREEN);
        }

        holder.percent24H.setText(map.get(FixtureTab.KEY_PERCENT_CHANGE_24H));

        if (map.get(FixtureTab.KEY_PERCENT_CHANGE_24H).substring(0,1).equals("-")){
            holder.percent24H.setTextColor(Color.RED);
        }
        else{
            holder.percent24H.setTextColor(Color.GREEN);
        }

        holder.percent7D.setText(map.get(FixtureTab.KEY_PERCENT_CHANGE_7D));

        if (map.get(FixtureTab.KEY_PERCENT_CHANGE_7D).substring(0,1).equals("-")){
            holder.percent7D.setTextColor(Color.RED);
        }
        else{
            holder.percent7D.setTextColor(Color.GREEN);
        }


        holder.AVAILABLE_SUPPLY.setText(map.get(FixtureTab.KEY_AVAILABLE_SUPPLY   ));
        holder.LAST_UPDATED	   .setText(getTimeAgo(Long.parseLong(map.get(FixtureTab.KEY_LAST_UPDATED))));
        holder.MARKET_CAP_INR  .setText(map.get(FixtureTab.KEY_MARKET_CAP_INR     ));
        holder.MARKET_CAP_USD  .setText(map.get(FixtureTab.KEY_MARKET_CAP_USD   ));
        holder.MAX_SUPPLY      .setText(map.get(FixtureTab.KEY_MAX_SUPPLY));
        holder.PRICE_USD       .setText(map.get(FixtureTab.KEY_PRICE_USD     ));
        holder.TOTAL_SUPPLY    .setText(map.get(FixtureTab.KEY_TOTAL_SUPPLY   ));
        holder.VOLUME_24INR    .setText(map.get(FixtureTab.KEY_24H_VOLUME_INR));
        holder.VOLUME_24USD    .setText(map.get(FixtureTab.KEY_24H_VOLUME_USD     ));*/
        //holder.imageView.setImageResource(Integer.parseInt(map.get("icon")));
        //holder.imageView.setImageResource(R.drawable.);

        //The portion below adds the card expand functionality
        final boolean isExpanded = position==mExpandedPosition;
        holder.constraintLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;

                /*ChangeBounds transition = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    transition = new ChangeBounds();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    transition.setDuration(125);
                }*/

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(rootConst);
                }

                //This removes the blink animation
                ((SimpleItemAnimator) passedRView.getItemAnimator()).setSupportsChangeAnimations(false);

                //notifyDataSetChanged();
                //notifyItemChanged(position);

                notifyItemRangeChanged(0, values.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }
}
