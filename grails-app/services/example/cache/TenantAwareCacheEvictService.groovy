package example.cache

import grails.gorm.multitenancy.Tenants
import grails.plugin.cache.GrailsCache
import groovy.transform.CompileStatic
import org.grails.orm.hibernate.HibernateDatastore
import org.grails.plugin.cache.GrailsCacheManager
import org.springframework.cache.Cache

@CompileStatic
class TenantAwareCacheEvictService  {

    GrailsCacheManager grailsCacheManager

    HibernateDatastore hibernateDatastore

    void evictByCurrentTenant(String tenantId) {
        evictByTenantId(Tenants.currentId(hibernateDatastore)?.toString())
    }

    void evictByTenantId(String tenantId) {
        if (tenantId != null) {
            for (String cacheName : grailsCacheManager.getCacheNames()) {
                Cache cache = grailsCacheManager.getCache(cacheName)
                if (cache instanceof GrailsCache) {
                    GrailsCache grailsCache = (GrailsCache) cache
                    for (Object key : grailsCache.allKeys) {
                        if (key instanceof TemporaryGrailsCacheKey) {
                            TemporaryGrailsCacheKey cacheKey = (TemporaryGrailsCacheKey) key
                            println "Key: " + cacheKey.getSimpleKey() + "tenant " + cacheKey.tenantId
                            if (cacheKey.tenantId == tenantId) {
                                grailsCache.evict(cacheKey)
                            }
                        }
                    }
                }
            }
        }

    }

}