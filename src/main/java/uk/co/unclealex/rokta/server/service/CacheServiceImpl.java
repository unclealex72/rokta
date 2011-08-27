package uk.co.unclealex.rokta.server.service;

import com.googlecode.ehcache.annotations.TriggersRemove;

public class CacheServiceImpl implements CacheService {

	@Override
	@TriggersRemove(cacheName=CACHE_NAME, removeAll=true)
	public void clearCache() {
		// Nothing doing!
	}

}
