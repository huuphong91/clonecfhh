package com.teamducati.cloneappcfh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.Notification;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder> {

    private Context context;
    private int position;
    private LayoutInflater mInflater;
    private List<Notification> mNotification;

    public NotificationListAdapter(Context context, List<Notification> mNotification) {
        this.context = context;
        this.mNotification = mNotification;
        mInflater = LayoutInflater.from(context);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        private TextView txtItemTilteNotification;
        private TextView txtItemContentNotification;
        private TextView txtItemDateNotification;
        private ItemClickListener itemClickListener;

        private NotificationViewHolder(View itemView) {
            super(itemView);
            txtItemTilteNotification = itemView.findViewById(R.id.txt_news_item_tilte_notification);
            txtItemContentNotification = itemView.findViewById(R.id.txt_news_item_content_notification);
            txtItemDateNotification = itemView.findViewById(R.id.txt_news_item_date_notification);
            //itemView.setOnClickListener(this);
           // itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);

            return true;
        }

    }


    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_item_row_notification, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        final Notification NotificationObj = mNotification.get(position);
        holder.txtItemTilteNotification.setText(NotificationObj.getTilte().trim());
        holder.txtItemContentNotification.setText(NotificationObj.getContent().trim());
        holder.txtItemDateNotification.setText(NotificationObj.getDate());
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

    @Override
    public int getItemCount() {
        if (mNotification != null)
            return mNotification.size();
        else return 0;
    }


}


