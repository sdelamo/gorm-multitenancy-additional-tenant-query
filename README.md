# Grails Multi-Tenancy Example

This [Grails](https://grails.org) application uses [GORM for Hibernate](http://gorm.grails.org/latest/hibernate/manual/index.html).

It uses `DISCRIMINATOR` multi-tenancy mode by setting at `grails-app/conf/application.yml` the following:

```yaml
grails:
    gorm:
        multiTenancy:
            mode: DISCRIMINATOR
```

It has a domain class `Book` which uses a custom field `tenant` as the tenancy discriminator. 

```groovy
package example

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant

@GrailsCompileStatic
class Book implements MultiTenant<Book> {
    ...
    String tenant
    static constraints = {
...
    static mapping = {
        tenantId name:'tenant'
    }
}
```

In `grails-app/init/BootStrap.groovy` multiple Books are saved. To select the tenant, for which the books are being saved, the method `MultiTenant::withTenant(Serializable, Closure)` is used. 

- For tenant `groovy`, the application saves 5 books
- For tenant `grails`, the application saves 8 books
- For tenant `micronaut`, the application saves 1 books

To save the books, `Boostrap` injects `BookService` a [GORM Data Service](http://gorm.grails.org/latest/hibernate/manual/index.html#dataServices) annotaed with `@CurrentTenant`, an annotation which resolves the current tenant for the context of a class or method. It uses the method `BookService::save(String, String, String, String, String)`

The application exposes following endpoints: 

- GET `/books/groovy` returns books of current tenant `groovy`
- GET `/books/grails` returns books of current tenant `grails`
- GET `/books/micronaut` returns books of current tenant `micronaut`
- GET `/books/groovygrails` returns books of current tenant `groovy` and books of an extra tenant `grails`

To allow queries which fetch current tenant items plus items belonging to other tenant `BookService` extends an `abstract`class `DataService`

```groovy
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
    ...
```

To allow fetching information coming from multiple tenants `DataService<T>` exposes a method: 

`DetachedCriteria<T> criteriaInTenants(String... additionalTenant = null)` 

`DetachedCriteria` allows for dynamic constructions of queries. If you build the criteria with `criteriaInTenant` execute the query within the `Closure` of `Tenants.withoutId(Closure)`. Example:

````groovy
    ...
    Number count(String additionalTenant = null) {
        DetachedCriteria<Book> c = criteriaInTenants(additionalTenant)
        Tenants.withoutId {
            c.count()
        }
    }
    ...
````




