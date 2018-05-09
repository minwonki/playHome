package com.example.wkmin.playhome.list;

import com.example.wkmin.playhome.BasePresenter;
import com.example.wkmin.playhome.BaseView;
import com.example.wkmin.playhome.data.source.network.ItemModel;

public interface ListContract {

    interface Presenter extends BasePresenter {
        void requestNaverApi(String searchText);
    }

    interface View extends BaseView<Presenter> {

        void showItems(ItemModel s);
        void showNoItem();
    }
}
