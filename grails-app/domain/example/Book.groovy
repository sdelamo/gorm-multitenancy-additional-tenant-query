package example

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant

@GrailsCompileStatic
class Book implements MultiTenant<Book> {
    String image
    String title
    String author
    String about
    String href
    String tenant
    static constraints = {
        image nullable: false
        title nullable: false
        author nullable: false
        about nullable: false
        href nullable: false
        about type: 'text'
    }
    static mapping = {
        tenantId name:'tenant'
    }
}
