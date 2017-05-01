package demo

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic

@SuppressWarnings('GrailsStatelessService')
@CompileStatic
class UploadPointOfInterestFeaturedImageService implements GrailsConfigurationAware {

    PointOfInterestGormService pointOfInterestGormService

    String cdnFolder
    String cdnRootUrl

    @Override
    void setConfiguration(Config co) {
        cdnFolder = co.getRequiredProperty('grails.guides.cdnFolder')
        cdnRootUrl = co.getRequiredProperty('grails.guides.cdnRootUrl')
    }

    @SuppressWarnings('JavaIoPackageAccess')
    PointOfInterest uploadFeatureImage(FeaturedImageCommand cmd) {

        String filename = cmd.featuredImageFile.originalFilename
        def folderPath = "${cdnFolder}/pointOfInterest/${cmd.id}" as String
        def folder = new File(folderPath)
        if ( !folder.exists() ) {
            folder.mkdirs()
        }
        def path = "${folderPath}/${filename}" as String
        cmd.featuredImageFile.transferTo(new File(path))

        String featuredImageUrl = "${cdnRootUrl}//pointOfInterest/${cmd.id}/${filename}"

        def poi = pointOfInterestGormService.updateFeaturedImageUrl(cmd.id, cmd.version, featuredImageUrl)

        if ( !poi || poi.hasErrors() ) {
            def f = new File(path)
            f.delete()
        }
        poi
    }
}
