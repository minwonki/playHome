package com.example.wkmin.playhome.list;

import com.example.wkmin.playhome.data.source.PlayHomeRepository;
import com.example.wkmin.playhome.data.source.network.ItemModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListPresenter implements ListContract.Presenter {

    private final ListContract.View view;
    private final PlayHomeRepository repository;

    ListPresenter(ListContract.View view, PlayHomeRepository repository) {
        this.view = view;
        this.repository = repository;
        view.setPresenter(this);
    }

    @Override
    public void requestNaverApi(String searchText) {

        repository.requestSearchApi(searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ItemModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ItemModel itemModel) {
                        if (itemModel.getItems().size() > 0)
                            view.showItems(itemModel);
                        else
                            view.showNoItem();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
