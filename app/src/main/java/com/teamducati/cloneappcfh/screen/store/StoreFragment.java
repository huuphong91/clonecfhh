package com.teamducati.cloneappcfh.screen.store;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.adapter.StoreAdapter;
import com.teamducati.cloneappcfh.entity.APIStoreMap.StoresItem;

import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment implements StoreContract.View {

    private StoreContract.Presenter mPresenter;
    private SupportMapFragment mapFragment;
    private List<StoresItem> mApiStores;
    private StoreAdapter mAdapterStore;
    @BindView(R.id.mRecyclerStrore)
    RecyclerView mRecyclerStrore;

    public StoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_store, container, false);
        ButterKnife.bind(this, rootView);
        ActivityCompat.requestPermissions(getActivity(),new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},123);

        init();
        mPresenter.onGetAllStore();
        return rootView;
    }

    private void init() {

        // mRecyclerStrore.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerStrore.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void setPresenter(StoreContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void addMarker() {

        mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map_container);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                } else {
                    // Show rationale and request permission.
                }
                mMap.clear(); //clear old markers

                GPSLocation gpsLocation=  new GPSLocation(getActivity());
                Location l = gpsLocation.getLocation();
                if(l!= null){
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    CameraPosition googlePlex = CameraPosition.builder()
                            .target(new LatLng(lat, lon))
                            .zoom(10)
                            .bearing(0)
                            .tilt(45)
                            .build();

                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex),
                            10000, null);

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lon))
                            .title("Gan nha")
                            .icon(mPresenter.bitmapDescriptorFromVector(getActivity(),
                                    R.drawable.icon_coffee)));

                }
                for (int i = 0; i < 10; i++) {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(mApiStores.get(i).getLatitude())
                                    , Double.parseDouble(mApiStores.get(i).getLongitude())))
                            .title(mApiStores.get(i).getName())
                            .snippet(mApiStores.get(i).getAddress().getStreet()));
                }
            }
        });
    }

    @Override
    public void showListStore(List<StoresItem> arrayList) {
        this.mApiStores = arrayList;
        mAdapterStore = new StoreAdapter(getActivity(), arrayList);
        mRecyclerStrore.setAdapter(mAdapterStore);
        mPresenter.onAddMarker();
    }
}
