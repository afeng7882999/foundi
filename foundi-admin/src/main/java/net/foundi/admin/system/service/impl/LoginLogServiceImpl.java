/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.foundi.admin.system.dao.LoginLogDao;
import net.foundi.admin.system.entity.domain.LoginLogDo;
import net.foundi.admin.system.service.LoginLogService;
import net.foundi.framework.entity.query.Query;
import net.foundi.framework.security.enums.AuthcType;
import net.foundi.framework.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
* 系统访问日志Service
*
* @author Afeng
*/
@Service
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLogDao, LoginLogDo> implements LoginLogService {

    @Override
    public IPage<LoginLogDo> page(Page<LoginLogDo> page, Query query) {
        if (page != null) {
            List<OrderItem> orderItems = page.orders();
            if (orderItems.stream().noneMatch(i -> LoginLogDo.ColOperTime.equals(i.getColumn()))) {
                page.addOrder(OrderItem.desc(LoginLogDo.ColOperTime));
            }
        }
        return super.page(page, query);
    }

    @Override
    public void logLogin(String username, String ip, String location, String browser, String os, String message,
                         AuthcType authcType, String type, String status, LocalDateTime operTime) {
        LoginLogDo aDo = new LoginLogDo();
        aDo.setUserName(username);
        aDo.setIp(ip);
        aDo.setLocation(location);
        aDo.setBrowser(browser);
        aDo.setOs(os);
        aDo.setMessage(message);
        aDo.setTypeDict(type);
        aDo.setStatusDict(status);
        aDo.setOperTime(operTime);
        if (authcType != null) {
            aDo.setAuthcTypeDict(authcType.key());
        }
        this.save(aDo);
    }
}