package com.example.thecoffeehouse.store.views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.bumptech.glide.request.transition.TransitionFactory;
import com.bumptech.glide.request.transition.ViewAnimationFactory;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.store.Store;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

public class StoreDetailDialogFragment extends DialogFragment {

    public static final String TAG = "StoreDetailDialogFragment";
    public static final String KEY_BUNDLE_STORE = "store";

    private Store mStore;
    private ViewPager vpImageSlider;
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private TextView tvStoreFullAdress;
    private TextView tvAccessTime;
    private TextView tvContactPhone;
    private TextView toolbarTitle;
    private TextView btnShowDirection;

    public static StoreDetailDialogFragment getInstance(Store store) {
        StoreDetailDialogFragment storeDetailDialogFragment = new StoreDetailDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BUNDLE_STORE, store);
        storeDetailDialogFragment.setArguments(bundle);
        return storeDetailDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_detail, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
        mStore = (Store) getArguments().getSerializable(KEY_BUNDLE_STORE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogFragment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initStoreInfomation(view);
        initSlider(view);
        initMapView(view, savedInstanceState);

        view.findViewById(R.id.frag_login_img_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        getEnterTransition();
    }

    private void initSlider(View view) {
        vpImageSlider = view.findViewById(R.id.vpStoreImageSlider);
        ImageSliderAdapter adapter = new ImageSliderAdapter(getContext());
        adapter.setListImage(mStore.storeImages);
        vpImageSlider.setAdapter(adapter);
        vpImageSlider.setCurrentItem(0);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    private void initStoreInfomation(View view) {
        toolbarTitle = view.findViewById(R.id.tvToolbarTitle);
        toolbarTitle.setText(mStore.storeName);
        tvStoreFullAdress = view.findViewById(R.id.tvStoreFullAddress);
        tvStoreFullAdress.setText(mStore.storeAddress.full_address);
        tvContactPhone = view.findViewById(R.id.tvContactPhone);
        tvContactPhone.setText(mStore.storePhone);
        tvAccessTime = view.findViewById(R.id.tvAccessTime);
        tvAccessTime.setText(mStore.storeOpenTime + " - " + mStore.storeCloseTime);

        btnShowDirection = view.findViewById(R.id.btnShowDirection);
        btnShowDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringUri = "google.navigation:q=" + mStore.storeLat + "," + mStore.storeLong;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringUri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
    }

    private void initMapView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mMapView = view.findViewById(R.id.mapViewMini);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(googleMap -> {
            mMapView.setClickable(false);
            googleMap.getUiSettings().setMapToolbarEnabled(false);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            googleMap.getUiSettings().setZoomControlsEnabled(false);
            googleMap.getUiSettings().setZoomGesturesEnabled(false);
            googleMap.getUiSettings().setScrollGesturesEnabled(false);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(mStore.storeLat, mStore.storeLong));
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_map));
            googleMap.addMarker(markerOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mStore.storeLat, mStore.storeLong), 14f));
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
