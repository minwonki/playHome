package com.example.wkmin.playhome.map;

import android.content.Context;

import com.example.wkmin.playhome.BasePresenter;
import com.example.wkmin.playhome.BaseView;
import com.example.wkmin.playhome.data.PlayHome;
import com.google.android.gms.maps.model.LatLngBounds;

import io.realm.RealmQuery;
import io.realm.RealmResults;

public interface MapsContract {

    interface View extends BaseView<Presenter> {
        Context getContext();
        void addMarker(RealmResults<PlayHome> list);

        void addCluster(RealmResults<PlayHome> homes);
    }

    interface Presenter extends BasePresenter {
        void loadMarker();
        void changeMarker(LatLngBounds latLngBounds);

        void addCluster();

        void addHistory(PlayHome playHome);
    }
}
