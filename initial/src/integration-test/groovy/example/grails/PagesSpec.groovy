package example.grails

import grails.testing.mixin.integration.Integration
import org.apache.grails.testing.http.client.HttpClientSupport
import spock.lang.Specification
import spock.lang.Unroll

@Integration
class PagesSpec extends Specification implements HttpClientSupport {

    RestaurantDataService restaurantDataService
    PointOfInterestDataService pointOfInterestDataService
    HotelDataService hotelDataService

    @Unroll
    void "the #controller pages render"() {
        given:
        Long id = save(controller)

        expect:
        http("/${controller}/index").assertStatus(200).assertContains('Example')
        http("/${controller}/create").assertStatus(200).assertContains('name="name"')
        http("/${controller}/show/${id}").assertStatus(200).assertContains('Example')
        http("/${controller}/edit/${id}").assertStatus(200).assertContains('name="version"')

        cleanup:
        delete(controller, id)

        where:
        controller << ['restaurant', 'pointOfInterest', 'hotel']
    }

    private Long save(String controller) {
        switch (controller) {
            case 'restaurant' -> restaurantDataService.save('Example').id
            case 'pointOfInterest' -> pointOfInterestDataService.save('Example').id
            default -> hotelDataService.save('Example').id
        }
    }

    private void delete(String controller, Long id) {
        switch (controller) {
            case 'restaurant' -> restaurantDataService.delete(id)
            case 'pointOfInterest' -> pointOfInterestDataService.delete(id)
            default -> hotelDataService.delete(id)
        }
    }
}
