package example

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.gorm.multitenancy.Tenants
import grails.web.servlet.mvc.GrailsParameterMap
import groovy.transform.CompileStatic

@CompileStatic
class BooksController implements GrailsConfigurationAware {

    BookService bookService

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
        int offset = max * (page - 1)
        Tenants.withId(tenantIds.first()) {
            String additionalTenant = tenantIds.length > 1 ? tenantIds.last() : null
            Number numberOfBooks = bookService.count(additionalTenant)
            List<Book> books = bookService.find([offset: offset, max: max], additionalTenant)
            int totalPages = (int) Math.ceil((numberOfBooks / max) as double)
            List<Page> pages = []
            for (int i = 1; i <= totalPages; i++) {
                pages << new Page(number: i, active: page == i)
            }
            [
                    pages: pages,
                    books: books,
                    count:numberOfBooks,
                    tenantId: tenantIds.join('')
            ] as Map<String, Object>
        }
    }
}
