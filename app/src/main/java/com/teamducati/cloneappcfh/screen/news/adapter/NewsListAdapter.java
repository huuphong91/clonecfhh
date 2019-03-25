package com.teamducati.cloneappcfh.screen.news.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.News;
import com.teamducati.cloneappcfh.screen.news.newsdetails.NewsWebViewDetailsDialogFragment;
import com.teamducati.cloneappcfh.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    private List<News> mNewss;

    @Inject
    public NewsListAdapter() {
    }

    public void setList(List<News> list) {
        mNewss = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_row_news, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        final News NewsObj = mNewss.get(position);

        holder.txtItemTilteNews.setText(NewsObj.getTitle());
        holder.txtItemContentNews.setText(NewsObj.getContent());

        Glide.with(holder.itemView.getContext())
                .load(NewsObj.getImage())
                .placeholder(R.drawable.common_full_open_on_phone)
                .into(holder.imgItemImageNews);

        holder.setItemClickListener((view, position1, isLongClick) -> {

            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            NewsWebViewDetailsDialogFragment newsWebViewDetailsDialogFragment =
                    new NewsWebViewDetailsDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_BUNDLE_WEB_VIEW_URL, mNewss.get(position1).getUrl());
            bundle.putString(Constants.KEY_BUNDLE_WEB_VIEW_TITLE, mNewss.get(position1).getTitle());
            newsWebViewDetailsDialogFragment.setArguments(bundle);
            newsWebViewDetailsDialogFragment.show(activity.getSupportFragmentManager(), null);
        });
    }

    @Override
    public int getItemCount() {
        if (mNewss != null)
            return mNewss.size();
        else return 0;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        private final ImageView imgItemImageNews;
        private final TextView txtItemTilteNews;
        private final TextView txtItemContentNews;
        private ItemClickListener itemClickListener;

        private NewsViewHolder(View itemView) {
            super(itemView);

            imgItemImageNews = itemView.findViewById(R.id.img_news);
            txtItemTilteNews = itemView.findViewById(R.id.txt_tilte_news);
            txtItemContentNews = itemView.findViewById(R.id.txt_content_news);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {

            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {

            itemClickListener.onClick(v, getAdapterPosition(), true);

            return true;
        }
    }
}


