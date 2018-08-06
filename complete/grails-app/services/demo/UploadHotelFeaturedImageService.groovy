package demo

import grails.gorm.transactions.Transactional

@Transactional
class UploadHotelFeaturedImageService {

    def amazonS3Service

    def hotelGormService

    Hotel uploadFeatureImage(FeaturedImageCommand cmd) {

        def path = "hotel/${cmd.id}/" + cmd.featuredImageFile.originalFilename
        String s3FileUrl = amazonS3Service.storeMultipartFile(path, cmd.featuredImageFile)

        def hotel = hotelGormService.updateFeaturedImageUrl(cmd.id, cmd.version, s3FileUrl)
        if ( !hotel || hotel.hasErrors() ) {
            amazonS3Service.deleteFile(path)
        }
        hotel
    }
}
