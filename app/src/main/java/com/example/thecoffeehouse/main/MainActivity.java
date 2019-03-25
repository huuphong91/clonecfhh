package com.example.thecoffeehouse.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.AppRespositoryImp;
import com.example.thecoffeehouse.data.model.notification.Notification;
import com.example.thecoffeehouse.data.model.user.User;
import com.example.thecoffeehouse.data.model.product.DataItem;
import com.example.thecoffeehouse.firstupdate.FirstUpdateFragment;
import com.example.thecoffeehouse.login.LoginDialogFragment;
import com.example.thecoffeehouse.news.NewsFragment;
import com.example.thecoffeehouse.notification.NotificationFragment;
import com.example.thecoffeehouse.order.OrderFragment;
import com.example.thecoffeehouse.order.adapter.OnOrderListItemInteractionListener;
import com.example.thecoffeehouse.order.cart.CartFragment;
import com.example.thecoffeehouse.order.cart.CartInstance;
import com.example.thecoffeehouse.order.cart.adpater.OnOrderListCartListener;
import com.example.thecoffeehouse.order.cart.cartdetail.CartDetail;
import com.example.thecoffeehouse.order.cart.database.CartViewModel;
import com.example.thecoffeehouse.order.cart.model.Cart;
import com.example.thecoffeehouse.order.detail.DetailDialogFragment;
import com.example.thecoffeehouse.order.drinks.DrinksFragment;
import com.example.thecoffeehouse.order.hightlight.HighLightDrinks;
import com.example.thecoffeehouse.profile.ProfileFragment;
import com.example.thecoffeehouse.store.views.StoreFragment;
import com.example.thecoffeehouse.update.UpdateFragment;
import com.example.thecoffeehouse.update.editfirstname.EditFirstNameFragment;
import com.example.thecoffeehouse.update.editlastname.EditLastNameFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


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
        super.onCreate (savedInstanceState);
        setContentView (R.layout.fragment_main);
        setAnimation ();
        initView ();
        addFragment (MainFragment.newInstance ());
        initData ();
        createNotification ();
        Log.d ("onCreate: ", getResources ().getDisplayMetrics ().densityDpi + "----");
    }

    private void initData() {
        mCartViewModel.getAllCarts ().observe (this, carts ->
                CartInstance.getInstance ().setListCart (carts));
    }


    private void initView() {
        mFragmentManager = getSupportFragmentManager ();
        mDataRef = FirebaseDatabase.getInstance ().getReference ("Users");
        mCartViewModel = ViewModelProviders.of (this).get (CartViewModel.class);
        mFragmentBackstackStateManager = new FragmentBackstackStateManager ();
        mFragmentBackstackStateManager.apply (mFragmentManager);
        mPrefs = getSharedPreferences ("dataUser", MODE_PRIVATE);

    }

    private void addFragment(Fragment fragment) {
        mFragmentManager.beginTransaction ()
                .add (R.id.content_main, fragment, Constant.MAIN_FRAGMENT)
                .addToBackStack (null)
                .commit ();
    }

    private void loadFragment(Fragment fragment, String tag) {
        mFragmentManager.beginTransaction ()
                .setCustomAnimations (R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace (R.id.content_main, fragment, tag)
                .addToBackStack (tag)
                .commit ();
    }

    private void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Explode explode = new Explode ();
            explode.setDuration (700);
            explode.setInterpolator (new AccelerateDecelerateInterpolator ());
            getWindow ().setExitTransition (explode);
            getWindow ().setEnterTransition (explode);
        }
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

