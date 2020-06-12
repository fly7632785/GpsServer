package com.jafir.springboot.service.impl;

import com.jafir.springboot.service.IGpsService;
import com.jafir.springboot.service.dao.LocationMapper;
import com.jafir.springboot.service.model.Location;
import com.jafir.springboot.service.model.request.GpsHisRequest;
import com.jafir.springboot.service.model.result.NowGpsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jafir on 2020/6/8.
 */
@Service
public class GpsService extends BaseService implements IGpsService {
    @Autowired
    LocationMapper locationMapper;

    @Override
    public void upload(Location location) {
        locationMapper.insertSelective(location);
    }

    @Override
    public Location getNowGps(Long uid) {
        return locationMapper.selectByUid(uid);
    }

    @Override
    public List<Location> getGpsHis(GpsHisRequest request) {
        return locationMapper.selectByUidTime(request);
    }

    @Override
    public List<NowGpsResult> getAllNowGps() {
        return locationMapper.getAllNowGps();
    }
}
