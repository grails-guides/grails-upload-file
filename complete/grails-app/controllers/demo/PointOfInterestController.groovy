package demo

import grails.gorm.transactions.ReadOnly

import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.CREATED
import grails.gorm.transactions.Transactional

@SuppressWarnings('LineLength')
class PointOfInterestController {

    static allowedMethods = [
            save: 'POST',
            update: 'PUT',
            uploadFeaturedImage: 'POST',
            delete: 'DELETE',
    ]

    def uploadPointOfInterestFeaturedImageService

    def pointOfInterestGormService

    // tag::index[]
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def (l, total) = pointOfInterestGormService.list(params)
        respond l, model:[pointOfInterestCount: total]
    }
    // end::index[]

    // tag::editFeaturedImage[]
    @ReadOnly
    def editFeaturedImage(PointOfInterest pointOfInterest) {
        respond pointOfInterest
    }
    // end::editFeaturedImage[]

    // tag::show[]
    @ReadOnly
    def show(PointOfInterest pointOfInterest) {
        respond pointOfInterest
    }
    // end::show[]

    // tag::create[]
    @SuppressWarnings(['GrailsMassAssignment', 'FactoryMethodName'])
    @ReadOnly
    def create() {
        respond new PointOfInterest(params)
    }
    // end::create[]

    // tag::edit[]
    @ReadOnly
    def edit(PointOfInterest pointOfInterest) {
        respond pointOfInterest
    }
    // end::edit[]

    // tag::uploadFeaturedImage[]
    def uploadFeaturedImage(FeaturedImageCommand cmd) {

        if (cmd.hasErrors()) {
            respond(cmd, model: [pointOfInterest: cmd], view: 'editFeaturedImage')
            return
        }

        def pointOfInterest = uploadPointOfInterestFeaturedImageService.uploadFeatureImage(cmd)
        if (pointOfInterest == null) {
            notFound()
            return
        }

        if (pointOfInterest.hasErrors()) {
            respond(pointOfInterest, model: [pointOfInterest: pointOfInterest], view: 'editFeaturedImage')
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'pointOfInterest.label', default: 'Point of Interest'), pointOfInterest.id])
                redirect pointOfInterest
            }
            '*' { respond pointOfInterest, [status: OK] }
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
            respond cmd.errors, model: [pointOfInterest: cmd], view: 'create'
            return
        }

        def pointOfInterest = pointOfInterestGormService.save(cmd)

        if (pointOfInterest == null) {
            notFound()
            return
        }

        if (pointOfInterest.hasErrors()) {
            respond pointOfInterest.errors, model: [pointOfInterest: pointOfInterest], view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'pointOfInterest.label', default: 'PointOfInterest'), pointOfInterest.id])
                redirect pointOfInterest
            }
            '*' { respond pointOfInterest, [status: CREATED] }
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
            respond cmd.errors, model: [pointOfInterest: cmd], view:'edit'
            return
        }

        def pointOfInterest = pointOfInterestGormService.update(cmd)
        if ( !pointOfInterest ) {
            notFound()
            return
        }
        if (pointOfInterest.hasErrors()) {
            respond pointOfInterest, model: [pointOfInterest: pointOfInterest], view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'pointOfInterest.label', default: 'PointOfInterest'), pointOfInterest.id])
                redirect pointOfInterest
            }
            '*' { respond pointOfInterest, [status: OK] }
        }
    }
    // end::update[]

    // tag::delete[]
    def delete() {
        Long pointOfInterestId = params.long('id')
        if ( pointOfInterestId == null) {
            notFound()
            return
        }

        pointOfInterestGormService.deleteById(pointOfInterestId)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'pointOfInterest.label', default: 'PointOfInterest'), pointOfInterestId])
                redirect action: 'index', method: 'GET'
            }
            '*' { render status: NO_CONTENT }
        }
    }
    // end::delete[]

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'pointOfInterest.label', default: 'PointOfInterest'), params.id])
                redirect action: 'index', method: 'GET'
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
