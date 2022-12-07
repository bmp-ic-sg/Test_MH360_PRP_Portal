package sg.ic.pg.rest.validator;

import sg.ic.pg.model.User;
import sg.ic.pg.rest.model.ValidateEmailRequest;
import sg.ic.pg.rest.model.ValidateMoEngageRequest;
import sg.ic.pg.util.property.Constant;

@Validator
public class UserValidator extends BaseValidator {


    public UserValidator() {
        // Empty Constructor
    }

    public boolean validate(User user) {
        return notNull(user) && validate(user.getFirstName()) && validate(user.getDob())
                && validate(user.getMobileNo()) && validate(user.getMobileCountryCode())
                && validate(user.getEmail()) && validate(user.getPassword()) && validatePassword(user.getPassword())
                && validate(user.getId()) && validate(user.getGender()) && validate(user.getToken())
                && user.getLastName() != null && !user.getLastName().isEmpty(); // Special handling for lastName to accommodate the empty space `lastName` for MyInfo integration, DO NOT CHANGE TO .validate(String) !!!
    }

    public boolean validate(ValidateEmailRequest request) {
        return notNull(request) && validate(request.getEmail());
    }

    public boolean validate(ValidateMoEngageRequest request) {
        return notNull(request) && validate(request.getEmail());
    }

    public boolean validatePassword(String password) {
        return password.matches(Constant.PASSWORD_REGEX);

    }
}
