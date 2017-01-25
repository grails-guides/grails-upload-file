package demo

import grails.transaction.Transactional

class RestaurantGormService {

    @Transactional(readOnly = true)
    List list(Map params) {
        [Restaurant.list(params), Restaurant.count()]
    }

    @Transactional
    Restaurant updateRestaurantFeaturedImage(Long restaurantId, Integer version, byte[] bytes, String contentType) {
        Restaurant restaurant = Restaurant.get(restaurantId)
        restaurant.version = version
        restaurant.featuredImageBytes = bytes
        restaurant.featuredImageContentType = contentType
        restaurant.save()
        restaurant
    }

    @Transactional
    Restaurant save(NameCommand cmd) {
        def restaurant = new Restaurant()
        restaurant.properties = cmd.properties
        restaurant.save()
    }

    @Transactional
    void deleteById(Long restaurantId) {
        def restaurant = Restaurant.get(restaurantId)
        restaurant?.delete()
    }

    @Transactional
    Restaurant update(NameUpdateCommand cmd) {
        def restaurant = Restaurant.get(cmd.id)
        if ( !restaurant ) {
            return null
        }
        restaurant.properties = cmd.properties
        restaurant.save()
    }
}
