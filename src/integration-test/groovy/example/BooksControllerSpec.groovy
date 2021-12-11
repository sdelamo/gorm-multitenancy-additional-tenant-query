package example

import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import spock.lang.Shared
import spock.lang.Specification
import io.micronaut.http.client.HttpClient
import spock.lang.Unroll

@Integration
class BooksControllerSpec extends Specification {
    @Shared
    HttpClient client

    @OnceBefore
    void init() {
        String baseUrl = "http://localhost:$serverPort"
        this.client = HttpClient.create(baseUrl.toURL())
    }

    @Unroll("GET #uri returns #count books")
    void "test it is possible to retrieve books from current tenant and another tenant"(String uri, int count) {
        expect:
        client.toBlocking().retrieve(uri).contains("Total: $count")

        where:
        uri                   || count
        "/books/groovygrails" || 13
        "/books/micronaut"    || 1
        "/books/grails"       || 8
        "/books/groovy"       || 5
    }
}
