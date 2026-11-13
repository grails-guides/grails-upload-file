package example.grails

import grails.validation.Validateable

class NameCommand implements Validateable {

    String name

    static constraints = {
        name blank: false
    }
}
