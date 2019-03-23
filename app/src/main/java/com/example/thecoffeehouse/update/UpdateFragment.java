package com.example.thecoffeehouse.update;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.User.User;
import com.example.thecoffeehouse.main.FragmentInteractionListener;
import com.example.thecoffeehouse.main.IIIII;
import com.example.thecoffeehouse.main.OnUpdateListener;
import com.example.thecoffeehouse.update.editbirthday.EditBirthDayFragment;
import com.example.thecoffeehouse.update.editfirstname.EditFirstNameFragment;
import com.example.thecoffeehouse.update.editgender.EditGenderFragment;
import com.example.thecoffeehouse.update.editimage.EditImagePresenter;
import com.example.thecoffeehouse.update.editimage.IEditImageContract;
import com.example.thecoffeehouse.update.editlastname.EditLastNameFragment;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment implements IEditImageContract.View {

    private IEditImageContract.Presenter presenter;
    private int RESULT_LOAD_IMG;
    private Uri imageUri;
    private User user;
    private RelativeLayout mRelChangeLastName;
    private RelativeLayout mRelChangeFirstName;
    private RelativeLayout mRelChangeBirthDay;
    private RelativeLayout mRelChangePhone;
    private RelativeLayout mRelChangeGendle;
    private TextView mTextViewLastName;
    private TextView mTextViewFirstName;
    private TextView mTextViewBirthDay;
    private TextView mTextViewPhone;
    private TextView mTextViewGendle;
    private ImageView mImageViewBack;
    private ImageView mImageViewUser;
    private SharedPreferences mPrefs;
    private AppCompatActivity activity;
    private FragmentInteractionListener mListener;
    private OnUpdateListener onUpdateListener;
    private IIIII iiiii;

    public static UpdateFragment newInstance() {
        UpdateFragment fragment = new UpdateFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            mListener = (FragmentInteractionListener) context;
        }
        if (context instanceof OnUpdateListener) {
            onUpdateListener = (OnUpdateListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_NoActionBar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        initView(view);
        checkdataUser();
        initEvents();
        return view;
    }

    private void initView(View view) {
        mRelChangeLastName = view.findViewById(R.id.update_frag_rlLastName);
        mRelChangeFirstName = view.findViewById(R.id.update_frag_rlFirstName);
        mRelChangeBirthDay = view.findViewById(R.id.update_frag_rlBirthDay);
        mRelChangePhone = view.findViewById(R.id.update_frag_rlPhone);
        mRelChangeGendle = view.findViewById(R.id.update_frag_rlGendle);
        mTextViewLastName = view.findViewById(R.id.update_frag_tvLastName);
        mTextViewFirstName = view.findViewById(R.id.update_frag_tvFirstName);
        mTextViewBirthDay = view.findViewById(R.id.update_frag_tvBirthDay);
        mTextViewPhone = view.findViewById(R.id.update_frag_tvNumberPhone);
        mTextViewGendle = view.findViewById(R.id.update_frag_tvGendle);
        mImageViewBack = view.findViewById(R.id.update_frag_imgBack);
        mImageViewUser = view.findViewById(R.id.update_frag_imgUser);
        activity = new AppCompatActivity();
        mPrefs = getActivity().getSharedPreferences("dataUser", MODE_PRIVATE);
        presenter = new EditImagePresenter(this);


    }

    private void initEvents() {
        mRelChangeLastName.setOnClickListener(v -> {
//            EditLastNameFragment.newInstance(user.getPhoneNumber(),user.getLastName()).show(getActivity().getSupportFragmentManager(), Constant.EDIT_NAME_FRAGMENT);
//            getDialog().dismiss();
            mListener.onChangeFragment(EditLastNameFragment.newInstance(user.getPhoneNumber(), user.getLastName()), Constant.EDIT_LNAME_FRAGMENT);
        });

        mRelChangeFirstName.setOnClickListener(v -> {
            mListener.onChangeFragment(EditFirstNameFragment.newInstance(user.getPhoneNumber(), user.getFirstName()), Constant.EDIT_FNAME_FRAGMENT);
//            EditFirstNameFragment.newInstance(user.getPhoneNumber(),user.getFirstName()).show(getActivity().getSupportFragmentManager(), Constant.EDIT_NAME_FRAGMENT);
//            getDialog().dismiss();
        });

        mRelChangeBirthDay.setOnClickListener(v -> {
            mListener.onChangeFragment(EditBirthDayFragment.newInstance(user.getPhoneNumber(), user.getBirthday()), Constant.EDIT_FNAME_FRAGMENT);

        });

        mRelChangePhone.setOnClickListener(v -> {

        });

        mRelChangeGendle.setOnClickListener(v -> {
//            dialogBox();
            mListener.onChangeFragment(EditGenderFragment.newInstance(user.getPhoneNumber()),Constant.BOTTOM_SHEET);
//            iiiii.iiiiii(EditGenderFragment.newInstance(user.getPhoneNumber()),Constant.BOTTOM_SHEET);
        });

        mImageViewBack.setOnClickListener(v -> {

//            Intent newIntent = new Intent(getContext(), MainActivity.class);
//            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(newIntent);
            Log.d( "initEvents: ", "----");
            onUpdateListener.onUpdateFragment();
        });

        mImageViewUser.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        });
    }

    private void checkdataUser() {
        Gson gson = new Gson();
        String json = mPrefs.getString("myObject", null);
        user = gson.fromJson(json, User.class);
        if (json != null) {
            Toast.makeText(getActivity(), "Co roi" + user.getFirstName(), Toast.LENGTH_SHORT).show();
            mTextViewLastName.setText(user.getLastName());
            mTextViewFirstName.setText(user.getFirstName());
            mTextViewBirthDay.setText(user.getBirthday());
            mTextViewPhone.setText(user.getPhoneNumber());
            mTextViewGendle.setText(user.getGender());
            Glide.with(getContext()).load(user.getImage()).placeholder(R.drawable.img_bg_tch).into(mImageViewUser);
        } else {
            Toast.makeText(getActivity(), "Rong", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onResume() {
        Log.d("11111111onResume: ", "");
        super.onResume();

    }

    @Override
    public void onPause() {
        Log.d("11111111onpause: ", "");
        super.onPause();
    }

    @Override
    public void onStart() {
        Log.d("11111111start: ", "");
        super.onStart();
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mImageViewUser.setImageBitmap(selectedImage);
                presenter.editImage(user.getPhoneNumber(),user.getImage(),selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onChangeSuccess(String messege) {

    }

    @Override
    public void onChangeFail(String messege) {

    }

    @Override
    public Context getContextt() {
        return getContext();
    }

    public void dialogBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Click on Image for tag");
        alertDialogBuilder.setPositiveButton("Ok",
                (arg0, arg1) -> {
                    Toast.makeText(getContext(), "OKE", Toast.LENGTH_SHORT).show();
                });

        alertDialogBuilder.setNegativeButton("cancel",
                (arg0, arg1) -> {
                    Toast.makeText(getContext(), "Cacel", Toast.LENGTH_SHORT).show();
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}