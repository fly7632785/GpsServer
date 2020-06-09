package com.jafir.springboot.listener;

import com.jafir.springboot.util.ApplicationContextUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by jafir on 2020/6/9.
 */
public class MainBusListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContextUtil.setContext(event.getApplicationContext());
    }
}
