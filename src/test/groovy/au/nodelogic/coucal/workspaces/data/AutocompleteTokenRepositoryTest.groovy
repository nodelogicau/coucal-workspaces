package au.nodelogic.coucal.workspaces.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class AutocompleteTokenRepositoryTest extends Specification {

    @Autowired
    AutocompleteTokenRepository repository

    def "should save and retrieve autocomplete tokens"() {
        given: "an autocomplete token"
        def token = new AutocompleteToken(fieldName: "testToken", token: "testValue")

        when: "the token is saved"
        repository.save(token)

        then: "it can be retrieved by name"
        def retrievedToken = repository.findByName("testToken")
        retrievedToken.isPresent()
        retrievedToken.get().value == "testValue"
    }
}
