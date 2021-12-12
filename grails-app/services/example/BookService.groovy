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
    DetachedCriteria<Book> getDetachedCriteria() {
        Book.where {}
    }

    abstract Book save(String title, String author, String about, String href,  String image)

    List<Book> find(QueryArgs args, String additionalTenant = null) {
        DetachedCriteria<Book> c = criteriaInTenants(additionalTenant, args)
        Tenants.withoutId {
            c.list()
        }
    }

    Number count(String additionalTenant = null) {
        DetachedCriteria<Book> c = criteriaInTenants(additionalTenant)
        Tenants.withoutId {
            c.count()
        }
    }
}