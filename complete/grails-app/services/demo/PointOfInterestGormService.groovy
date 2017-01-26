package demo

import grails.transaction.Transactional

class PointOfInterestGormService {

    @Transactional(readOnly = true)
    List list(Map params) {
        [PointOfInterest.list(params), PointOfInterest.count()]
    }

    // tag::updateFeaturedImageUrl[]
    @Transactional
    PointOfInterest updateFeaturedImageUrl(Long pointOfInterestId, Integer version, String featuredImageUrl) {
        PointOfInterest poi = PointOfInterest.get(pointOfInterestId)
        if ( !poi ) {
            return null
        }
        poi.version = version
        poi.featuredImageUrl = featuredImageUrl
        poi.save()
    }
    // end::updateFeaturedImageUrl[]

    @Transactional
    PointOfInterest save(NameCommand cmd) {
        PointOfInterest pointOfInterest = new PointOfInterest()
        pointOfInterest.properties = cmd.properties
        pointOfInterest.save()
    }

    @Transactional
    void deleteById(Long pointOfInterestId) {
        PointOfInterest pointOfInterest = PointOfInterest.get(pointOfInterestId)
        pointOfInterest?.delete()
    }

    @Transactional
    PointOfInterest update(NameUpdateCommand cmd) {
        def poi = PointOfInterest.get(cmd.id)
        if ( !poi ) {
            return null
        }
        poi.properties = cmd.properties
        poi.save()
    }
}
