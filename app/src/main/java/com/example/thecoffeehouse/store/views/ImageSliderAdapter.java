package com.example.thecoffeehouse.store.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.thecoffeehouse.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ImageSliderAdapter extends PagerAdapter {

    private List<String> mListImage;
    private Context mContext;

    public ImageSliderAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mListImage == null) return 0;
        return mListImage.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image_slider, container, false);
        ImageView imageView = view.findViewById(R.id.imgStoreImage);
        Glide.with(mContext).load(mListImage.get(position)).placeholder(R.mipmap.store_placeholder).into(imageView);
        container.addView(view);
        return view;
    }


    public void setListImage(List<String> mListImage) {
        this.mListImage = mListImage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

}
