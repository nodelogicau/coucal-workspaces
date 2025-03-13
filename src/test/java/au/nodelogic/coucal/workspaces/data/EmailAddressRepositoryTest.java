package au.nodelogic.coucal.workspaces.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmailAddressRepositoryTest {

    @Autowired
    private EmailAddressRepository repository;

    private EmailAddress emailAddress;

    @BeforeEach
    public void setUp() {
        emailAddress = new EmailAddress();
        emailAddress.setAddress("test@test.com");
        repository.save(emailAddress);
    }

    @AfterEach
    public void tearDown() {
        repository.delete(emailAddress);
    }

    @Test
    void givenEmailAddress_whenSaved_thenCanBeFoundById() {
        EmailAddress address = repository.findById(this.emailAddress.getAddress()).orElse(null);
        assertNotNull(address);
    }
}
