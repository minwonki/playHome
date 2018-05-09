package com.example.wkmin.playhome.history;

import com.example.wkmin.playhome.BasePresenter;
import com.example.wkmin.playhome.BaseView;
import com.example.wkmin.playhome.data.HistoryHome;

import io.realm.RealmResults;

public interface HistoryContract {

    interface Presenter extends BasePresenter {
        void getHistory();
    }

    interface View extends BaseView<HistoryContract.Presenter> {
        void showHistory(RealmResults<HistoryHome> historyHomes);
    }
}
