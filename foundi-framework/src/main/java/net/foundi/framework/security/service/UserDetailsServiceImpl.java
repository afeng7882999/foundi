package net.foundi.framework.security.service;

import net.foundi.framework.security.enums.AccountStatus;
import net.foundi.framework.security.enums.AuthcType;
import net.foundi.framework.security.exception.SecureException;
import net.foundi.framework.security.model.UserContext;
import net.foundi.framework.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 用户验证处理
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final AccountService accountService;

    public UserDetailsServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 通过用户名获取用户对象
     *
     * @param username 用户名
     * @return UserDetails对象
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserContext user = accountService.getUserContextByUsername(username);

        if (user == null) {
            log.info("登录用户：{} 不存在", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        } else if (AccountStatus.LOCKED.key().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用", username);
            throw new SecureException("对不起，您的账号：" + username + " 已停用");
        }

        user.setAuthcType(AuthcType.PASSWORD);
        return user;
    }
}
