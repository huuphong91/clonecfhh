package com.example.thecoffeehouse.main;

import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.news.NewsFragment;
import com.example.thecoffeehouse.order.OrderFragment;
import com.example.thecoffeehouse.profile.ProfileFragment;
import com.example.thecoffeehouse.store.views.StoreFragment;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private FragmentManager mFragmentManager;
    private DatabaseReference mDataRef;
    private String numberPhone;

    private com.example.thecoffeehouse.user.User mUser;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    loadFragment(NewsFragment.newInstance());
                    return true;
                case R.id.navigation_order:
                    loadFragment(OrderFragment.newInstance());
                    return true;
                case R.id.navigation_store:
                    loadFragment(StoreFragment.newInstance());
                    return true;
                case R.id.navigation_profile:
                    loadFragment(ProfileFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFragmentManager = getSupportFragmentManager();
        mDataRef = FirebaseDatabase.getInstance().getReference("Users");

    }

    private void loadFragment(Fragment fragment) {
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void printHashKey(){
        try{
            PackageInfo info = getPackageManager()
                    .getPackageInfo("com.example.thecoffeehouse",
                            PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                Log.d("KEYHASH: ", Base64.encodeToString(messageDigest.digest(),Base64.DEFAULT));
            }
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void checkLogged(){
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                numberPhone = "0" + account.getPhoneNumber().getPhoneNumber();
                Toast.makeText(MainActivity.this, "Logged", Toast.LENGTH_SHORT).show();
                loadUser();
            }

            @Override
            public void onError(AccountKitError accountKitError) {
                mUser = null;
                Toast.makeText(MainActivity.this, "Chua Login", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUser(){
        mDataRef.child(numberPhone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() != 0){
                    Toast.makeText(MainActivity.this, "Lay DC", Toast.LENGTH_SHORT).show();
                    mUser = dataSnapshot.getValue(com.example.thecoffeehouse.user.User.class);
                }
                else {
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "LOI", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
