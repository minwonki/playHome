package com.example.wkmin.playhome.data.source.local;

import com.example.wkmin.playhome.data.HistoryHome;
import com.example.wkmin.playhome.data.PlayHome;
import com.example.wkmin.playhome.data.PlayHomeModel;
import com.example.wkmin.playhome.data.source.PlayHomeDataSource;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class PlayHomeLocalDataSource implements PlayHomeDataSource.Local {

    private static PlayHomeLocalDataSource INSTANCE;
    private final Realm realm;

    private PlayHomeLocalDataSource() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public static PlayHomeLocalDataSource getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PlayHomeLocalDataSource();

        return INSTANCE;
    }

    private void addHome(final PlayHome playHome) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(PlayHome.class).max("id");
                long pk = (currentIdNum != null) ? currentIdNum.intValue() + 1 : 1;
                playHome.setId(pk);
                realm.copyToRealmOrUpdate(playHome);
            }
        });
    }

    @Override
    public RealmResults<PlayHome> findAllPlayHome() {
        return realm.where(PlayHome.class).findAll();
    }

    @Override
    public void removeAll() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    @Override
    public void ParsingJSONtoRepository(String json) {
        Gson gson = new Gson();
        PlayHomeModel[] playHomeModel = gson.fromJson(json, PlayHomeModel[].class);
        for (PlayHomeModel home : playHomeModel) {
            PlayHome playHome = new PlayHome();

            playHome.setName(home.getName());
            playHome.setStarCount(home.getStarCount());
            playHome.setImageUrl(home.getPrimaryImage());
            playHome.setThumbnailUrl(home.getPrimaryImageThumbnail());
            playHome.setStarAvg(home.getStarScoreAvg());
            playHome.setAddress(home.getFormattedAddress());
            playHome.setTags(home.getTagNames());
            playHome.setLatitude(home.getLatitude());
            playHome.setLongitude(home.getLongitude());

            this.addHome(playHome);
        }
    }

    @Override
    public RealmQuery<PlayHome> findByLatLngBounds(LatLngBounds latLngBounds) {
        return realm.where(PlayHome.class).
                between("longitude",
                        latLngBounds.southwest.longitude,
                        latLngBounds.northeast.longitude).
                between("latitude",
                        latLngBounds.southwest.latitude,
                        latLngBounds.northeast.latitude);
    }

    @Override
    public void addHistory(final HistoryHome home) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(HistoryHome.class).max("id");
                long pk = (currentIdNum != null) ? currentIdNum.intValue() + 1 : 1;
                home.setId(pk);
                realm.copyToRealmOrUpdate(home);
            }
        });
    }

    @Override
    public RealmResults<HistoryHome> getHistory() {
        RealmResults<HistoryHome> resultsAll = realm.where(HistoryHome.class).findAll();
        return realm.where(HistoryHome.class)
                .between("id", resultsAll.size() - 50, resultsAll.size())
                .findAllSorted("id", Sort.DESCENDING);
    }
}
