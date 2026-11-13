package example.grails

class PointOfInterest {

    String name

    static constraints = {
        name nullable: false, blank: false
    }
}
