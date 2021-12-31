/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin;

import net.foundi.common.constant.FoundiConst;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * foundi admin
 *
 * @author Afeng (afeng7882999@163.com)
*/
@SpringBootApplication
@ComponentScan("net.foundi.*")
public class FoundiAdminApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FoundiAdminApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    @PostConstruct
    void started() {
        // set default TimeZone
        TimeZone.setDefault(TimeZone.getTimeZone(FoundiConst.DEFAULT_TIMEZONE));
    }
}
