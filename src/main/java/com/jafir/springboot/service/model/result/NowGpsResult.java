package com.jafir.springboot.service.model.result;

import lombok.Data;

/**
 * Created by jafir on 2020/6/12.
 */
@Data
public class NowGpsResult {
    private Long uid;
    private Double lat;
    private Double lng;
    private Long time;
    private String avatar;
    private String name;
}
