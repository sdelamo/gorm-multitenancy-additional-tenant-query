package example

import grails.gorm.multitenancy.Tenants
import groovy.transform.CompileStatic
import org.grails.datastore.gorm.services.DefaultTenantService
import org.grails.datastore.mapping.model.DatastoreConfigurationException
import org.grails.datastore.mapping.multitenancy.MultiTenancySettings
import org.grails.datastore.mapping.multitenancy.MultiTenantCapableDatastore

@CompileStatic
class ReplacementDefaultTenantService extends DefaultTenantService {

    @Override
    def <T> T withId(Serializable tenantId, Closure<T> callable) {
        MultiTenantCapableDatastore multiTenantCapableDatastore = multiTenantDatastore()
        def mode = multiTenantCapableDatastore.getMultiTenancyMode()
        if(mode != MultiTenancySettings.MultiTenancyMode.NONE) {
            if (tenantId.toString().contains("current")) {
                String newTenantId = tenantId.toString().replaceAll("current",currentId().toString())
                return Tenants.withId(multiTenantCapableDatastore, newTenantId, callable)
            }
            return Tenants.withId(multiTenantCapableDatastore, tenantId, callable)
        }
        else {
            throw new DatastoreConfigurationException("Current datastore [$datastore] is not configured for Multi-Tenancy")
        }
    }
}
