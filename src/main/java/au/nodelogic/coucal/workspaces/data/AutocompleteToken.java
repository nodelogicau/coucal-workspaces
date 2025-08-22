package au.nodelogic.coucal.workspaces.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Saved tokens for properties that lend themselves to tokenized values.
 *
 * * CATEGORIES
 * * LOCATION
 * * RESOURCES
 * * STATUS
 * * CONTACT
 * * RELATED-TO
 * * ACTION
 * * REFID (RFC9253)
 * * LOCATION-TYPE (RFC9073)`
 * * RESOURCE-TYPE (RFC9073)
 * * FEATURE (RFC7986)
 * * LABEL (RFC7986)
 *
 *
 */
@Entity
public class AutocompleteToken {

    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    private long id;

    private String fieldName;

    private String token;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
