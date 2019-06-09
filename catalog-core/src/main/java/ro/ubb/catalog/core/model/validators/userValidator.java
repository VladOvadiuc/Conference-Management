package ro.ubb.catalog.core.model.validators;


import ro.ubb.catalog.core.model.ConferenceUser;

/**
 * Created by Patricia on 03/02/2019
 **/
public class userValidator implements Validator<ConferenceUser> {
    /**
     * Public method for validating a given ConfferenceUser entity.
     *
     * @param entity-the entity of the userValidator
     *
     * @throws ClientException
     *                  if one of the attributes of the entity does not match the validation
     */
    @Override
    public void validate(ConferenceUser entity) throws UserException {
        boolean valid=true;
        valid =
                entity.getEmail() != "" &&
                entity.getAffiliation() !="" &&
                entity.getName() !="" &&
                entity.getPassword() !="" &&
                entity.getUsername() !="" ;
        if  (valid==false)
            throw new UserException("Error at the validation of the entity of the User");

    }
}
