package com.teamducati.cloneappcfh.screen.store;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
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
import java.util.List;
import androidx.appcompat.widget.Toolbar;

public class DialogStoreDetail {

    public void showDialog(Context context, String name, List<String> listImage, Double lat, Double lon, String address,
                           String time, String numberPhone) {
        Dialog dialog = new Dialog(context, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_detail_store);

        Toolbar exit = (Toolbar) dialog.findViewById(R.id.tbrExitStroreDetail);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ViewFlipper mStroreDetail = dialog.findViewById(R.id.imgStroreDetail);
        for (int i = 0; i < listImage.size(); i++) {
            ImageView imageView = new ImageView(context);
            Glide.with(context)
                    .load(listImage.get(i))
                    .placeholder(R.drawable.common_full_open_on_phone)
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mStroreDetail.addView(imageView);
        }
        mStroreDetail.setFlipInterval(5000);
        mStroreDetail.setAutoStart(true);
        Animation animation_in_right = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        Animation animation_out_right = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
        mStroreDetail.setInAnimation(animation_in_right);
        mStroreDetail.setOutAnimation(animation_out_right);

        TextView mNameStoreMap = dialog.findViewById(R.id.txtNameStroreDetail);
        mNameStoreMap.setText(name);

        MapView mMapView = (MapView) dialog.findViewById(R.id.mpvStroreDetail);
        MapsInitializer.initialize(context);
        mMapView.onCreate(dialog.onSaveInstanceState());
        mMapView.onResume();

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(lat, lon))
                        .zoom(15)
                        .bearing(0)
                        .tilt(45)
                        .build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 1000, null);
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title(name)
                        .snippet(numberPhone));
            }
        });

        TextView mAddressStroreDetail = dialog.findViewById(R.id.txtAddressStroreDetail);
        mAddressStroreDetail.setText(address);

        TextView mTimeOC = dialog.findViewById(R.id.txtOpeningClosingTimeStroreDetail);
        mTimeOC.setText(time);

        TextView mNumberPhome = dialog.findViewById(R.id.txtNumberPhoneStroreDetail);
        mNumberPhome.setText(numberPhone);
        dialog.show();
    }
}
