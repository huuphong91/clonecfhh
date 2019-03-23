package com.teamducati.cloneappcfh.screen.news.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.NewsPromotion;
import com.teamducati.cloneappcfh.screen.news.newsdetails.NewsWebViewDetailsDialogFragment;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class NewsPromotionListAdapter extends RecyclerView.Adapter<NewsPromotionListAdapter.NewsPromotionViewHolder> {

    private List<NewsPromotion> mNewsPromotions;

    @Inject
    public NewsPromotionListAdapter() {
    }

    public void setList(List<NewsPromotion> list) {
        mNewsPromotions = list;
        notifyDataSetChanged();
    }

    class NewsPromotionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        private ImageView imgItemImageNewsPromotion;
        private TextView txtItemTilteNewsPromotion;
        private ItemClickListener itemClickListener;

        private NewsPromotionViewHolder(View itemView) {
            super(itemView);
            imgItemImageNewsPromotion = itemView.findViewById(R.id.img_news_promotion);
            txtItemTilteNewsPromotion = itemView.findViewById(R.id.txt_tilte_news_promotion);
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

    @NonNull
    @Override
    public NewsPromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_row_news_promotion, parent, false);
        return new NewsPromotionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsPromotionViewHolder holder, int position) {
        final NewsPromotion newsPromotionObj = mNewsPromotions.get(position);
        holder.txtItemTilteNewsPromotion.setText(newsPromotionObj.getTitle());
        Glide.with(holder.itemView.getContext())
                .load(newsPromotionObj.getImage())
                .placeholder(R.drawable.common_full_open_on_phone)
                .into(holder.imgItemImageNewsPromotion);


        holder.setItemClickListener((view, position1, isLongClick) -> {

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                NewsWebViewDetailsDialogFragment dialogFragmentDetails =
                        new NewsWebViewDetailsDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", mNewsPromotions.get(position1).getUrl());
                bundle.putString("title", mNewsPromotions.get(position1).getTitle());
                dialogFragmentDetails.setArguments(bundle);
                dialogFragmentDetails.show(activity.getSupportFragmentManager(), null);
        });
    }

    @Override
    public int getItemCount() {
        if (mNewsPromotions != null)
            return mNewsPromotions.size();
        else return 0;
    }
}


