package example.grails

import grails.gorm.services.Service

@Service(Restaurant)
interface RestaurantDataService {
    Restaurant get(Long id)
    List<Restaurant> list(Map args)
    Number count()
    void delete(Serializable id)
    Restaurant update(Serializable id, Long version, String name)
    Restaurant save(String name)
}
