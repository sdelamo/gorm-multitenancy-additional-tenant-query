package example

import grails.gorm.DetachedCriteria
import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.multitenancy.Tenants
import grails.gorm.services.Service
import groovy.transform.CompileStatic
import org.grails.orm.hibernate.HibernateDatastore
import org.springframework.beans.factory.annotation.Autowired

@CompileStatic
@CurrentTenant
@Service(Book)
abstract class BookService extends DataService<Book> {

    @Autowired
    HibernateDatastore hibernateDatastore

    @Override
    HibernateDatastore getHibernateDataStore() {
        return this.hibernateDatastore
    }

    @Override
    DetachedCriteria<Book> criteriaWithAdditionalTenant(String additionalTenant = null) {
        Book.where {
            tenant in resolvedTenantIds(additionalTenant)
        }
    }

    abstract Book save(String title,  String author,  String about,  String href,  String image)

    List<Book> find(Map args, String additionalTenant = null) {
        DetachedCriteria<Book> c = criteriaWithArgs(criteriaWithAdditionalTenant(additionalTenant), args)
        Tenants.withoutId {
            c.list()
        }
    }

    Number count(String additionalTenant = null) {
        DetachedCriteria<Book> c = criteriaWithAdditionalTenant(additionalTenant)
        Tenants.withoutId {
            c.count()
        }
    }


}