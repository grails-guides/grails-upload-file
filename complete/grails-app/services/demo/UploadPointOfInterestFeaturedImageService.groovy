package demo

import grails.config.Config
import grails.core.support.GrailsConfigurationAware

class UploadPointOfInterestFeaturedImageService implements GrailsConfigurationAware {

    String cdnFolder
    String cdnRootUrl

    def pointOfInterestGormService

    @Override
    void setConfiguration(Config co) {
        cdnFolder = co.getRequiredProperty('grails.guides.cdnFolder')
        cdnRootUrl = co.getRequiredProperty('grails.guides.cdnRootUrl')
    }

    PointOfInterest uploadFeatureImage(FeaturedImageCommand cmd) {

        def folder = new File(cdnFolder)
        String filename = cmd.featuredImageFile.originalFilename
        def destinationPath = "${folder}/${filename}" as String
        cmd.featuredImageFile.transferTo(new File(destinationPath))

        String featuredImageUrl = "${cdnRootUrl}/${filename}"

        def poi = pointOfInterestGormService.updateFeaturedImageUrl(cmd.id, cmd.version, featuredImageUrl)

        if ( !poi || poi.hasErrors() ) {
            def f = new File(destinationPath)
            f.delete()
        }
        poi
    }
}
