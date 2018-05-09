package com.example.wkmin.playhome.history;

import com.example.wkmin.playhome.data.HistoryHome;
import com.example.wkmin.playhome.data.source.PlayHomeRepository;

import io.realm.RealmResults;

class HistoryPresenter implements HistoryContract.Presenter {

    private final HistoryContract.View view;
    private final PlayHomeRepository repository;

    HistoryPresenter(HistoryContract.View view, PlayHomeRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    @Override
    public void getHistory() {
        RealmResults<HistoryHome> historyHomes = repository.getHistory();
        view.showHistory(historyHomes);
    }
}
