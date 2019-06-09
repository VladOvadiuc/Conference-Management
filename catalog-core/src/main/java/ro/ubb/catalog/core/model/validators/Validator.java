package ro.ubb.catalog.core.model.validators;

/**
 * Created by Patricia on 03/02/2019
 **/
/**
 * Interface for generic Validator.
 *
 *
 */
public interface Validator<T> {
    /**
     * Public method for validating a given entity.
     *
     * @param entity-the entity of the validator
     *
     * @throws ValidatorException
     *                  if one of the attributes of the entity does not match the validation
     */
    void validate(T entity) throws ValidatorException;
}
