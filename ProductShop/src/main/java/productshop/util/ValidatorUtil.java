package productshop.util;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidatorUtil {

    <E> boolean isValid (E Object);

    <E>Set<ConstraintViolation<E>> violations (E Object);
}
