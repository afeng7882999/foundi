/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.xss;

import net.foundi.common.utils.lang.StringUtils;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * XSS过滤Filter
 */
public class XssFilter implements Filter {

	// 排除链接
	public List<String> excludes = new ArrayList<>();

	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public void init(FilterConfig config) {
		String excludesParam = config.getInitParameter("excludes");
		if (StringUtils.hasValue(excludesParam)) {
			excludes = StringUtils.str2List(excludesParam)
					.stream()
					.filter(StringUtils::hasValue)
					.collect(Collectors.toList());
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		// 是否在排除url列表中
		String url = req.getServletPath();
		for (String excludePattern : excludes) {
			if (antPathMatcher.match(excludePattern, url)) {
				chain.doFilter(req, response);
				return;
			}
		}
		// 执行XSS过滤
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(req);
		chain.doFilter(xssRequest, response);
	}

	@Override
	public void destroy() {
	}

}