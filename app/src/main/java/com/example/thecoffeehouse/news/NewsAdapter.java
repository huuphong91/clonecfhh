package com.example.thecoffeehouse.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.entity.ResponseForYou;
import com.example.thecoffeehouse.data.model.entity.ResponseNews;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>{
    private Context mContext;

    private List<ResponseForYou> mListNews;



    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView title_for_you,title_bold_you, title_for_news;
        public ImageView thumbnail, thumbnail_news;
        public MyViewHolder(View view) {
            super(view);
            title_for_you = (TextView) view.findViewById(R.id.title_for_you);
            title_bold_you=(TextView) view.findViewById(R.id.title_bold_for_you);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail_for_you);
        }
    }

    public NewsAdapter(Context mContext, List < ResponseForYou > mListNews) {
        this.mContext = mContext;
        this.mListNews =mListNews ;
    }
        @Override
        public MyViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_for_you, parent, false);
        return new MyViewHolder(itemView);

    }

        @Override
        public void onBindViewHolder (final MyViewHolder holder, int position) {
            final ResponseForYou album = mListNews.get(position);
            holder.title_bold_you.setText(album.getTitle());
            holder.title_for_you.setText(album.getContent());
            Glide.with(mContext).load(album.getImage()).into(holder.thumbnail);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        @Override
        public int getItemCount () {
        return mListNews.size();
    }
     public void setValues(List<ResponseForYou> values) {
        mListNews = values;
        notifyDataSetChanged ();
    }
}
