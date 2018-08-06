package example.grails

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class NameUpdateCommand implements Validateable {
    Long id
    Long version
    String name

    static constraints = {
        id nullable: false
        version nullable: false
        name nullable: false, blank: false
    }
}
