package com.example.thecoffeehouse.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.entity.ResponseForYou;
import com.example.thecoffeehouse.news.webview.WebViewForYou;

import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapterForYou extends RecyclerView.Adapter<NewsAdapterForYou.MyViewHolder>{
    private Context mContext;
    private List<ResponseForYou> mListNews;
    private FragmentManager manager;


    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView title_for_you,title_bold_you, title_for_news;
        public ImageView thumbnail, thumbnail_news;
        public MyViewHolder(View view) {
            super(view);
            title_bold_you=(TextView) view.findViewById(R.id.title_bold_for_you);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail_for_you);

        }
    }
    public NewsAdapterForYou(Context mContext, List<ResponseForYou> mListNews, FragmentManager manager) {
        this.mContext = mContext;
        this.mListNews =mListNews ;
        this.manager = manager;
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
            Glide.with(mContext).load(album.getImage()).placeholder(R.mipmap.store_placeholder).into(holder.thumbnail);
            holder.thumbnail.setOnClickListener(v -> {
                try {
                    WebViewForYou.newInstance(album).show(manager, "NewsForyou");
                }catch (Exception e)
                {

                }
            });
            holder.itemView.setOnClickListener(view -> {
                try {
                    WebViewForYou.newInstance(album).show(manager, "NewsForyou");
                }catch (Exception e)
                {

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
