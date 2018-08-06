package demo

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class HotelGormService {

    @ReadOnly
    List list(Map params) {
        [Hotel.list(params), Hotel.count()]
    }

    // tag::updateFeaturedImageUrl[]
    @Transactional
    Hotel updateFeaturedImageUrl(Long id, Integer version, String featuredImageUrl) {
        Hotel hotel = Hotel.get(id)
        if ( !hotel ) {
            return null
        }
        hotel.version = version
        hotel.featuredImageUrl = featuredImageUrl
        hotel.save()
    }
    // end::updateFeaturedImageUrl[]

    @Transactional
    Hotel save(NameCommand cmd) {
        def hotel = new Hotel()
        hotel.properties = cmd.properties
        hotel.save()
    }

    @Transactional
    Hotel update(NameUpdateCommand cmd) {
        Hotel hotel = Hotel.get(cmd.id)
        hotel.properties = cmd.properties
        hotel.save()
    }

    @Transactional
    void deleteById(Long hotelId) {
        def hotel = Hotel.get(hotelId)
        hotel?.delete()
    }
}
