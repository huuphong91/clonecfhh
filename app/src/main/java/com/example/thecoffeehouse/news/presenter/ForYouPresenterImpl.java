package com.example.thecoffeehouse.news.presenter;

import com.example.thecoffeehouse.data.model.AppRepositoryNews.AppRespositoryImpNewsPromotion;
import com.example.thecoffeehouse.news.viewnews.ForYouView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForYouPresenterImpl implements ForYouPresenter {
    private ForYouView newsforyou;
    private CompositeDisposable compositeDisposable;
    private AppRespositoryImpNewsPromotion mAppRepository;

    public ForYouPresenterImpl(ForYouView forYouView) {
        mAppRepository = new AppRespositoryImpNewsPromotion(forYouView.getActivity().getApplication());
        this.newsforyou = forYouView;
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void getForYou() {
        Disposable disposable = mAppRepository.getForYou()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsforyou::displayForYou);
                compositeDisposable.add(disposable);
    }
    @Override
    public void loadListNewsFromDatabase() {
        Disposable disposable = mAppRepository.getListNewsFromDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsforyou::displayForYou);
        compositeDisposable.add(disposable);
        mAppRepository.loadApiNewsToDatabase().observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {},throwable -> {});
    }



    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void onStop() {
        compositeDisposable.dispose();
    }

}
