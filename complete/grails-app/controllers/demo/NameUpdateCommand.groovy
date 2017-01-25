package demo

import grails.validation.Validateable

class NameUpdateCommand implements Validateable {
    Long id
    String name

    static constraints = {
        id nullable: false
        name nullable: false, blank: false
    }
}