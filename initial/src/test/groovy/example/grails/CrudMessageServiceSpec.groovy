package example.grails

import grails.testing.services.ServiceUnitTest
import org.springframework.context.MessageSource
import spock.lang.Specification
import spock.lang.Unroll

class CrudMessageServiceSpec extends Specification implements ServiceUnitTest<CrudMessageService> {

    @Unroll
    void "#crud uses code #code and default message #defaultMessage"() {
        expect:
        service.codeByCrud(crud) == code
        service.defaultMessageByCrud(crud) == defaultMessage

        where:
        crud           || code                         | defaultMessage
        CRUD.CREATE    || 'default.created.message'    | 'Created'
        CRUD.UPDATE    || 'default.updated.message'    | 'Updated'
        CRUD.DELETE    || 'default.deleted.message'    | 'Deleted'
        CRUD.NOT_FOUND || 'default.not.found.message'  | 'Not Found'
    }

    void "message asks the message source for the code, arguments and default"() {
        given:
        service.messageSource = Mock(MessageSource)

        when:
        String result = service.message(CRUD.UPDATE, 'Hotel', 7L, Locale.ENGLISH)

        then:
        1 * service.messageSource.getMessage('default.updated.message', ['Hotel', 7L] as Object[], 'Updated', Locale.ENGLISH) >> 'Hotel 7 updated'
        result == 'Hotel 7 updated'
    }
}
