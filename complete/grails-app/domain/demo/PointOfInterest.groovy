package demo

class PointOfInterest {
    String name
    String featuredImageUrl

    static constraints = {
        featuredImageUrl nullable: true
    }
}
