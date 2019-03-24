package com.teamducati.cloneappcfh.screen.order.shipaddressrepick;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.libraries.places.api.Places;

import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.screen.order.adapter.RepickShipAddressAdapter;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerDialogFragment;

public class ShipAddressRepick extends DialogFragment implements ShipAddressRepickContract.View {

    @BindView(R.id.edtRepickShipLocation)
    EditText edtRepickShipLocation;
    @BindView(R.id.rvRepickShipAddress)
    RecyclerView rvRepickShipAddress;

    private Unbinder unbinder;

    private ShipAddressRepickContract.Presenter mPresenter;

    private RepickShipAddressAdapter repickShipAddressAdapter;

    private String mAddress = null;

    private PlacesClient placesClient;

    private FindAutocompletePredictionsRequest.Builder requestBuilder;

    private static ShipAddressRepick sInstance;

    public static ShipAddressRepick newInstance() {
        if (sInstance == null) {
            sInstance = new ShipAddressRepick();
        }
        return sInstance;
    }

    private static ShipAddressRepick destroyInstance() {
        if (sInstance != null) {
            sInstance = null;
        }
        return sInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.RepickShipAddressFullScreen);

        mPresenter = new ShipAddressRepickPresenter(this);

        placesClient = Places.createClient(Objects.requireNonNull(getActivity()));

        requestBuilder = FindAutocompletePredictionsRequest.builder()
                .setCountry("vn");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_repick_ship_location, container, false);

        unbinder = ButterKnife.bind(this, view);

        initRecycleView();

        return view;
    }

    private void initRecycleView() {
        rvRepickShipAddress.setLayoutManager(new LinearLayoutManager(getActivity()));
        repickShipAddressAdapter = new RepickShipAddressAdapter();
        rvRepickShipAddress.setHasFixedSize(true);
        rvRepickShipAddress.setAdapter(repickShipAddressAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mAddress != null) {
            mPresenter.loadPositionShipAddressType();
        }

        edtRepickShipLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString().trim();
                if ((!input.equals("")) && (input.length() != 0)) {
                    requestBuilder.setQuery(input);
                    mPresenter.loadShipAddressGooglePlaces(requestBuilder, placesClient);
                } else {
                    mPresenter.loadPositionShipAddressType();
                }
            }
        });
    }

    public void setLocation(String address) {
        mAddress = address;
    }

    @Override
    public void showPositionShipAddressTypes() {
        repickShipAddressAdapter.displayPositionShipTitle(mAddress);
    }

    @Override
    public void showShipAddressResults(List<String> addressList) {
        repickShipAddressAdapter.displayShipAddressResults(addressList);
        addressList.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        destroyInstance();
    }
}
