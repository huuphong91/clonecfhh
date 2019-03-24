package com.example.thecoffeehouse.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private final LayoutInflater mInflater;
    private List<Notification> notificationList; // Cached copy of words
    private Context context;

    public NotificationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        notificationList = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_notification_recyclerview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification current = notificationList.get(position);
        Glide.with(context).load(current.getImageNotification()).into(holder.mImageNotification);
        holder.mTextViewTitle.setText(current.getTitle());
        holder.mTextViewMessege.setText(current.getMessege());

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mImageNotification;
        TextView mTextViewTitle;
        TextView mTextViewMessege;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageNotification = itemView.findViewById(R.id.imgNotification);
            mTextViewTitle = itemView.findViewById(R.id.tvTitle);
            mTextViewMessege = itemView.findViewById(R.id.tvMessege);
        }
    }

    void setNotification(List<Notification> list) {
        notificationList.clear();
        notificationList.addAll(list);
        notifyDataSetChanged();
    }
}
