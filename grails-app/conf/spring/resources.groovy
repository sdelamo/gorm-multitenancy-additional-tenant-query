import example.cache.TenantAwareCustomCacheKeyGenerator
import example.cache.TenantAwareSimpleKeyGenerator
beans = {
    tenantAwareSimpleKeyGenerator(TenantAwareSimpleKeyGenerator)
    customCacheKeyGenerator(TenantAwareCustomCacheKeyGenerator,
            ref('tenantAwareSimpleKeyGenerator'),
            ref('hibernateDatastore'))
}
