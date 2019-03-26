package productshop.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtilImpl implements ValidatorUtil {
    private Validator validator;

    public ValidatorUtilImpl()
    {
        this.validator= Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Override
    public <E> boolean isValid(E Object) {
        return this.validator.validate(Object).size()==0;
    }

    @Override
    public <E> Set<ConstraintViolation<E>> violations(E Object) {
        return this.validator.validate(Object);
    }
}
