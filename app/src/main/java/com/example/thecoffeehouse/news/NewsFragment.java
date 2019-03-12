package com.example.thecoffeehouse.news;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thecoffeehouse.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsFragment extends Fragment {
    View rootView;
    private NewsAdapter adapter;
    private NewsAdapter_News adapter2;
    private List<News> albumList, albumListVer;
    private TextView mTxtName, mTxtLogin;
    private ImageView mImgAccount, mImgNotification;


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
        albumList = new ArrayList<>();
        albumListVer = new ArrayList<>();
        adapter = new NewsAdapter(getContext(), albumList);
        adapter2 = new NewsAdapter_News(getContext(), albumListVer);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(8, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerViewNews.setVerticalFadingEdgeEnabled(true);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNews.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNews.setAdapter(adapter2);
        prepareAlbums();
        return rootView;
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.images_coffe,
                R.drawable.images_coffe2,
                R.drawable.images_coffe3,
                R.drawable.images_coffe4,
                R.drawable.images_coffe5,
                R.drawable.images_coffe6,
                R.drawable.images_coffe7,
                R.drawable.images_coffe8,
        };

        News a = new News("Coffee One Dotta", covers[0]);
        albumList.add(a);
        a = new News("Tra Dao 2 ", covers[1]);
        albumList.add(a);
        albumListVer.add(a);
        a = new News("Tra Dao 5", covers[2]);
        albumList.add(a);

        a = new News("Coffee với sữa", covers[3]);
        albumList.add(a);
        a = new News("Coffe Cabuchino", covers[4]);
        albumList.add(a);
        a = new News("Coffe House Sữa", covers[5]);
        albumList.add(a);
        a=new News("Coffee được làm để uống kèm với bánh co tác dung làm cho cơ thể đủ sức tỉnh táo...",covers[6]);
        albumListVer.add(a);
        a=new News("Coffee được làm để tăng sức khỏe kèm khi bạn uống ít tác dung làm cho cơ thể đủ sức tỉnh táo...",covers[7]);
        albumListVer.add(a);
//        a=new News("Coffee được làm để tăng sức khỏe kèm khi bạn uống cùng bạn bè nhân dịp lễ 8/3 của thế giới phụ nữ kỉ niệm điều tuyệt vời...",covers[8]);
//        albumListVer.add(a);
        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
    }

    private void initView(View view) {
        mTxtName = view.findViewById(R.id.news_account_name);
        mTxtName.setVisibility(View.GONE);
        mTxtLogin = view.findViewById(R.id.news_text_account);
        mImgAccount = view.findViewById(R.id.news_account_image);
        mImgNotification = view.findViewById(R.id.news_push_notification);
    }
}
