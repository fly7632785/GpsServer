package com.jafir.springboot.web.controller;

import com.jafir.springboot.service.IGpsService;
import com.jafir.springboot.service.impl.SettingService;
import com.jafir.springboot.service.model.Location;
import com.jafir.springboot.service.model.api.ResponseResult;
import com.jafir.springboot.service.model.api.ResponseUtil;
import com.jafir.springboot.service.model.request.GpsHisRequest;
import com.jafir.springboot.service.model.result.NowGpsResult;
import com.jafir.springboot.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jafir on 2018/3/7.
 */
@Controller
public class GpsController extends BaseController {

    @Autowired
    private IGpsService gpsService;
    @Autowired
    private SettingService settingService;


    @RequestMapping(value = "gps", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult gps(@RequestBody Location location, @RequestHeader("token") String token) {
        Long uid = Long.valueOf(JwtUtil.getUserId(token));
        location.setUid(uid);
        gpsService.upload(location);
        return ResponseUtil.makeOK(settingService.getUploadInterval());
    }


    @RequestMapping(value = "/nowGps", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Location> getNowGps(@RequestHeader("token") String token) {
        Long uid = Long.valueOf(JwtUtil.getUserId(token));
        Location location = gpsService.getNowGps(uid);
        if (location != null) {
            return ResponseUtil.makeOK(location);
        }
        return ResponseUtil.makeErr("没有实时数据");
    }

    @RequestMapping(value = "/allNowGps", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<List<NowGpsResult>> getAllNowGps() {
        List<NowGpsResult> location = gpsService.getAllNowGps();
        if (location != null) {
            return ResponseUtil.makeOK(location);
        }
        return ResponseUtil.makeErr("没有实时数据");
    }

    @RequestMapping(value = "/gpsHis", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<List<Location>> getGpsHis(@RequestHeader("token") String token, @RequestBody GpsHisRequest request) {
        if (request.getUid() == null || request.getUid() == 0) {
            Long uid = Long.valueOf(JwtUtil.getUserId(token));
            request.setUid(uid);
        }
        List<Location> location = gpsService.getGpsHis(request);
        if (location != null) {
            return ResponseUtil.makeOK(location);
        }
        return ResponseUtil.makeErr("没有历史数据");
    }

}
