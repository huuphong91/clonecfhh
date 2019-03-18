package com.teamducati.cloneappcfh.screen.order;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.adapter.RepickShipAddressAdapter;
import com.teamducati.cloneappcfh.screen.main.FetchAddressTask;
import com.teamducati.cloneappcfh.screen.main.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RepickShipAddressDialog extends DialogFragment implements RepickShipAddressAdapter.OnClickListener {

    private OnClickItem mListener;

    private EditText edtRepickShipLocation;
    private RecyclerView rvRepickShipAddress;

    private RepickShipAddressAdapter repickShipAddressAdapter;

    private String mAddress = null;

    private PlacesClient placesClient;
    private FindAutocompletePredictionsRequest.Builder requestBuilder;

    private List<String> addressList = new ArrayList<>();

    static RepickShipAddressDialog newInstance() {
        return new RepickShipAddressDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.RepickShipAddressFullScreen);

        placesClient = Places.createClient(Objects.requireNonNull(getActivity()));
        requestBuilder = FindAutocompletePredictionsRequest.builder()
                .setCountry("vn");

        ((MainActivity) Objects.requireNonNull(getActivity())).getLocation();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_repick_ship_location, container, false);
        edtRepickShipLocation = view.findViewById(R.id.edtRepickShipLocation);
        rvRepickShipAddress = view.findViewById(R.id.rvRepickShipAddress);
        rvRepickShipAddress.setLayoutManager(new LinearLayoutManager(getActivity()));
        repickShipAddressAdapter = new RepickShipAddressAdapter(this);
        rvRepickShipAddress.setHasFixedSize(true);
        rvRepickShipAddress.setAdapter(repickShipAddressAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mAddress != null) {
            repickShipAddressAdapter.displayPositionShipTitle(mAddress);
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
                    Task<FindAutocompletePredictionsResponse> task =
                            placesClient.findAutocompletePredictions(requestBuilder.build());
                    task.addOnSuccessListener(response -> {
                        for (AutocompletePrediction autocompletePrediction :
                                response.getAutocompletePredictions()) {
                            addressList.add(String.valueOf(autocompletePrediction.getFullText(null)));
                        }
                        repickShipAddressAdapter.displayShipAddressResults(addressList);
                        addressList.clear();
                    });
                } else {
                    repickShipAddressAdapter.displayPositionShipTitle(mAddress);
                }
            }
        });
    }

    public void setLocation(String address) {
        mAddress = address;
    }

    @Override
    public void onCurrentLocationClick() {
        ((MainActivity) Objects.requireNonNull(getActivity())).getLocation();
        dismiss();
    }

    @Override
    public void onRecentLocationCLick() {
        dismiss();
    }

    @Override
    public void onPickLocationClick(String address) {
        String addressPart[] = address.split(",");
        mListener.onClickItem(addressPart[0]);
        dismiss();
    }

    public interface OnClickItem {
        void onClickItem(String address);
    }

    public void setOnClickItem(OnClickItem listener) {
        mListener = listener;
    }

}
