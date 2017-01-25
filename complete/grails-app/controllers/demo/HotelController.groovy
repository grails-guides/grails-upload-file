package demo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class HotelController {

    static allowedMethods = [save: "POST",
                             update: "PUT",
                             uploadFeaturedImage: "POST",
                             delete: "DELETE"]

    def uploadHotelFeaturedImageService

    def hotelGormService

    // tag::index[]
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def (l, total) = hotelGormService.list(params)
        respond l, model: [hotelCount: total]
    }
    // end::index[]

    // tag::show[]
    @Transactional(readOnly = true)
    def show(Hotel hotel) {
        respond hotel
    }
    // end::show[]

    // tag::create[]
    @Transactional(readOnly = true)
    def create() {
        respond new Hotel(params)
    }
    // end::create[]

    // tag::editFeaturedImage[]
    @Transactional(readOnly = true)
    def editFeaturedImage(Hotel hotel) {
        respond hotel
    }
    // end::editFeaturedImage[]

    // tag::edit[]
    @Transactional(readOnly = true)
    def edit(Hotel hotel) {
        respond hotel
    }
    // end::edit[]

    // tag::uploadFeaturedImage[]
    def uploadFeaturedImage(FeaturedImageCommand cmd) {

        if (cmd.hasErrors()) {
            respond(cmd, model: [hotel: cmd], view: 'editFeaturedImage')
            return
        }

        def hotel = uploadHotelFeaturedImageService.uploadFeatureImage(cmd)
        if (hotel == null) {
            notFound()
            return
        }

        if (hotel.hasErrors()) {
            respond(hotel, model: [hotel: hotel], view: 'editFeaturedImage')
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'hotel.label', default: 'Hotel'), hotel.id])
                redirect hotel
            }
            '*'{ respond hotel, [status: OK] }
        }
    }
    // end::uploadFeaturedImage[]

    // tag::saveAction[]
    def save(NameCommand cmd) {
        if (cmd == null) {
            notFound()
            return
        }

        if (cmd.hasErrors()) {
            respond cmd.errors, model: [hotel: cmd], view:'create'
            return
        }

        def hotel = hotelGormService.save(cmd)

        if ( hotel == null ) {
            notFound()
            return
        }

        if ( hotel.hasErrors() ) {
            respond hotel.errors, model: [hotel: hotel], view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'hotel.label', default: 'Hotel'), hotel.id])
                redirect hotel
            }
            '*' { respond hotel, [status: CREATED] }
        }
    }
    // end::saveAction[]


    // tag::updateAction[]
    @Transactional
    def update(NameUpdateCommand cmd) {
        if (cmd == null) {
            notFound()
            return
        }

        if (cmd.hasErrors()) {
            respond cmd.errors, model: [hotel: cmd], view: 'edit'
            return
        }

        def hotel = hotelGormService.update(cmd)

        if ( hotel == null) {
            notFound()
            return
        }

        if ( hotel.hasErrors() ) {
            respond hotel.errors, model: [hotel: hotel], view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'hotel.label', default: 'Hotel'), hotel.id])
                redirect hotel
            }
            '*'{ respond hotel, [status: OK] }
        }
    }
    // end::updateAction[]

    // tag::delete[]
    def delete() {

        Long hotelId = params.long('id')

        if ( !hotelId ) {
            notFound()
            return
        }

        hotelGormService.deleteById(hotelId)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'hotel.label', default: 'Hotel'), hotelId])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    // tag::delete[]

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'hotel.label', default: 'Hotel'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
