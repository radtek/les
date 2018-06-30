/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.common.filter;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

import org.wxjs.les.common.utils.SpringContextHolder;

/**
 * 页面高速缓存过滤器
 * @author ThinkGem
 * @version 2013-8-5
 */
public class PageCachingFilter extends SimplePageCachingFilter {

	private CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);
	
	@Override
	protected CacheManager getCacheManager() {
		return cacheManager;
	}
	
}
