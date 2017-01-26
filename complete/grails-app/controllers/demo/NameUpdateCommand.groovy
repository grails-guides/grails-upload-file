package demo

import grails.validation.Validateable

class NameUpdateCommand implements Validateable {
    Long id
    Integer version
    String name

    static constraints = {
        id nullable: false
        version nullable: false
        name nullable: false, blank: false
    }
}