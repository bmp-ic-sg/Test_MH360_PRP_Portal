package sg.ic.pg.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MobileVerificationDetailsResponse extends ServiceResponse {

	@JsonProperty("mobileNo")
	private String mobileNo;

	@JsonProperty("mobileCountryCode")
	private String mobileCountryCode;

	public MobileVerificationDetailsResponse() {
		// Empty Constructor
	}

	public MobileVerificationDetailsResponse(String mobileCountryCode, String mobileNo) {
		this.mobileCountryCode = mobileCountryCode;
		this.mobileNo = mobileNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getMobileCountryCode() {
		return mobileCountryCode;
	}

	public void setMobileCountryCode(String mobileCountryCode) {
		this.mobileCountryCode = mobileCountryCode;
	}

}
