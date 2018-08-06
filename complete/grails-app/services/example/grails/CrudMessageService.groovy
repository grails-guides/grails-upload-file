package example.grails

import groovy.transform.CompileStatic
import org.springframework.context.MessageSource

@SuppressWarnings('GrailsStatelessService')
@CompileStatic
class CrudMessageService {

    MessageSource messageSource

    String message(CRUD crud, String domainName, Long domainId, Locale locale) {
        messageSource.getMessage(codeByCrud(crud),
                [domainName, domainId] as Object[],
                defaultMessageByCrud(crud),
                locale)
    }

    String defaultMessageByCrud(CRUD crud) {
        switch (crud) {
            case CRUD.NOT_FOUND:
                'Not Found'
            case CRUD.CREATE:
                'Created'
            case CRUD.UPDATE:
                'Updated'
            case CRUD.DELETE:
                'Deleted'
        }
    }

    String codeByCrud(CRUD crud) {
        switch (crud) {
            case CRUD.NOT_FOUND:
                'default.not.found.message'
            case CRUD.CREATE:
                'default.created.message'
            case CRUD.UPDATE:
                'default.updated.message'
            case CRUD.DELETE:
                'default.deleted.message'
        }
    }
}
