package com.teamducati.cloneappcfh.screen.news.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.NewsPromotion;
import com.teamducati.cloneappcfh.screen.news.newsdetails.NewsWebViewDetailsDialogFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class NewsPromotionListAdapter extends RecyclerView.Adapter<NewsPromotionListAdapter.NewsPromotionViewHolder> {

    private Context context;
    private int position;
    private LayoutInflater mInflater;
    private List<NewsPromotion> mNewsPromotions;

    public NewsPromotionListAdapter(Context context, List<NewsPromotion> mNewsPromotions) {
        this.context = context;
        this.mNewsPromotions = mNewsPromotions;
        mInflater = LayoutInflater.from(context);
    }

    public void setPosition(int position) {
        this.position = position;
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

        public void setItemClickListener(ItemClickListener itemClickListener) {
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

    @Override
    public NewsPromotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_item_row_news_promotion, parent, false);
        return new NewsPromotionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsPromotionViewHolder holder, int position) {
        final NewsPromotion newsPromotionObj = mNewsPromotions.get(position);
        holder.txtItemTilteNewsPromotion.setText(newsPromotionObj.getTitle());
        Glide.with(context)
                .load(newsPromotionObj.getImage())
                .placeholder(R.drawable.common_full_open_on_phone)
                .into(holder.imgItemImageNewsPromotion);


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    setPosition(position);
                    Toast.makeText(context, "Long Click" + position, Toast.LENGTH_SHORT).show();

                } else {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    NewsWebViewDetailsDialogFragment dialogFragmentDetails =
                            new NewsWebViewDetailsDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("url", mNewsPromotions.get(position).getUrl());
                    bundle.putString("title", mNewsPromotions.get(position).getTitle());
                    dialogFragmentDetails.setArguments(bundle);
                    dialogFragmentDetails.show(activity.getSupportFragmentManager(), null);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mNewsPromotions != null)
            return mNewsPromotions.size();
        else return 0;
    }


}


