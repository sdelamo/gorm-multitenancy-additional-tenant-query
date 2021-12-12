package example

import grails.gorm.DetachedCriteria
import grails.gorm.multitenancy.Tenants
import groovy.transform.CompileStatic
import org.grails.orm.hibernate.HibernateDatastore

import javax.management.Query

@CompileStatic
abstract class DataService<T> {

    abstract HibernateDatastore getHibernateDataStore()

    abstract DetachedCriteria<T> getDetachedCriteria()

    DetachedCriteria<T> criteriaInTenants(String... additionalTenant = null) {
        detachedCriteria
                .in("tenant", resolvedTenantIds(additionalTenant))
    }

    DetachedCriteria<T> criteriaInTenants(String additionalTenant = null, QueryArgs args) {
        criteriaWithArgs(detachedCriteria
                .in("tenant", resolvedTenantIds(additionalTenant)), args)
    }

    List<Serializable> resolvedTenantIds(String... additionalTenant) {
        List<Serializable> resolvedTenantIds = []
        resolvedTenantIds.add(Tenants.currentId(getHibernateDataStore()))
        if (additionalTenant) {
            for (String t : additionalTenant) {
                resolvedTenantIds.add(t)
            }
        }
        resolvedTenantIds
    }

    DetachedCriteria<T> criteriaWithArgs(DetachedCriteria<T> c, QueryArgs args) {
        if (args?.offset?.isPresent()) {
            c = c.offset(args.offset.get())
        }
        if (args?.max?.isPresent()) {
            c = c.max(args.max.get())
        }
        if (args?.order?.isPresent()) {
            c = c.order(args.order.get())
        }
        c
    }
}
