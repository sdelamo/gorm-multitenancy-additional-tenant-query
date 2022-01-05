package example

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.plugin.cache.Cacheable
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@Slf4j
@CompileStatic
class BookListService implements GrailsConfigurationAware {

    int max

    @Override
    void setConfiguration(Config co) {
        max = co.getProperty('books.page.max', Integer, 5)
    }

    BookService bookService

    @Cacheable(value = "books", key = { args.toString() + (additionalTenant ?: '') })
    BookPage find(QueryArgs args, String additionalTenant) {
        log.info("not cached")
        int max = (args.max.isPresent() ? args.max.get() : max)
        int offset = args.offset.isPresent() ? args.offset.get() : 0
        int page = offset == 0 ? 1 : Math.ceil((offset / max) as double).toInteger() + 1
        Number numberOfBooks = bookService.count(additionalTenant)
        List<Book> books = bookService.find(args, additionalTenant)
        int totalPages = (int) Math.ceil((numberOfBooks / max) as double)
        List<Page> pages = []
        for (int i = 1; i <= totalPages; i++) {
            pages << new Page(number: i, active: page == i)
        }
        new BookPage(pages: pages, books: books, count: numberOfBooks)
    }

}