package demo

import grails.transaction.Transactional

class RestaurantGormService {

    // tag::list[]
    @Transactional(readOnly = true)
    List list(Map params) {
        [Restaurant.list(params), Restaurant.count()]
    }
    // end::list[]

    // tag::updateRestaurantFeaturedImage[]
    @Transactional
    Restaurant updateRestaurantFeaturedImage(Long restaurantId, Integer version, byte[] bytes, String contentType) {
        Restaurant restaurant = Restaurant.get(restaurantId)
        if ( !restaurant ) {
          return null
        }
        restaurant.version = version
        restaurant.featuredImageBytes = bytes
        restaurant.featuredImageContentType = contentType
        restaurant.save()
        restaurant
    }

    // end::updateRestaurantFeaturedImage[]

    // tag::save[]
    @Transactional
    Restaurant save(NameCommand cmd) {
        def restaurant = new Restaurant()
        restaurant.properties = cmd.properties
        restaurant.save()
    }

    // end::save[]

    // tag::deleteById[]
    @Transactional
    void deleteById(Long restaurantId) {
        def restaurant = Restaurant.get(restaurantId)
        restaurant?.delete()
    }

    // end::deleteById[]

    // tag::update[]
    @Transactional
    Restaurant update(NameUpdateCommand cmd) {
        def restaurant = Restaurant.get(cmd.id)
        if ( !restaurant ) {
            return null
        }
        restaurant.properties = cmd.properties
        restaurant.save()
    }
    // end::update[]
}
