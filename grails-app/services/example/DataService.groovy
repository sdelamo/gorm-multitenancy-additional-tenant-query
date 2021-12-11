package example

import grails.gorm.DetachedCriteria
import grails.gorm.multitenancy.Tenants
import org.grails.orm.hibernate.HibernateDatastore

abstract class DataService<T> {

    abstract HibernateDatastore getHibernateDataStore()

    abstract DetachedCriteria<T> criteriaWithAdditionalTenant(String additionalTenant = null)

    DetachedCriteria<T> criteriaWithArgs(DetachedCriteria<T> c, Map args) {
        if (args?.offset) {
            c = c.offset(args.offset as int)
        }
        if (args?.max) {
            c = c.max(args.max as int)
        }
        c
    }

    List<Serializable> resolvedTenantIds(String additionalTenant) {
        List<Serializable> resolvedTenantIds = []
        resolvedTenantIds.add(Tenants.currentId(getHibernateDataStore()))
        if (additionalTenant) {
            resolvedTenantIds.add(additionalTenant)
        }
        resolvedTenantIds
    }
}
