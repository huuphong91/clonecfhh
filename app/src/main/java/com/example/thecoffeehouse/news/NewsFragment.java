package com.example.thecoffeehouse.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.RxBus;
import com.example.thecoffeehouse.data.AppRespositoryImp;
import com.example.thecoffeehouse.data.model.user.User;
import com.example.thecoffeehouse.data.model.entity.ResponseForYou;
import com.example.thecoffeehouse.data.model.entity.ResponseNews;
import com.example.thecoffeehouse.login.LoginDialogFragment;
import com.example.thecoffeehouse.main.FragmentInteractionListener;
import com.example.thecoffeehouse.news.adapter.NewsAdapterForYou;
import com.example.thecoffeehouse.news.adapter.NewsAdapterNews;
import com.example.thecoffeehouse.news.presenter.ForYouPresenter;
import com.example.thecoffeehouse.news.presenter.ForYouPresenterImpl;
import com.example.thecoffeehouse.news.presenter.NewsPresenter;
import com.example.thecoffeehouse.news.presenter.NewsPresenterImpl;
import com.example.thecoffeehouse.news.viewnews.ForYouView;
import com.example.thecoffeehouse.news.viewnews.NewsView;
import com.example.thecoffeehouse.notification.NotificationFragment;
import com.example.thecoffeehouse.update.UpdateFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsFragment extends Fragment implements NewsView, ForYouView {

    private TextView mTxtName, mTxtLogin;
    private ImageView mImgAccount, mImgNotification;
    private User mUser;
    private AppCompatActivity activity;
    private FragmentInteractionListener mListener;
    private SharedPreferences mPrefs;
    private String numberPhone;
    private DatabaseReference mDataRef;
    private NewsAdapterForYou adapter;
    private NewsAdapterNews adapter2;
    private NewsPresenter newsPresenter;
    private ForYouPresenter forYouPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            mListener = (FragmentInteractionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        return view;
    }

    private void getForYouList() {
        forYouPresenter.loadListNewsFromDatabase();
    }

    private void getNewsList() {
        newsPresenter.loadNewsForFromDatabase();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        final RecyclerView recyclerView = view.findViewById(R.id.for_you_recycleview);
        final RecyclerView recyclerViewNews = view.findViewById(R.id.news_recycleview);
        swipeRefreshLayout = view.findViewById(R.id.swiper_fresh);
        List<ResponseNews> listNews = new ArrayList<>();
        List<ResponseForYou> listForYou = new ArrayList<>();
        adapter = new NewsAdapterForYou(getContext(), listForYou, getFragmentManager());
        adapter2 = new NewsAdapterNews(getContext(), listNews, getFragmentManager());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager mNewsLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewNews.setLayoutManager(mNewsLayoutManager);
        recyclerViewNews.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNews.setAdapter(adapter2);
        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(mLayoutManager1);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

            RecyclerView.LayoutManager mNewsLayoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerViewNews.setLayoutManager(mNewsLayoutManager1);
            recyclerViewNews.setItemAnimator(new DefaultItemAnimator());
            recyclerViewNews.setAdapter(adapter2);
            adapter.notifyDataSetChanged();
            adapter2.notifyDataSetChanged();
            swipeRefreshLayout.getDrawingTime();
            swipeRefreshLayout.setRefreshing(false);
        });
        setupNews();
    }

    public void setupNews() {
        newsPresenter = new NewsPresenterImpl(this);
        forYouPresenter = new ForYouPresenterImpl(this);
        getNewsList();
        getForYouList();
    }

    private void initView(View view) {
        mTxtName = view.findViewById(R.id.news_account_name);
        mTxtName.setVisibility(View.GONE);
        mTxtLogin = view.findViewById(R.id.news_text_account);
        mImgAccount = view.findViewById(R.id.news_account_image);
        mImgNotification = view.findViewById(R.id.news_push_notification);
        activity = (AppCompatActivity) getActivity();
        mPrefs = activity.getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        mDataRef = FirebaseDatabase.getInstance().getReference("Users");
        mUser = new User();
//        AppRespositoryImp appRespositoryImp = new AppRespositoryImp(getActivity().getApplication());
//        mImgNotification.setOnClickListener(v -> appRespositoryImp.getNotification(this));
        mImgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChangeFragment(NotificationFragment.newInstance(), Constant.NOTIFICATION_FRAGMENT);
            }
        });
    }

    private void loggedView(User user) {
        Glide.with(activity).load(user.getImage()).placeholder(R.mipmap.store_placeholder).into(mImgAccount);
        mTxtName.setText(user.getFirstName() + " " + user.getLastName());
        mTxtName.setVisibility(View.VISIBLE);
        mImgAccount.setOnClickListener(v -> {
//            UpdateFragment.newInstance().show(getChildFragmentManager(), Constant.UPDATE_FRAGMENT);
            mListener.onChangeFragment(UpdateFragment.newInstance(), Constant.UPDATE_FRAGMENT);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LOGGGGGGGGGG", "onStart: ");
//        RxBus.getInstance()
//                .getObservable()
//                .filter(o -> o instanceof User)
//                .subscribe(o -> {
//                    User user = (User) o;
//                    loggedView(user);
//                });
    }

    @Override
    public void onResume() {
        super.onResume();
        checkUserLogin();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void unLoginView() {
        mImgAccount.setImageResource(R.mipmap.ic_person);
        mTxtLogin.setVisibility(View.VISIBLE);
        mTxtLogin.setOnClickListener(view -> {
//            LoginDialogFragment.newInstance().show(getFragmentManager(), Constant.LOGIN_FRAGMENT);
            mListener.onChangeFragment(LoginDialogFragment.newInstance(), Constant.LOGIN_FRAGMENT);
        });
    }

    private void checkUserLogin() {
        Gson gson = new Gson();
        mUser = new User();
        String json = mPrefs.getString("myObject", null);
        mUser = gson.fromJson(json, User.class);
        if (mUser != null) {
            loggedView(mUser);
        } else {
            unLoginView();
        }
    }
//
//    private Bitmap StringToBitMap(String encodedString) {
//        try {
//            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//            return bitmap;
//        } catch (Exception e) {
//            e.getMessage();
//            return null;
//        }
//    }

    @Override
    public void displayNews(List<ResponseNews> itemList) {
        List<ResponseNews> list = new ArrayList<>();
        if (itemList != null) {
            list.addAll(itemList);
        }
        adapter2.setValues(list);
    }

    @Override
    public void onError(Throwable throwable) {
        Toast.makeText(activity, "Something wrong! Try a gain.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayForYou(List<ResponseForYou> for_you_itemlist) {
        List<ResponseForYou> listforyou = new ArrayList<>();
        if (for_you_itemlist != null) {
            listforyou.addAll(for_you_itemlist);
        }
        adapter.setValues(listforyou);
    }
}