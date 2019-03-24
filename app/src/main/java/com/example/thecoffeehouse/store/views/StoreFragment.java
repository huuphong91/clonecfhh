package com.example.thecoffeehouse.store.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.store.Store;
import com.example.thecoffeehouse.data.model.store.StoreResponeObject;
import com.example.thecoffeehouse.store.presenters.StorePresenter;
import com.example.thecoffeehouse.store.presenters.StorePresenterIpm;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class StoreFragment extends Fragment implements OnMapReadyCallback, StoreView, GoogleMap.OnMyLocationChangeListener {

    private final String TAG = getClass().getName();

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private List<Store> listStore;
    private Location mCurentLocation;
    private List<Store> listStoreNearBy;
    private List<StoreResponeObject.State> mListState;
    private RecyclerView rvListNearByStore;
    private RecyclerView rvListState;
    private StoreHorizontalAdapter adapterStoreNearBy;
    private DistrictVerticalAdapter districtAdapter;
    private FloatingActionButton btnShowMyLocation;
    private StorePresenter presenter;
    TextView tvShowDistric;

    public static StoreFragment newInstance() {

        return new StoreFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_store, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        listStoreNearBy = new ArrayList<>();
        listStore = new ArrayList<>();
        mListState = new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        presenter = new StorePresenterIpm(this);
        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        btnShowMyLocation = view.findViewById(R.id.fabShowMyLocation);
        btnShowMyLocation.setOnClickListener(v -> {
            mCurentLocation = null;
            if (mGoogleMap.isMyLocationEnabled()) {
                Location myLocation = mGoogleMap.getMyLocation();
                if (myLocation == null) return;
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 13f));
            }
        });
        initRecyclerView(view);
        initDistricList(view);
    }

    private void initDistricList(View view) {
        tvShowDistric = view.findViewById(R.id.tvListDistric);
        tvShowDistric.setTag(0);
        tvShowDistric.setOnClickListener(v -> {
            switch ((int) v.getTag()) {
                case 0:
                    rvListState.setVisibility(View.VISIBLE);
                    rvListNearByStore.setVisibility(View.GONE);
                    tvShowDistric.setTag(1);
                    break;
                case 1:
                    rvListState.setVisibility(View.GONE);
                    rvListNearByStore.setVisibility(View.VISIBLE);
                    tvShowDistric.setTag(0);
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        LatLng centerOfVietnam = new LatLng(16.681521, 107.159505);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerOfVietnam, 6));
        if (checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 696);
        } else {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setOnMyLocationChangeListener(this);
        }
        //mGoogleMap.setInfoWindowAdapter(new MapInfoWindow(getContext()));
        presenter.loadListStore();
        presenter.loadListStoreFromDatabase();
    }

    private void initRecyclerView(View view) {
        adapterStoreNearBy = new StoreHorizontalAdapter(getContext(), listStoreNearBy);
        rvListNearByStore = view.findViewById(R.id.rvListStoreNearBy);
        rvListNearByStore.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvListNearByStore.setAdapter(adapterStoreNearBy);
        rvListNearByStore.setVisibility(View.VISIBLE);
        adapterStoreNearBy.setItemClickListener((view1, position) -> {
            Store store = listStoreNearBy.get(position);
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(store.storeLat, store.storeLong), 13f));
            StoreDetailDialogFragment.getInstance(store).show(getFragmentManager(), StoreDetailDialogFragment.TAG);
        });
        districtAdapter = new DistrictVerticalAdapter(mListState, getContext());
        rvListState = view.findViewById(R.id.rvListDistric);
        rvListState.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rvListState.setHasFixedSize(true);
        rvListState.setAdapter(districtAdapter);
        districtAdapter.setOnItemClickListener((v, item) -> {
            tvShowDistric.performClick();
            if (item instanceof StoreResponeObject.State) {
                StoreResponeObject.State state = (StoreResponeObject.State) item;
                listStoreNearBy.clear();
                listStoreNearBy.addAll(state.districts.get(0).stores);
                adapterStoreNearBy.notifyDataSetChanged();
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(state.districts.get(0).stores.get(0).storeLat, state.districts.get(0).stores.get(0).storeLong), 13f));
            } else if (item instanceof StoreResponeObject.District) {
                StoreResponeObject.District district = (StoreResponeObject.District) item;
                listStoreNearBy.clear();
                listStoreNearBy.addAll(district.stores);
                adapterStoreNearBy.notifyDataSetChanged();
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(district.stores.get(0).storeLat, district.stores.get(0).storeLong), 13f));
            }
        });
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        mMapView.onResume();
        if (mCurentLocation != null & mGoogleMap != null) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurentLocation.getLatitude(), mCurentLocation.getLongitude()), 13f));
        }
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
        presenter.onStop();
        mMapView.onStop();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        mMapView.onDestroy();
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onLowMemory() {
        Log.d(TAG, "onLowMemory: ");
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onStoreLoaded(StoreResponeObject responeObject) {
        districtAdapter.setMlistState(responeObject.listState);
    }

    @Override
    public void onStoreLoaded(List<Store> listStoreStore) {
        Log.d(TAG, "onStoreLoaded: listStoreStore.size() = " + listStoreStore.size());
        for (Store store : listStoreStore
        ) {
            listStore.add(store);
            Log.d(this.getClass().getName(), store.storeName);
            LatLng mLatLng = new LatLng(store.storeLat, store.storeLong);
            MarkerOptions markerOptions = new MarkerOptions().position(mLatLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_map));
            markerOptions.title(store.storeName);
            markerOptions.snippet(store.storeAddress.full_address);
            mGoogleMap.addMarker(markerOptions);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        Log.e(this.getClass().getName(), throwable.getLocalizedMessage());
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (listStore.isEmpty() || mCurentLocation != null) {
            return;
        }

        listStoreNearBy.clear();
        mCurentLocation = location;
        if (mGoogleMap != null)
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13f));
        Location storeLocation = new Location("Location to compair");
        for (Store store : listStore) {
            storeLocation.setLatitude(store.storeLat);
            storeLocation.setLongitude(store.storeLong);
            store.storeDistance = (int) mCurentLocation.distanceTo(storeLocation);
            if (store.storeDistance < 10000) {
                listStoreNearBy.add(store);
            }
        }
        Collections.sort(listStoreNearBy, Collections.reverseOrder());
        adapterStoreNearBy.notifyDataSetChanged();
        testGeocoder(location);
        mGoogleMap.setOnMyLocationClickListener(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 696) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.setOnMyLocationChangeListener(this);
            }

        }
    }


    private void testGeocoder(Location location) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        Disposable disposable = Single
                .fromCallable(() -> geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1))
                .subscribeOn(Schedulers.single())
                .toObservable()
                .flatMap(Observable::fromIterable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(address -> Log.d(TAG, "testGeocoder: size: " + address.getAddressLine(0)),throwable -> {});
    }


}
