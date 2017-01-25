package demo

class UploadRestaurantFeaturedImageService {

    def restaurantGormService

    Restaurant uploadFeatureImage(FeaturedImageCommand cmd) {
        byte[] bytes = cmd.featuredImageFile.bytes
        String contentType = cmd.featuredImageFile.contentType
        restaurantGormService.updateRestaurantFeaturedImage(cmd.id, cmd.version, bytes, contentType)
    }
}
