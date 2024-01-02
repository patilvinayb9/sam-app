package com.edge;

import com.edge.core.config.CoreConstants;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@Configuration
public class CommonBeansConfig {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(CoreConstants.DEFAULT_TIMEZONE));   // It will set UTC timezone
        System.out.println("Spring boot application running in " + CoreConstants.DEFAULT_TIMEZONE + " timezone :" + new Date());   // It will print UTC timezone
    }

}