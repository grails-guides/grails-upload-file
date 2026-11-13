package example.grails

import grails.testing.web.controllers.ControllerUnitTest
import org.springframework.mock.web.MockMultipartFile
import spock.lang.Specification

class RestaurantControllerSpec extends Specification implements ControllerUnitTest<RestaurantController> {

    void setup() {
        controller.restaurantDataService = Mock(RestaurantDataService)
        controller.crudMessageService = Mock(CrudMessageService)
    }

    void "a valid upload stores the bytes and content type, then redirects to the restaurant"() {
        given:
        byte[] bytes = [1, 2, 3] as byte[]
        Restaurant saved = new Restaurant(name: 'Diner')
        saved.id = 5L
        request.method = 'POST'
        request.addFile(new MockMultipartFile('featuredImageFile', 'photo.png', 'image/png', bytes))
        params.id = 5
        params.version = 0

        when:
        controller.uploadFeaturedImage()

        then:
        1 * controller.restaurantDataService.update(5L, 0L, bytes, 'image/png') >> saved
        response.redirectedUrl == '/restaurant/show/5'
    }

    void "an invalid upload shows the form again without storing anything"() {
        given:
        request.method = 'POST'
        request.addFile(new MockMultipartFile('featuredImageFile', 'notes.txt', 'text/plain', [1] as byte[]))
        params.id = 5
        params.version = 0

        when:
        controller.uploadFeaturedImage()

        then:
        0 * controller.restaurantDataService.update(*_)
        view == 'editFeaturedImage'
        model.restaurant instanceof FeaturedImageCommand
    }

    void "featuredImage renders the stored bytes with their content type"() {
        given:
        byte[] bytes = [9, 8, 7] as byte[]
        controller.restaurantDataService.get(5L) >> new Restaurant(name: 'Diner',
                featuredImageBytes: bytes, featuredImageContentType: 'image/jpeg')

        when:
        controller.featuredImage(5L)

        then:
        response.contentType.startsWith('image/jpeg')
        response.contentAsByteArray == bytes
    }

    void "editFeaturedImage for a missing restaurant redirects to the list"() {
        when:
        controller.editFeaturedImage(404L)

        then:
        response.redirectedUrl == '/restaurant/index'
    }
}
