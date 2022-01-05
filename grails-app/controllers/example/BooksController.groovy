package example

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.gorm.multitenancy.Tenants
import grails.web.servlet.mvc.GrailsParameterMap
import groovy.transform.CompileStatic

@CompileStatic
class BooksController implements GrailsConfigurationAware {

    BookListService bookListService

    int max

    @Override
    void setConfiguration(Config co) {
        max = co.getProperty('books.page.max', Integer, 5)
    }

    def index() {
    }

    def grails() {
        render view: 'books', model: modelByTenantId(params, 'grails')
    }

    def micronaut() {
        render view: 'books', model: modelByTenantId(params, 'micronaut')
    }

    def groovy() {
        render view: 'books', model:  modelByTenantId(params, 'groovy')
    }

    def groovygrails() {
        render view: 'books', model:  modelByTenantId(params, 'groovy', 'grails')
    }

    private Map<String, Object> modelByTenantId(GrailsParameterMap params, String... tenantIds) {
        int page = params.int("page", 1)
        String sortPropertyName = params.get("sort")?.toString()
        String direction = params.get("order")?.toString()
        int offset = max * (page - 1)
        QueryArgs args = QueryArgs.builder()
                .offset(offset)
                .max(max)
                .order(QueryArgs.orderOf(sortPropertyName, direction))
                .build()
        Tenants.withId(tenantIds.first()) {
            String additionalTenant = tenantIds.length > 1 ? tenantIds.last() : null
            BookPage bookPage = bookListService.find(args, additionalTenant)
            [
                    pages: bookPage.pages,
                    books: bookPage.books,
                    count: bookPage.count,
                    tenantId: tenantIds.join('')
            ] as Map<String, Object>
        }
    }
}
