package com.teamducati.cloneappcfh.screen.order;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.adapter.RepickShipAddressAdapter;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RepickShipAddressDialog extends DialogFragment implements GetAddressesTask.OnTaskCompleted {

    private EditText edtRepickShipLocation;
    private RecyclerView rvRepickShipAddress;

    private RepickShipAddressAdapter repickShipAddressAdapter;

    static RepickShipAddressDialog newInstance() {
        return new RepickShipAddressDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.RepickShipAddressFullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_repick_ship_location, container, false);
        edtRepickShipLocation = view.findViewById(R.id.edtRepickShipLocation);
        rvRepickShipAddress = view.findViewById(R.id.rvRepickShipAddress);
        rvRepickShipAddress.setLayoutManager(new LinearLayoutManager(getActivity()));
        repickShipAddressAdapter = new RepickShipAddressAdapter();
        repickShipAddressAdapter.displayPositionShipTitle();
        rvRepickShipAddress.setHasFixedSize(true);
        rvRepickShipAddress.setAdapter(repickShipAddressAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtRepickShipLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString().trim();
                if ((!input.equals("")) && (input.length() != 0)) {
                    new GetAddressesTask(getActivity(), RepickShipAddressDialog.this::onTaskCompleted).execute(input);
                } else {
                    repickShipAddressAdapter.displayPositionShipTitle();
                }
                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onTaskCompleted(List<String> addressList) {
        repickShipAddressAdapter.displayShipAddressResults(addressList);
    }
}
