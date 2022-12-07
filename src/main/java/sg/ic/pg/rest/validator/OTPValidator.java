package sg.ic.pg.rest.validator;

import sg.ic.pg.rest.model.OTPRequest;
import sg.ic.pg.rest.model.ValidateOTPRequest;

@Validator
public class OTPValidator extends BaseValidator {

    public OTPValidator() {
        // Empty Constructor
    }

    public boolean validate(OTPRequest request) {
        return notNull(request) && validate(request.getMobileCountryCode()) && validate(request.getMobileNo());
    }

    public boolean validate(ValidateOTPRequest request) {
        return notNull(request) && validate(request.getOtp());

    }
}
