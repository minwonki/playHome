package com.example.wkmin.playhome.cmn;

import com.example.wkmin.playhome.data.PlayHome;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class PlayHomeClusterItem implements ClusterItem {

    private final LatLng position;
    private final PlayHome home;

    public PlayHomeClusterItem(PlayHome home) {
        this.position = new LatLng(home.getLatitude(), home.getLongitude());
        this.home = home;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public PlayHome getHome() {
        return home;
    }
}
