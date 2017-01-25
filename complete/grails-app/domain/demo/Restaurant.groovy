package demo

class Restaurant {
    String name
    byte[] featuredImageBytes
    String featuredImageContentType

    static constraints = {
        featuredImageBytes nullable: true
        featuredImageContentType nullable: true
    }

    static mapping = {
        featuredImageBytes column: "featured_image_bytes", sqlType: "longblob"

    }
}
