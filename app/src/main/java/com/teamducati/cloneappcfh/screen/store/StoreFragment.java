package com.teamducati.cloneappcfh.screen.store;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.di.ActivityScoped;
import com.teamducati.cloneappcfh.entity.APIStoreMap.StatesItem;
import com.teamducati.cloneappcfh.entity.APIStoreMap.StoresItem;
import com.teamducati.cloneappcfh.screen.store.adapter.ProvinceAdapter;
import com.teamducati.cloneappcfh.screen.store.adapter.StoreAdapter;
import com.teamducati.cloneappcfh.utils.eventsbus.EventBusStore;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
@ActivityScoped
public class StoreFragment extends DaggerFragment implements StoreContract.View {

    @Inject
    StoreContract.Presenter mPresenter;

    private SupportMapFragment mapFragment;
    private List<StoresItem> mApiStores;
    private List<StatesItem> mStatesItems;
    private StoreAdapter mAdapterStore;
    private GoogleMap mMap;
    private ProvinceAdapter mProvinceAdapter;

    //////////
    private int positionStoreSelected;

    @BindView(R.id.lnlChooseStore)
    LinearLayout mChooseStore;

    @BindView(R.id.lnlListStore)
    LinearLayout mListStore;

    @BindView(R.id.txtChooseStore)
    TextView mStore;

    @BindView(R.id.mRecyclerStrore)
    RecyclerView mRecyclerStrore;

    @BindView(R.id.mRcvListStrore)
    RecyclerView mRcvListStrore;

    @BindView(R.id.imgUpDown)
    ImageView mUpDown;

    @Inject
    public StoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_store, container, false);
        ButterKnife.bind(this, rootView);
        init();
        initMap();
        mPresenter.onGetAllStore();
        mPresenter.onGetAllProvince();
        EventBus.getDefault().register(this);
        return rootView;
    }

    private void init() {
        mRecyclerStrore.setHasFixedSize(true);
        mRecyclerStrore.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        mRcvListStrore.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        mRcvListStrore.setLayoutManager(linearLayout);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusStore event) {
        positionStoreSelected = event.position;
        //Toast.makeText(getActivity(), "" + positionStoreSelected, Toast.LENGTH_SHORT).show();
        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(event.lat, event.lon))
                .zoom(15)
                .bearing(0)
                .tilt(45)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 1000, null);
        showListStore(event.storesItems);
        HideShow();
        mStore.setText(event.name);
    }

    private void HideShow() {
        if (mChooseStore.getTag().equals("0")) {
            mChooseStore.setTag("1");
            mUpDown.setImageDrawable(getResources().getDrawable(R.drawable.icon_up));
            mListStore.setVisibility(View.VISIBLE);
            mRecyclerStrore.setVisibility(View.GONE);
        } else {
            mChooseStore.setTag("0");
            mUpDown.setImageDrawable(getResources().getDrawable(R.drawable.icon_down));
            mListStore.setVisibility(View.GONE);
            mRecyclerStrore.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mStore.setOnClickListener(v -> {
            HideShow();
        });
    }

    @Override
    public void showListStore(List<StoresItem> arrayList) {
        this.mApiStores = arrayList;
        mAdapterStore = new StoreAdapter(getActivity(), arrayList);
        mRecyclerStrore.setAdapter(mAdapterStore);
        for (int i = 0; i < mApiStores.size(); i++) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(mApiStores.get(i).getLatitude())
                            , Double.parseDouble(mApiStores.get(i).getLongitude())))
                    .title(mApiStores.get(i).getName())
                    .snippet(mApiStores.get(i).getAddress().getStreet()));
        }
        // initStoreCity();
    }

    @Override
    public void showListProvince(List<StatesItem> arrayList) {
        this.mStatesItems = arrayList;
        mProvinceAdapter = new ProvinceAdapter(getActivity(), arrayList);
        mRcvListStrore.setAdapter(mProvinceAdapter);
    }

    private void initMap() {
        mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.mpvStroreDetail);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
                mMap.clear(); //clear old markers
                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(10.782683, 106.675247))
                        .zoom(7)
                        .bearing(0)
                        .tilt(45)
                        .build();
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 1000, null);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }
}
