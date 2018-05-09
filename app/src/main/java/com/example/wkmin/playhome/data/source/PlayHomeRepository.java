package com.example.wkmin.playhome.data.source;

import com.example.wkmin.playhome.data.HistoryHome;
import com.example.wkmin.playhome.data.PlayHome;
import com.example.wkmin.playhome.data.source.Remote.PlayHomeRemoteDataSource;
import com.example.wkmin.playhome.data.source.local.PlayHomeLocalDataSource;
import com.example.wkmin.playhome.data.source.network.ItemModel;
import com.google.android.gms.maps.model.LatLngBounds;

import io.reactivex.Observable;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class PlayHomeRepository {

    private static PlayHomeRepository INSTANCE = null;
    private final PlayHomeLocalDataSource playHomeLocalDataSource;
    private final PlayHomeRemoteDataSource playHomeRemoteDataSource;

    private PlayHomeRepository() {
        playHomeLocalDataSource = PlayHomeLocalDataSource.getInstance();
        playHomeRemoteDataSource = PlayHomeRemoteDataSource.getInstance();
    }

    public static PlayHomeRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayHomeRepository();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public RealmResults<PlayHome> findAllPlayHome() {
        return playHomeLocalDataSource.findAllPlayHome();
    }

    public Observable<ItemModel> requestSearchApi(String searchText) {
        return playHomeRemoteDataSource.requestSearchApi(searchText);
    }

    @SuppressWarnings("unused")
    public void removeAll() {
        playHomeLocalDataSource.removeAll();
    }

    public void ParsingJSONtoRepository(String json) {
        playHomeLocalDataSource.ParsingJSONtoRepository(json);
    }

    public RealmQuery<PlayHome> findByLatLngBounds(LatLngBounds latLngBounds) {
        return playHomeLocalDataSource.findByLatLngBounds(latLngBounds);
    }

    public void addHistory(HistoryHome historyHome) {
        playHomeLocalDataSource.addHistory(historyHome);
    }

    public RealmResults<HistoryHome> getHistory() {
        return playHomeLocalDataSource.getHistory();
    }

}
