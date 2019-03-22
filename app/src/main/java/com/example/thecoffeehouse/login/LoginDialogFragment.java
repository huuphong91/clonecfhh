package com.example.thecoffeehouse.login;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dx.dxloadingbutton.lib.LoadingButton;
import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.firstupdate.FirstUpdateFragment;
import com.example.thecoffeehouse.main.FragmentInteractionListener;
import com.example.thecoffeehouse.main.IIIII;
import com.example.thecoffeehouse.main.MainActivity;
import com.example.thecoffeehouse.main.OnUpdateListener;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class LoginDialogFragment extends Fragment implements IPresenterLoginContract.View {

    IPresenterLoginContract.Presenter presenter;
    private final static int REQUEST_CODE = 999;
    private Toolbar toolbar;
    private ImageView mImageDelete;
    private LoadingButton mButtonCommit;
    private Button mButtonFB;
    private Button mButtonEmail;
    private EditText mEditTextPhone;
    private AppCompatActivity activity;
    private String numberPhone;
    private FragmentInteractionListener mListener;
    private IIIII iiiii;
    private OnUpdateListener onUpdateListener;

    public static LoginDialogFragment newInstance() {
        LoginDialogFragment fragment = new LoginDialogFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            mListener = (FragmentInteractionListener) context;
        }
        if(context instanceof OnUpdateListener){
            onUpdateListener = (OnUpdateListener) context;
        }
        if( context instanceof IIIII){
            iiiii = (IIIII) context;
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_dialog, container, false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_NoActionBar);
    }

    private void startLoginPage(LoginType loginType, String numberPhone) {
        if (loginType == LoginType.EMAIL) {
            Intent i = new Intent(activity, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                    new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.EMAIL,
                            AccountKitActivity.ResponseType.TOKEN);
            i.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
            startActivityForResult(i, REQUEST_CODE);
        } else if (loginType == LoginType.PHONE) {
            Intent i = new Intent(activity, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                    new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,
                            AccountKitActivity.ResponseType.TOKEN);
            configurationBuilder.setInitialPhoneNumber(new PhoneNumber("+84", numberPhone));
            i.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
            startActivityForResult(i, REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            AccountKitLoginResult accountKitLoginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (accountKitLoginResult.getError() != null) {
                Toast.makeText(activity, "" + accountKitLoginResult.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
                return;
            } else if (accountKitLoginResult.wasCancelled()) {
                Toast.makeText(activity, "CANCEL", Toast.LENGTH_SHORT).show();
                return;
            } else {

                presenter.checkFirstLogin(numberPhone);
                if (accountKitLoginResult.getAccessToken() != null) {
//                    Toast.makeText(activity, "Success! %s" + accountKitLoginResult.getAccessToken().getAccountId(), Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(activity, "Success! %s" + accountKitLoginResult.getAuthorizationCode().substring(10, 0), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onCheckSucess(String result) {
        Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckFail(String result) {
//        FirstUpdateFragment.newInstance().show(getFragmentManager(), Constant.FIRST_UPDATE_FRAGMENT);
        mListener.onChangeFragment(FirstUpdateFragment.newInstance(), Constant.FIRST_UPDATE_FRAGMENT);

    }

    @Override
    public void onLoadSucess(String result) {
        mButtonCommit.loadingSuccessful();
        //LoginDialogFragment.this.dismiss();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mStartActivity = new Intent(getContext(), MainActivity.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(getContext(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                System.exit(0);
            }
        }, 2000);
    }

    @Override
    public void onLoadFail(String result) {
        Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
        mButtonCommit.loadingFailed();
    }

    @Override
    public Context activity() {
        return getContext();
    }

    private void initView(View view) {
        toolbar = view.findViewById(R.id.frag_login_toolbar);
        toolbar.setTitle("");
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        mImageDelete = toolbar.findViewById(R.id.frag_login_img_delete);
        mButtonCommit = view.findViewById(R.id.frag_login_btn_commit);
        mButtonFB = view.findViewById(R.id.frag_login_btn_fb);
        mButtonEmail = view.findViewById(R.id.frag_login_btn_email);
        mEditTextPhone = view.findViewById(R.id.fragment_edt_phone);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        presenter = new PresenterLogin(this);
        activity.setSupportActionBar(toolbar);
    }

    private void initEvent() {
        mImageDelete.setOnClickListener(v -> onUpdateListener.onUpdateFragment());

        mButtonEmail.setOnClickListener(v -> {
            Toast.makeText(activity, "I'm Tan ", Toast.LENGTH_SHORT).show();
        });

        mButtonFB.setOnClickListener(v -> {
            SharedPreferences mPrefs = getContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.clear();
            editor.commit();
            AccountKit.logOut();
            Log.d( "initEvent: ","------");
            onUpdateListener.onUpdateFragment();
//            Intent newIntent = new Intent(activity, MainActivity.class);
//            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(newIntent);
        });

        mButtonCommit.setOnClickListener(v -> {
            mButtonCommit.startLoading();
            numberPhone = mEditTextPhone.getText().toString();
            startLoginPage(LoginType.PHONE, numberPhone);
        });
    }
}
