package example.grails

class Hotel {

    String name

    static constraints = {
        name nullable: false, blank: false
    }
}
