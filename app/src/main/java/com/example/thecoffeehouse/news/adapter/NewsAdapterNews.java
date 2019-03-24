package com.example.thecoffeehouse.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thecoffeehouse.data.model.entity.ResponseNews;
import com.bumptech.glide.Glide;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.news.webview.WebViewNews;

import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapterNews extends RecyclerView.Adapter<NewsAdapterNews.MyViewHolder> {
    private Context mContext;
    private List<ResponseNews> mNewsList;
    private FragmentManager manager;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title_for_news, title_bold_for_news;
        private ImageView thumbnail_news;

        public MyViewHolder(View view) {
            super(view);
            title_bold_for_news = (TextView) view.findViewById(R.id.title_bold_news);
            thumbnail_news = (ImageView) view.findViewById(R.id.thumbnail_for_news);
            title_for_news = (TextView) view.findViewById(R.id.title_for_news);
        }
    }

    public NewsAdapterNews(Context mContext, List<ResponseNews> mNewList, FragmentManager manager) {
        this.mContext = mContext;
        this.mNewsList = mNewList;
        this.manager = manager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_for_news, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ResponseNews album = mNewsList.get(position);
        holder.title_for_news.setText((album.getContent()));
        holder.title_bold_for_news.setText(album.getTitle());
        Glide.with(mContext).load(album.getImage()).placeholder(R.mipmap.store_placeholder).into(holder.thumbnail_news);
        holder.thumbnail_news.setOnClickListener(v -> {
            try {
                WebViewNews.newInstance(album).show(manager, "NewsDetail");
            } catch (Exception e) {

            }
        });
        holder.itemView.setOnClickListener(v -> {
            try {
                WebViewNews.newInstance(album).show(manager, "NewsDetail");
            } catch (Exception e) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public void setValues(List<ResponseNews> values) {
        mNewsList = values;
        notifyDataSetChanged();
    }
}
