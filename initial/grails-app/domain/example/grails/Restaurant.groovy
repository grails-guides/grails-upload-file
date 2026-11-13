package example.grails

class Restaurant {

    String name

    static constraints = {
        name nullable: false, blank: false
    }
}
