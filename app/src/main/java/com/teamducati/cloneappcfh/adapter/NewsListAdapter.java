package com.teamducati.cloneappcfh.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.News;
import com.teamducati.cloneappcfh.screen.news.NewsWebViewActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    Context context;
    public int position;
    private final LayoutInflater mInflater;
    private List<News> mNewss;

    public NewsListAdapter(Context context, List<News> mNewss) {
        this.context = context;
        this.mNewss = mNewss;
        mInflater = LayoutInflater.from(context);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        private final ImageView imgItemImageNews;
        private final TextView txtItemTilteNews;
        private ItemClickListener itemClickListener;

        private NewsViewHolder(View itemView) {
            super(itemView);
            imgItemImageNews = itemView.findViewById(R.id.img_news);
            txtItemTilteNews = itemView.findViewById(R.id.txt_tilte_news);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
            String url_news = mNewss.get(getAdapterPosition()).getUrl();
            Intent intent = new Intent(context, NewsWebViewActivity.class);
            intent.putExtra("url_news", url_news);
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);

            return true;
        }

    }


    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_item_row_news, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        final News NewsObj = mNewss.get(position);
        holder.txtItemTilteNews.setText(NewsObj.getTitle());
        Glide.with(context)
                .load(NewsObj.getImage())
                .placeholder(R.drawable.common_full_open_on_phone)
                .into(holder.imgItemImageNews);


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    setPosition(position);
                    Toast.makeText(context, "Long Click" + position, Toast.LENGTH_SHORT).show();

                } else {
                    //onClick

                }
            }
        });
    }

    public void setDevices(ArrayList<News> Newss) {
        mNewss = Newss;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mNewss != null)
            return mNewss.size();
        else return 0;
    }


}


