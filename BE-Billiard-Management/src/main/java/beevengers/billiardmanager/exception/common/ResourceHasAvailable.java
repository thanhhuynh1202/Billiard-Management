package beevengers.billiardmanager.exception.common;

import beevengers.billiardmanager.exception.core.ApplicationException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceHasAvailable extends ApplicationException {
    private String fieldName;
    private Object fieldValue;

    public ResourceHasAvailable(String fieldName, Object fieldValue) {
        super(String.format("Resource has available with %s", fieldName));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }


}