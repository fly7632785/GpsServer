package com.jafir.springboot.service.impl;

import com.jafir.springboot.service.ISettingService;
import com.jafir.springboot.service.dao.SettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jafir on 2018/3/7.
 */
@Service
public class SettingService extends BaseService implements ISettingService {

    @Autowired
    SettingMapper settingMapper;

    @Override
    public Long getUploadInterval() {
        return settingMapper.selectByPrimaryKey(1) == null ? 5000 : settingMapper.selectByPrimaryKey(1).getUploadiInterval();
    }
}