//
//    private void checkdataUser() {
//        Gson gson = new Gson ();
//        String json = mPrefs.getString ("myObject", null);
//        User user = gson.fromJson (json, User.class);
//        if (json != null) {
//            Toast.makeText (MainActivity.this, "Co roi" + user.getFirstName (), Toast.LENGTH_SHORT).show ();
//        } else {
//            Toast.makeText (this, "Rong", Toast.LENGTH_SHORT).show ();
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume ();
//        checkdataUser ();
    }

    private void checkdataUser() {
        Gson gson = new Gson ();
        String json = mPrefs.getString ("myObject", null);
        User user = gson.fromJson (json, User.class);
        if (json != null) {
        } else {
        }
    }

    @Override
    public void onChangeFragment(Fragment fragment, String tag) {
        loadFragment (fragment, tag);
    }

    @Override
    public void onUpdateFragment() {
        onBackPressed ();
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
        Fragment fragment = mFragmentManager.findFragmentByTag (Constant.EDIT_LNAME_FRAGMENT);
        if (fragment instanceof EditLastNameFragment) {
            super.onBackPressed ();
            return;
        }
        fragment = mFragmentManager.findFragmentByTag (Constant.FIRST_UPDATE_FRAGMENT);
        if (fragment instanceof FirstUpdateFragment) {
            super.onBackPressed ();
            return;
        }
        fragment = mFragmentManager.findFragmentByTag (Constant.LOGIN_FRAGMENT);
        if (fragment instanceof LoginDialogFragment) {
            super.onBackPressed ();
            return;
        }
        fragment = mFragmentManager.findFragmentByTag (Constant.EDIT_FNAME_FRAGMENT);
        if (fragment instanceof EditFirstNameFragment) {
            super.onBackPressed ();
            return;
        }
        fragment = mFragmentManager.findFragmentByTag (Constant.UPDATE_FRAGMENT);
        if (fragment instanceof UpdateFragment) {
            super.onBackPressed ();
            return;
        }
        fragment = mFragmentManager.findFragmentByTag (Constant.CART_FRAGMENT);
        if (fragment instanceof CartFragment) {
            super.onBackPressed ();
            return;
        }
        fragment = mFragmentManager.findFragmentByTag (Constant.NOTIFICATION_FRAGMENT);
        if (fragment instanceof NotificationFragment) {
            super.onBackPressed ();
            return;
        }
        fragment = mFragmentManager.findFragmentByTag (Constant.ORDER_FRAGMENT);
        if (fragment instanceof OrderFragment) {
            return;
        }
        fragment = mFragmentManager.findFragmentByTag (Constant.STORE_FRAGMENT);
        if (fragment instanceof StoreFragment) {
            return;
        }
        fragment = mFragmentManager.findFragmentByTag (Constant.PROFILE_FRAGMENT);
        if (fragment instanceof ProfileFragment) {
            return;
        }
        fragment = mFragmentManager.findFragmentByTag (Constant.NEWS_FRAGMENT);
        if (fragment instanceof NewsFragment) {
            return;
        }
        fragment = mFragmentManager.findFragmentByTag (Constant.MAIN_FRAGMENT);
        if (fragment instanceof MainFragment) {
            finish ();
        }
        overridePendingTransition (R.anim.abc_slide_out_bottom, R.anim.abc_slide_in_bottom);
//        super.onBackPressed();
    }

    public void clearStack() {
        //Here we are clearing back stack fragment entries
        int backStackEntry = getSupportFragmentManager ().getBackStackEntryCount ();
        if (backStackEntry > 0) {
            for (int i = 0; i < backStackEntry; i++) {
                getSupportFragmentManager ().popBackStackImmediate ();
            }
        }

        //Here we are removing all the fragment that are shown here
        if (getSupportFragmentManager ().getFragments () != null && getSupportFragmentManager ().getFragments ().size () > 0) {
            for (int i = 0; i < getSupportFragmentManager ().getFragments ().size (); i++) {
                Fragment mFragment = getSupportFragmentManager ().getFragments ().get (i);
                if (mFragment != null
                        && !(mFragment instanceof MainFragment)
                        && !(mFragment instanceof NewsFragment)
                        && !(mFragment instanceof OrderFragment)
                        && !(mFragment instanceof StoreFragment)
                        && !(mFragment instanceof ProfileFragment
                        && !(mFragment instanceof DrinksFragment)
                        && !(mFragment instanceof HighLightDrinks))) {
                    getSupportFragmentManager ().beginTransaction ().remove (mFragment).commit ();
                }
            }
        }
    }

    private void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel ("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService (NotificationManager.class);
            manager.createNotificationChannel (channel);
        }

        FirebaseMessaging.getInstance ().subscribeToTopic ("general")
                .addOnCompleteListener (new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Sucessfully";
                        if (!task.isSuccessful ()) {
                            msg = "Fail";
                        }
                        Log.d ("TAG", msg);
                    }
                });
    }

}
