package com.example.thecoffeehouse.main;

import android.accounts.Account;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.User.User;
import com.example.thecoffeehouse.data.model.product.DataItem;
import com.example.thecoffeehouse.firstupdate.FirstUpdateFragment;
import com.example.thecoffeehouse.login.LoginDialogFragment;
import com.example.thecoffeehouse.news.NewsFragment;
import com.example.thecoffeehouse.order.OrderFragment;
//import com.example.thecoffeehouse.order.adapter.OnOrderListItemInteractionListener;
import com.example.thecoffeehouse.order.adapter.OnOrderListItemInteractionListener;
import com.example.thecoffeehouse.order.cart.CartInstance;
import com.example.thecoffeehouse.order.cart.adpater.OnOrderListCartListener;
import com.example.thecoffeehouse.order.cart.cartdetail.CartDetail;
import com.example.thecoffeehouse.order.cart.database.CartViewModel;
import com.example.thecoffeehouse.order.cart.model.Cart;
import com.example.thecoffeehouse.order.detail.DetailDialogFragment;
import com.example.thecoffeehouse.profile.ProfileFragment;
import com.example.thecoffeehouse.store.views.StoreFragment;
import com.example.thecoffeehouse.update.UpdateFragment;
import com.example.thecoffeehouse.update.editfirstname.EditFirstNameFragment;
import com.example.thecoffeehouse.update.editgender.EditGenderFragment;
import com.example.thecoffeehouse.update.editgender.EditGenderPresenter;
import com.example.thecoffeehouse.update.editlastname.EditLastNameFragment;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;


public class MainActivity extends AppCompatActivity implements FragmentInteractionListener, OnUpdateListener,
        OnOrderListItemInteractionListener, OnOrderListCartListener {

    private FragmentManager mFragmentManager;
    private FragmentBackstackStateManager mFragmentBackstackStateManager;
    private DatabaseReference mDataRef;
    private String numberPhone;
    private CartViewModel mCartViewModel;
    private SharedPreferences mPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        initView();
        addFragment(MainFragment.newInstance());
        initData();
        Log.d("onCreate: ", getResources().getDisplayMetrics().densityDpi + "----");
    }

    private void initData() {
        mCartViewModel.getAllCarts().observe(this, carts ->
                CartInstance.getInstance().setListCart(carts));
    }



    private void initView() {
        mFragmentManager = getSupportFragmentManager();
        mDataRef = FirebaseDatabase.getInstance().getReference("Users");
        mCartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager = getSupportFragmentManager();
        mFragmentBackstackStateManager = new FragmentBackstackStateManager();
        mFragmentBackstackStateManager.apply(mFragmentManager);

        mPrefs = getSharedPreferences("dataUser",MODE_PRIVATE);

    }

    private void addFragment(Fragment fragment) {
        mFragmentManager.beginTransaction()
                .add(R.id.content_main, fragment, Constant.MAIN_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    private void loadFragment(Fragment fragment, String tag) {
        mFragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    //get HashKey's application
//    private void printHashKey(){
//        try{
//            PackageInfo info = getPackageManager()
//                    .getPackageInfo("com.example.thecoffeehouse",
//                            PackageManager.GET_SIGNATURES);
//            for(Signature signature : info.signatures){
//                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
//                Log.d("KEYHASH: ", Base64.encodeToString(messageDigest.digest(),Base64.DEFAULT));
//            }
//        }catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }


    private void checkdataUser(){
        Gson gson = new Gson();
        String json = mPrefs.getString("myObject", null);
        User user = gson.fromJson(json,User.class);
        if(json != null){
            Toast.makeText(MainActivity.this, "Co roi"+user.getFirstName(), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Rong", Toast.LENGTH_SHORT).show();
        }
    }
//0797134182

    @Override
    protected void onResume() {
        super.onResume();
        checkdataUser();
    }


    @Override
    public void onChangeFragment(Fragment fragment, String tag) {
        loadFragment(fragment, tag);
    }

    @Override
    public void onUpdateFragment() {
        Log.d("onUpdateFragment: ", "---------");
        mFragmentBackstackStateManager.getListener().onBackStackChanged();
        Fragment fragment = mFragmentManager.findFragmentByTag(Constant.EDIT_LNAME_FRAGMENT);
        if (fragment instanceof EditLastNameFragment) {
            onBackPressed();
            return;
        }
        fragment = mFragmentManager.findFragmentByTag(Constant.FIRST_UPDATE_FRAGMENT);
        if (fragment instanceof FirstUpdateFragment) {
            onBackPressed();
            return;
        }

        fragment = mFragmentManager.findFragmentByTag(Constant.LOGIN_FRAGMENT);
        if (fragment instanceof LoginDialogFragment) {
            onBackPressed();
            return;
        }
        fragment = mFragmentManager.findFragmentByTag(Constant.EDIT_FNAME_FRAGMENT);
        if (fragment instanceof EditFirstNameFragment) {
            onBackPressed();
            return;
        }
        fragment = mFragmentManager.findFragmentByTag(Constant.UPDATE_FRAGMENT);
        if (fragment instanceof UpdateFragment) {
            onBackPressed();
            return;
        }

//        onBackPressed();

    }
    @Override
    public void onItemClickListener(DataItem dataItem) {
        DetailDialogFragment.newInstance (dataItem).show (mFragmentManager, "data");
    }

    @Override
    public void onListCartItemClickListener(Cart dataItem) {
        CartDetail.newInstance (dataItem).show (mFragmentManager, "cart");
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
