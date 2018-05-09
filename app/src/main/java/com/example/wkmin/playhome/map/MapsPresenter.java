package com.example.wkmin.playhome.map;

import com.example.wkmin.playhome.cmn.Util;
import com.example.wkmin.playhome.data.HistoryHome;
import com.example.wkmin.playhome.data.PlayHome;
import com.example.wkmin.playhome.data.source.PlayHomeRepository;
import com.google.android.gms.maps.model.LatLngBounds;

import io.realm.RealmResults;

public class MapsPresenter implements MapsContract.Presenter {

    private final MapsContract.View view;
    private final PlayHomeRepository repository;

    MapsPresenter(MapsContract.View view, PlayHomeRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    @Override
    public void loadMarker() {
        //repository.removeAll();
        RealmResults<PlayHome> homes =  repository.findAllPlayHome();
        if (homes.size() <= 0) {
            repository.ParsingJSONtoRepository(Util.readJSONFromAssets(view.getContext()));
        }
    }

    @Override
    public void changeMarker(LatLngBounds latLngBounds) {
        RealmResults<PlayHome> homes =  repository.findByLatLngBounds(latLngBounds).findAll();
        if (homes.size() > 200) {
            homes = repository.findAllPlayHome();
            view.addCluster(homes);
        } else {
            view.addMarker(homes);
        }
    }

    @Override
    public void addCluster() {
        RealmResults<PlayHome> homes =  repository.findAllPlayHome();
        view.addCluster(homes);
    }

    @Override
    public void addHistory(PlayHome playHome) {
        HistoryHome historyHome = new HistoryHome();
        historyHome.setName(playHome.getName());
        historyHome.setAddress(playHome.getAddress());
        historyHome.setLatitude(playHome.getLatitude());
        historyHome.setLongitude(playHome.getLongitude());
        repository.addHistory(historyHome);
    }
}
