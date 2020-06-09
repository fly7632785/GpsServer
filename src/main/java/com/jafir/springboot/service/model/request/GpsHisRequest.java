package com.jafir.springboot.service.model.request;

import lombok.Data;

/**
 * Created by jafir on 2020/6/8.
 */
@Data
public class GpsHisRequest {
    private Long uid;
    private Long from;
    private Long to;
}
