package example.cache

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class EvictController {
    TenantAwareCacheEvictService tenantAwareCacheEvictService

    def grails() {
        tenantAwareCacheEvictService.evictByTenantId('grails')
        redirect controller: 'books', action: 'index'
    }

    def micronaut() {
        tenantAwareCacheEvictService.evictByTenantId('micronaut')
        redirect controller: 'books', action: 'index'
    }

    def groovy() {
        tenantAwareCacheEvictService.evictByTenantId('groovy')
        redirect controller: 'books', action: 'index'
    }


}