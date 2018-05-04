package com.ljn.buglysimple.api;

import com.ljn.buglysimple.bean.RealmPatientEcgObject;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/05/03
 *     desc   :
 *     modify :
 * </pre>
 */

public interface IApiService {
    @GET("ecgs/")
    Observable<Response<List<RealmPatientEcgObject>>>
    fetchEcgHistory(@Query("patient_huid") String pHuid,
                    @Query("month") String month,
                    @Query("year") String year,
                    @Query("start")String startTime,
                    @Query("end")String endTime);

}
