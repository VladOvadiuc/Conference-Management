package ro.ubb.catalog.core.model.validators;

import ro.ubb.catalog.core.model.PCMember;

public class PCMemberValidator implements Validator<PCMember> {
    @Override
    public void validate(PCMember entity) throws UserException
    {
        boolean valid=true;
        valid =
                entity.getWebpage() != "";
        if  (valid==false)
            throw new UserException("Error at the validation of the entity of the PCMember");
    }

}
