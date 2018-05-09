package com.example.wkmin.playhome.data.source.Remote;

import com.example.wkmin.playhome.data.source.PlayHomeDataSource;
import com.example.wkmin.playhome.data.source.network.ItemModel;
import com.example.wkmin.playhome.data.source.network.NaverApiService;
import com.example.wkmin.playhome.data.source.network.RetrofitImpl;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlayHomeRemoteDataSource implements PlayHomeDataSource.Remote {

    @SuppressWarnings("FieldCanBeLocal")
    private final String clientId = "ofLV90gIa7UtlWCDJikQ";
    @SuppressWarnings("FieldCanBeLocal")
    private final String clientSecret = "BiQBhg1nAe";

    private static PlayHomeRemoteDataSource INSTANCE;
    private final NaverApiService service;

    private PlayHomeRemoteDataSource() {
        service = RetrofitImpl.getInstance().create(NaverApiService.class);
    }

    public static PlayHomeRemoteDataSource getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PlayHomeRemoteDataSource();

        return INSTANCE;
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Override
    public Observable<ItemModel> requestSearchApi(String searchText) {

        final Observable<ItemModel> observable = service.searchNaver(clientId, clientSecret, searchText);
        Observable<ItemModel> newOb = Observable.create(new ObservableOnSubscribe<ItemModel>() {
            @Override
            public void subscribe(final ObservableEmitter<ItemModel> emitter) {
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ItemModel>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ItemModel itemModel) {
                                emitter.onNext(itemModel);
                            }

                            @Override
                            public void onError(Throwable e) {
                                emitter.onError(e);
                            }

                            @Override
                            public void onComplete() {
                                emitter.onComplete();
                            }
                        });
            }
        });

        return newOb;

    }
}
