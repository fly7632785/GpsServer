package com.jafir.springboot.service;

import com.jafir.springboot.service.model.Location;
import com.jafir.springboot.service.model.request.GpsHisRequest;
import com.jafir.springboot.service.model.result.NowGpsResult;

import java.util.List;

/**
 * Created by jafir on 2020/6/8.
 */
public interface IGpsService {
    void upload(Location location);

    Location getNowGps(Long uid);

    List<Location> getGpsHis(GpsHisRequest request);

    List<NowGpsResult> getAllNowGps();
}
