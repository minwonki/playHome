package com.example.wkmin.playhome.data.source.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface NaverApiService {
    @GET("v1/search/blog.json")
    Observable<ItemModel> searchNaver(@Header("X-Naver-Client-Id") String clientId,
                                      @Header("X-Naver-Client-Secret") String clientSecret,
                                      @Query("query") String query);
}
