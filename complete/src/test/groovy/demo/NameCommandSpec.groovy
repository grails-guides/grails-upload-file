package demo

import spock.lang.Specification
import spock.lang.Unroll

@SuppressWarnings('MethodName')
class NameCommandSpec extends Specification {

    @Unroll
    void "name is required. #name is #description"() {
        when:
        def cmd = new NameUpdateCommand(name: name)

        then:
        expected == cmd.validate(['name'])
        cmd.errors['name']?.code == code

        where:
        name     | expected | code
        'Hilton' | true     | null
        null     | false    | 'nullable'
        ''       | false    | 'blank'

        description = expected ? 'valid' : "invalid with error code ${code}"
    }
}
