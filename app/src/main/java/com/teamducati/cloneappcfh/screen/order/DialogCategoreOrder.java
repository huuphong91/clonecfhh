package com.teamducati.cloneappcfh.screen.order;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.screen.order.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DialogCategoreOrder {
    public void showDialog(Context context) {

        List<String> mListCategory = new ArrayList<>();
        mListCategory.add("Món nổi bật");
        mListCategory.add("Yêu thích");
        mListCategory.add("Món gân đây");
        mListCategory.add("Cà Phê");
        mListCategory.add("Thức uống đá say");
        mListCategory.add("Trà trái cây");
        mListCategory.add("Thức uống trái cây");
        mListCategory.add("Macchiato");
        mListCategory.add("Khác");
        mListCategory.add("Bánh ngọt");
        mListCategory.add("Bánh mặn");

        CategoryAdapter adapter = new CategoryAdapter(context,mListCategory);

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_sort_order);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams windowLayout = dialog.getWindow()
                .getAttributes();
        windowLayout.gravity = Gravity.BOTTOM | Gravity.LEFT;
        dialog.getWindow().getAttributes().verticalMargin = 0.18F;
        dialog.getWindow().getAttributes().horizontalMargin = 0.04F;
        dialog.getWindow().setAttributes(windowLayout);
        RecyclerView mRcvListCategory = dialog.findViewById(R.id.rcvListCategory);
        mRcvListCategory.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        mRcvListCategory.setLayoutManager(linearLayout);
        mRcvListCategory.setAdapter(adapter);

        dialog.show();
    }
}
