package example.grails

import grails.testing.gorm.DataTest
import spock.lang.Specification
import spock.lang.Unroll

class DomainConstraintsSpec extends Specification implements DataTest {

    void setupSpec() {
        mockDomains(Restaurant, PointOfInterest, Hotel)
    }

    @Unroll
    void "#instance.class.simpleName with name '#instance.name' is #description"() {
        expect:
        instance.validate() == valid
        instance.errors.hasFieldErrors('name') == !valid

        where:
        instance                            || valid
        new Restaurant(name: null)          || false
        new Restaurant(name: '')            || false
        new Restaurant(name: 'Diner')       || true
        new PointOfInterest(name: null)     || false
        new PointOfInterest(name: '')       || false
        new PointOfInterest(name: 'Museum') || true
        new Hotel(name: null)               || false
        new Hotel(name: '')                 || false
        new Hotel(name: 'Grand')            || true

        description = valid ? 'valid' : 'invalid'
    }
}
