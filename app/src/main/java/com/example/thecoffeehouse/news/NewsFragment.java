package com.example.thecoffeehouse.news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.entity.ResponseForYou;
import com.example.thecoffeehouse.data.model.entity.ResponseNews;
import com.example.thecoffeehouse.news.adapter.NewsAdapter;
import com.example.thecoffeehouse.news.adapter.NewsAdapterNews;
import com.example.thecoffeehouse.news.presenter.ForYouPresenter;
import com.example.thecoffeehouse.news.presenter.NewsPresenter;
import com.example.thecoffeehouse.news.viewnews.ForYouView;
import com.example.thecoffeehouse.news.viewnews.NewsView;
import com.example.thecoffeehouse.user.User;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsFragment extends Fragment implements NewsView, ForYouView {

     View rootView;
    private TextView mTxtName, mTxtLogin;
    private ImageView mImgAccount, mImgNotification;
    String numberPhone;
    private DatabaseReference mDataRef;
    private User mUser;
    private AppCompatActivity activity;
    private NewsAdapter adapter;
    private NewsAdapterNews adapter2;
    private NewsPresenter newsPresenter;
    private ForYouPresenter forYouPresenter;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news, container, false);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.for_you_recycleview);
        final RecyclerView recyclerViewNews = (RecyclerView) rootView.findViewById(R.id.news_recycleview);
        List<ResponseNews> listNews= new ArrayList<> ();
        List<ResponseForYou> listForYou = new ArrayList<>();
        adapter = new NewsAdapter(getContext(),listForYou,getFragmentManager());
        adapter2 = new NewsAdapterNews(getContext(),listNews, getFragmentManager());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //recyclerViewNews.setVerticalFadingEdgeEnabled(true);
        RecyclerView.LayoutManager mNewsLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerViewNews.setLayoutManager(mNewsLayoutManager);
        recyclerViewNews.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNews.setAdapter(adapter2);
        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private  void getForYouList()
    {
        forYouPresenter.getForYou();
    }
    private void getNewsList() {

        newsPresenter.getNews ();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setupNews();
    }

    public void setupNews()
    {
        newsPresenter=new NewsPresenter(NewsFragment.this);
        forYouPresenter=new ForYouPresenter(NewsFragment.this);
    }

    private void initView(View view) {
        mTxtName = view.findViewById(R.id.news_account_name);
        mTxtName.setVisibility(View.GONE);
        mTxtLogin = view.findViewById(R.id.news_text_account);
        mImgAccount = view.findViewById(R.id.news_account_image);
        mImgNotification = view.findViewById(R.id.news_push_notification);
        mTxtName = view.findViewById(R.id.news_account_name);
        mTxtName.setVisibility(View.GONE);
        mTxtLogin = view.findViewById(R.id.news_text_account);
        mImgAccount = view.findViewById(R.id.news_account_image);
        mImgNotification = view.findViewById(R.id.news_push_notification);
        mDataRef = FirebaseDatabase.getInstance().getReference("Users");
        activity = (AppCompatActivity) getActivity();
        mUser = new User();
    }

    @Override
    public void displayNews(List<ResponseNews> itemList) {
        List<ResponseNews> list = new ArrayList<>();
        if (itemList != null) {
            for (ResponseNews item : itemList) {
                list.add (item);
            }
        }
        adapter2.setValues(list);
    }

    @Override
    public void displayForYou(List<ResponseForYou> for_you_itemlist) {
        List<ResponseForYou> listforyou = new ArrayList<> ();
        if (for_you_itemlist != null) {
            for (ResponseForYou item : for_you_itemlist) {
                listforyou.add (item);
            }
        }
        adapter.setValues(listforyou);
    }

    @Override
    public void displayError(String s) {

    }

    @Override
    public void onResume() {
        super.onResume ();
        getNewsList ();
        getForYouList();
    }


    private void checkLogged(){
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                numberPhone = "0" + account.getPhoneNumber().getPhoneNumber();
                Toast.makeText(activity, "Logged", Toast.LENGTH_SHORT).show();
                loadUser();
            }

            @Override
            public void onError(AccountKitError accountKitError) {
                mUser = null;
                Toast.makeText(activity, "Chua Login", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUser(){
        mDataRef.child(numberPhone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() != 0){
                    Toast.makeText(activity, "Lay DC", Toast.LENGTH_SHORT).show();
                    mUser = dataSnapshot.getValue(User.class);
                    setUserView(mUser);


                }
                else {
                    Toast.makeText(activity, "Server quá đông, thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(activity, "LOI", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setUserView(User user){
        if(mUser != null){
            Toast.makeText(activity, ""+user.getLastName(), Toast.LENGTH_SHORT).show();
           Bitmap bitmap = StringToBitMap(user.getImage());
           mImgAccount.setImageBitmap(bitmap);
           mTxtLogin.setVisibility(View.GONE);
           mTxtName.setText(user.getFirstName()+" "+user.getLastName());
           mTxtName.setVisibility(View.VISIBLE);
        }
    }

    private Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
