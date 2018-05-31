package com.zeus.ali.matchday.News;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;
import com.zeus.ali.matchday.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ali on 1/12/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HashMap<String,String>> values;

    /*public FixtureAdaptor(Context context) {
        this.context = context;
    }*/


    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsAdapter(ArrayList<HashMap<String,String>> myDataset) {
        values = myDataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView galleryImage;
        public TextView author;
        public TextView mtitle;
        public TextView sdetails;
        public TextView mtime;
        public View layout;
        CardView cardViewNews;

        public ViewHolder(View v) {
            super(v);
            layout      = v;
            galleryImage    = v.findViewById(R.id.galleryImage);
            //author       = v.findViewById(R.id.author);
            mtitle       = v.findViewById(R.id.title);
            sdetails       = v.findViewById(R.id.sdetails);
            mtime   = v.findViewById(R.id.time);
            cardViewNews = v.findViewById(R.id.cardViewNews);
        }
    }


    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View view =
                inflater.inflate(R.layout.news_list_items, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.cardViewNews);
        HashMap<String,String> map = values.get(position);
        //holder.author.setText(map.get(NewsTab.KEY_AUTHOR));
        holder.mtitle.setText(map.get(NewsTab.KEY_TITLE));
        holder.sdetails.setText(map.get(NewsTab.KEY_DESCRIPTION));
        holder.mtime.setText(map.get(NewsTab.KEY_PUBLISHEDAT).substring(0,10));
        //holder.galleryImage.setImageResource(Integer.parseInt(map.get(NewsTab.KEY_URLTOIMAGE)));
        if(map.get(NewsTab.KEY_URLTOIMAGE).length() < 5)
        {
            holder.galleryImage.setVisibility(View.GONE);
        }else{
            Picasso.with(holder.galleryImage.getContext())
                    .load(map.get(NewsTab.KEY_URLTOIMAGE))
                    //.resize(400, 300)
                    .into(holder.galleryImage);
        }

    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}