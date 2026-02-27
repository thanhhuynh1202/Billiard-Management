package beevengers.billiardmanager.exception.common;

import beevengers.billiardmanager.exception.core.ApplicationException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends ApplicationException {
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String fieldName, Object fieldValue) {
        super(String.format("Resource not found with %s : '%s'", fieldName, fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
