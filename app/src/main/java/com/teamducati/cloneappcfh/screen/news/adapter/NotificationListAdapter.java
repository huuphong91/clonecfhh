package com.teamducati.cloneappcfh.screen.news.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.Notification;
import com.teamducati.cloneappcfh.screen.news.notificationsdetails.NotificationDetailsDialogFragment;
import com.teamducati.cloneappcfh.utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<Notification> mNotification;

    public NotificationListAdapter(Context context, List<Notification> mNotification) {
        this.context = context;
        this.mNotification = mNotification;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_item_row_notification, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        final Notification NotificationObj = mNotification.get(position);
        holder.txtItemTilteNotification.setText(NotificationObj.getTilte().trim());
        holder.txtItemContentNotification.setText(NotificationObj.getContent().trim());
        holder.txtItemDateNotification.setText(NotificationObj.getDate());
        holder.setItemClickListener((view, position1, isLongClick) -> {
            if (isLongClick) {
                Toast.makeText(context, "Long Click" + position1, Toast.LENGTH_SHORT).show();

            } else {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                NotificationDetailsDialogFragment newsNotificationDialogFragment =
                        new NotificationDetailsDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.KEY_BUNDLE_FIREBASE_TITLE, NotificationObj.getTilte());
                bundle.putString(Constants.KEY_BUNDLE_FIREBASE_CONTENT, NotificationObj.getContent());
                bundle.putString(Constants.KEY_BUNDLE_FIREBASE_IMAGE_URL, NotificationObj.getUrl());
                newsNotificationDialogFragment.setArguments(bundle);
                newsNotificationDialogFragment.show(activity.getSupportFragmentManager(), null);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mNotification != null)
            return mNotification.size();
        else return 0;
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
            itemView.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);
        }

        void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }


        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);

            return true;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }


}


