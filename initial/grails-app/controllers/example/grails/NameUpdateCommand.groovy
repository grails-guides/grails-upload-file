package example.grails

import grails.validation.Validateable

class NameUpdateCommand implements Validateable {

    Long id
    Long version
    String name

    static constraints = {
        id nullable: false
        version nullable: false
        name blank: false
    }
}
