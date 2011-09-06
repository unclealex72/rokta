package uk.co.unclealex.rokta.server.service;

import javax.annotation.PostConstruct;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.constructs.blocking.BlockingCache;

import com.googlecode.ehcache.annotations.TriggersRemove;

public class CacheServiceImpl implements CacheService {

	private CacheManager i_ehCacheManager;
	
	@PostConstruct
	public void initialise() {
    CacheManager cacheManager = getEhCacheManager();
		Ehcache cache = cacheManager.getEhcache(CACHE_NAME);
    if (!(cache instanceof BlockingCache)) {
        // decorate and substitute
        BlockingCache newBlockingCache = new BlockingCache(cache);
        cacheManager.replaceCacheWithDecoratedCache(cache, newBlockingCache);
    }
	}
	
	@Override
	@TriggersRemove(cacheName=CACHE_NAME, removeAll=true)
	public void clearCache() {
		// Nothing doing!
	}
	
	public CacheManager getEhCacheManager() {
		return i_ehCacheManager;
	}
	
	public void setEhCacheManager(CacheManager ehCacheManager) {
		i_ehCacheManager = ehCacheManager;
	}

}
