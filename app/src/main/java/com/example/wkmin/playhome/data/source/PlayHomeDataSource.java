package com.example.wkmin.playhome.data.source;

import com.example.wkmin.playhome.data.HistoryHome;
import com.example.wkmin.playhome.data.PlayHome;
import com.example.wkmin.playhome.data.source.network.ItemModel;
import com.google.android.gms.maps.model.LatLngBounds;


import io.reactivex.Observable;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public interface PlayHomeDataSource {

    interface Local {
        RealmResults<PlayHome> findAllPlayHome();
        void removeAll();
        void ParsingJSONtoRepository(String json);
        RealmQuery<PlayHome> findByLatLngBounds(LatLngBounds latLngBounds);

        void addHistory(HistoryHome home);
        RealmResults<HistoryHome> getHistory();
    }

    interface Remote {
        Observable<ItemModel> requestSearchApi(String searchText);
    }
}
