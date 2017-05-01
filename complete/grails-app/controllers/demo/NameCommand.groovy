package demo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class NameCommand implements Validateable {
    String name

    static constraints = {
        name nullable: false, blank: false
    }
}
