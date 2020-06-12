package com.jafir.springboot.service.dao;

import com.jafir.springboot.service.model.Location;
import com.jafir.springboot.service.model.request.GpsHisRequest;
import com.jafir.springboot.service.model.result.NowGpsResult;

import java.util.List;

public interface LocationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Location record);

    int insertSelective(Location record);

    Location selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Location record);

    int updateByPrimaryKey(Location record);

    List<Location> selectByUidTime(GpsHisRequest request);

    Location selectByUid(Long uid);

    List<NowGpsResult> getAllNowGps();
}