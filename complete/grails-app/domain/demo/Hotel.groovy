package demo

class Hotel {

    String name
    String featuredImageUrl

    static constraints = {
        featuredImageUrl nullable: true
    }
}
