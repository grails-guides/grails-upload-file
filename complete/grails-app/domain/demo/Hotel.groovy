package demo

class Hotel {

    String name
    String featuredImageUrl // <1>

    static constraints = {
        featuredImageUrl nullable: true
    }
}
