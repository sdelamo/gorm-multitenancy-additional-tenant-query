package example.cache

import grails.gorm.multitenancy.Tenants
import grails.plugin.cache.GrailsCacheKeyGenerator
import org.grails.orm.hibernate.HibernateDatastore
import org.springframework.aop.framework.AopProxyUtils
import org.springframework.cache.interceptor.KeyGenerator
import java.lang.reflect.Method

class TenantAwareCustomCacheKeyGenerator implements KeyGenerator, GrailsCacheKeyGenerator {
    private final HibernateDatastore hibernateDatastore
    private final KeyGenerator innerKeyGenerator

    TenantAwareCustomCacheKeyGenerator(KeyGenerator innerKeyGenerator,
                                       HibernateDatastore hibernateDatastore) {
        this.innerKeyGenerator = innerKeyGenerator
        this.hibernateDatastore = hibernateDatastore
    }

    Object generate(Object target, Method method, Object... params) {
        Class<?> objClass = AopProxyUtils.ultimateTargetClass(target)
        return new CacheKey(
                objClass.getName().intern(),
                method.toString().intern(),
                target.hashCode(), innerKeyGenerator.generate(target, method, params))
    }

    @Override
    Serializable generate(String className, String methodName, int objHashCode, Closure keyGenerator) {
        final Object simpleKey = keyGenerator.call()
        Serializable tenantId = Tenants.currentId(hibernateDatastore)
        return new TemporaryGrailsCacheKey(className, methodName, objHashCode, simpleKey, tenantId)
    }

    @Override
    Serializable generate(String className, String methodName, int objHashCode, Map methodParams) {
        final Object simpleKey = methodParams
        Serializable tenantId = Tenants.currentId(hibernateDatastore)
        return new TemporaryGrailsCacheKey(className, methodName, objHashCode, simpleKey, tenantId)
    }



}
