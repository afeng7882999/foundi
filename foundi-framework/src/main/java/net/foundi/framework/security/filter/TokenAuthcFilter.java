/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.filter;

import net.foundi.framework.security.model.UserContext;
import net.foundi.framework.security.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 令牌认证过滤器，验证token有效性
 *
 * @author Afeng (afeng7882999@163.com)
*/
@Component
public class TokenAuthcFilter extends OncePerRequestFilter {

   private final TokenService tokenService;

   public TokenAuthcFilter(TokenService tokenService) {
      this.tokenService = tokenService;
   }

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
           throws ServletException, IOException {
      UserContext loginUser = tokenService.getLoginUser(request);
      if (loginUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {
         tokenService.refreshTokenIfNeeded(loginUser);
         UsernamePasswordAuthenticationToken authenticationToken =
                 new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
         authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
         SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
      chain.doFilter(request, response);
   }
}
