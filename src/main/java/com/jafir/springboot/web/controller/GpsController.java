package com.jafir.springboot.web.controller;

import com.jafir.springboot.service.IGpsService;
import com.jafir.springboot.service.model.Location;
import com.jafir.springboot.service.model.api.ResponseResult;
import com.jafir.springboot.service.model.api.ResponseUtil;
import com.jafir.springboot.service.model.request.GpsHisRequest;
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


    @RequestMapping(value = "gps", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult gps(@RequestBody Location location) {
        gpsService.upload(location);
        return ResponseUtil.makeOK();
    }


    @RequestMapping(value = "/nowGps", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Location> getNowGps(@RequestParam Long uid) {
        Location location = gpsService.getNowGps(uid);
        if (location != null) {
            return ResponseUtil.makeOK(location);
        }
        return ResponseUtil.makeErr("没有实时数据");
    }

    @RequestMapping(value = "/gpsHis", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<List<Location>> getGpsHis(@RequestBody GpsHisRequest request) {
        List<Location> location = gpsService.getGpsHis(request);
        if (location != null) {
            return ResponseUtil.makeOK(location);
        }
        return ResponseUtil.makeErr("没有历史数据");
    }

}
