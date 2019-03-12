package com.example.thecoffeehouse.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thecoffeehouse.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private Context mContext;
    private List<News> albumList;
    public boolean vertical=true;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, title_for_news;
        public ImageView thumbnail, thumbnail_news;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_for_you);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail_for_you);
//            thumbnail_news = (ImageView) view.findViewById(R.id.thumbnail_view_news);
//            title_for_news = (TextView) view.findViewById(R.id.title_news);
        }
    }



    public NewsAdapter(Context mContext, List < News > albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }
        @Override
        public MyViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_for_you, parent, false);
        return new MyViewHolder(itemView);

    }


        @Override
        public void onBindViewHolder ( final MyViewHolder holder, int position) {
            News album = albumList.get(position);
                holder.title.setText(album.getName());
                Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

//                holder.title_for_news.setText((album.getName()));
//                // loading album cover using Glide library
//
//                Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail_news);


        }


        private void showPopupMenu (View view){
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_news, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

        /**
         * Click listener for popup menu items
         */
        class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

            public MyMenuItemClickListener() {
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_open_news:
                        Toast.makeText(mContext, "Open News", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                }
                return false;
            }
        }

        @Override
        public int getItemCount () {
        return albumList.size();
    }
}
