package com.teamducati.cloneappcfh.screen.store;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.APIStoreMap.StoresItem;
import com.teamducati.cloneappcfh.screen.store.adapter.StoreAdapter;

import java.util.ArrayList;
import java.util.List;
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
    private GoogleMap mMap;

    @BindView(R.id.spnStoreCity)
    Spinner mListStore;

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
        init();
        initMap();
        mPresenter.onGetAllStore();
        return rootView;
    }

    private void initStoreCity() {
        List<String> mStore = new ArrayList<>();
        for (int i = 0; i < mApiStores.size(); i++) {
            if (mApiStores.get(i).getAddress().getWard() != null) {
                mStore.add(mApiStores.get(i).getAddress().getWard());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item, mStore);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mListStore.setAdapter(adapter);
        mListStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // mMap.clear(); //clear old markers
                CameraPosition googlePlex = CameraPosition.builder()

                        .target(new LatLng(Double.parseDouble(mApiStores.get(i).getLatitude()),
                                Double.parseDouble(mApiStores.get(i).getLongitude())))
                        .zoom(15)
                        .bearing(0)
                        .tilt(45)
                        .build();
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 1000, null);
//                ArrayList<StoresItem> storesPosition = new ArrayList<>();
//                if (storesPosition != null) {
//                    storesPosition.add(new StoresItem(mApiStores.get(i)));
//                    mAdapterStore = new StoreAdapter(getActivity(), storesPosition);
//                    mRecyclerStrore.setAdapter(mAdapterStore);
//                    mAdapterStore.notifyDataSetChanged();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void init() {
        // mRecyclerStrore.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerStrore.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void setPresenter(StoreContract.Presenter presenter) {
        mPresenter = presenter;
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
        initStoreCity();
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
}
