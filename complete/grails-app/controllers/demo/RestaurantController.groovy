package demo

import grails.gorm.transactions.ReadOnly
import groovy.transform.CompileDynamic
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.CREATED
import grails.transaction.Transactional

@SuppressWarnings('LineLength')
class RestaurantController {

    static allowedMethods = [
            save: 'POST',
            update: 'PUT',
            uploadFeaturedImage: 'POST',
            delete: 'DELETE',
    ]

    def uploadRestaurantFeaturedImageService

    def restaurantGormService

    // tag::featuredImage[]
    @Transactional(readOnly = true)
    def featuredImage(Restaurant restaurant) {
        if (restaurant == null || restaurant.featuredImageBytes == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        render file: restaurant.featuredImageBytes, contentType: restaurant.featuredImageContentType
    }

    // end::featuredImage[]

    // tag::editFeaturedImage[]
    @Transactional(readOnly = true)
    def editFeaturedImage(Restaurant restaurant) {
        respond restaurant
    }

    // end::editFeaturedImage[]

    // tag::index[]
    @ReadOnly
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def (l, total) = restaurantGormService.list(params)
        respond l, model:[restaurantCount: total]
    }

    // end::index[]

    // tag::show[]
    @Transactional(readOnly = true)
    def show(Restaurant restaurant) {
        respond restaurant
    }

    // end::show[]

    // tag::create[]
    @SuppressWarnings(['GrailsMassAssignment', 'FactoryMethodName'])
    @Transactional(readOnly = true)
    def create() {
        respond new Restaurant(params)
    }

    // end::create[]

    // tag::edit[]
    @Transactional(readOnly = true)
    def edit(Restaurant restaurant) {
        respond restaurant
    }

    // end::edit[]

    // tag::uploadFeaturedImage[]
    def uploadFeaturedImage(FeaturedImageCommand cmd) {
        if (cmd == null) {
            notFound()
            return
        }

        if (cmd.hasErrors()) {
            respond(cmd.errors, model: [restaurant: cmd], view: 'editFeaturedImage')
            return
        }

        def restaurant = uploadRestaurantFeaturedImageService.uploadFeatureImage(cmd)

        if (restaurant == null) {
            notFound()
            return
        }

        if (restaurant.hasErrors()) {
            respond(restaurant.errors, model: [restaurant: restaurant], view: 'editFeaturedImage')
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'restaurant.label', default: 'Restaurant'), restaurant.id])
                redirect restaurant
            }
            '*' { respond restaurant, [status: OK] }
        }
    }

    // end::uploadFeaturedImage[]

    // tag::save[]
    def save(NameCommand cmd) {
        if (cmd == null) {
            notFound()
            return
        }

        if (cmd.hasErrors()) {
            respond cmd.errors, model: [restaurant: cmd], view:'create'
            return
        }

        def restaurant = restaurantGormService.save(cmd)

        if (restaurant == null) {
            notFound()
            return
        }

        if (restaurant.hasErrors()) {
            respond restaurant.errors, model: [restaurant: restaurant], view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'restaurant.label', default: 'Restaurant'), restaurant.id])
                redirect restaurant
            }
            '*' { respond restaurant, [status: CREATED] }
        }
    }

    // end::save[]

    // tag::update[]
    def update(NameUpdateCommand cmd) {
        if (cmd == null) {
            notFound()
            return
        }

        if (cmd.hasErrors()) {
            respond restaurant.errors, model: [restaurant: cmd], view:'edit'
            return
        }

        def restaurant = restaurantGormService.update(cmd)

        if (restaurant == null) {
            notFound()
            return
        }

        if (restaurant.hasErrors()) {
            respond restaurant.errors, model: [restaurant: restaurant], view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'restaurant.label', default: 'Restaurant'), restaurant.id])
                redirect restaurant
            }
            '*' { respond restaurant, [status: OK] }
        }
    }

    // end::update[]

    // tag::delete[]
    def delete() {

        Long restaurantId = params.long('id')

        if (restaurantId == null) {
            notFound()
            return
        }

        restaurantGormService.deleteById(restaurantId)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'restaurant.label', default: 'Restaurant'), restaurantId])
                redirect action: 'index', method: 'GET'
            }
            '*' { render status: NO_CONTENT }
        }
    }

    // end::delete[]

    // tag::notFound[]
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'restaurant.label', default: 'Restaurant'), params.id])
                redirect action: 'index', method: 'GET'
            }
            '*' { render status: NOT_FOUND }
        }
    }

    // end::notFound[]
}
