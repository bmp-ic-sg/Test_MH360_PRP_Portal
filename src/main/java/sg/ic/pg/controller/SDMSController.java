package sg.ic.pg.controller;

import sg.ic.pg.controller.model.SDMSCountriesResponse;
import sg.ic.pg.controller.model.SDMSNationalitiesResponse;
import sg.ic.pg.controller.model.SDMSRequest;
import sg.ic.pg.util.http.HTTPClient;
import sg.ic.pg.util.http.model.HTTPRequest;
import sg.ic.pg.util.http.model.HTTPResponse;
import sg.ic.pg.util.json.JsonHelper;
import sg.ic.pg.util.property.Constant;
import sg.ic.pg.util.property.Property;

@Controller
public class SDMSController extends BaseAPIController{

    public SDMSController() {
        log = getLogger(this.getClass());
    }

    public SDMSCountriesResponse getCountryOfResidence()
    {
        final String methodName = "getCountryOfResidence";
        start(methodName);

        SDMSRequest request = new SDMSRequest();
        request.setVersion(Constant.KEY_VERSION);
        request.setLanguageCode(Constant.KEY_LANGUAGE_CODE);

        logRequest(methodName, request);

        HTTPRequest httpRequest = buildAPIRequest(getProperty(Property.API_RETRIEVE_COUNTRIES_URL));
        log.debug(methodName, "URL : " + httpRequest.getUrl());

        HTTPResponse httpResponse = HTTPClient.post(httpRequest, JsonHelper.toJson(request));

        SDMSCountriesResponse response = JsonHelper.fromJson(httpResponse.getBody(), SDMSCountriesResponse.class);
        logResponse(methodName, response.getResponseCode() +" : "+ response.getResponseMessage());
        logResponse(methodName, "Found : " +response.getCount() +" countries");
        completed(methodName);
        return response;
    }

    public SDMSNationalitiesResponse getNationalities()
    {
        final String methodName = "getNationalities";
        start(methodName);

        SDMSRequest request = new SDMSRequest();
        request.setVersion(Constant.KEY_VERSION);
        request.setLanguageCode(Constant.KEY_LANGUAGE_CODE);

        logRequest(methodName, request);

        HTTPRequest httpRequest = buildAPIRequest(getProperty(Property.API_RETRIEVE_NATIONALITIES_URL));
        log.debug(methodName, "URL : " + httpRequest.getUrl());

        HTTPResponse httpResponse = HTTPClient.post(httpRequest, JsonHelper.toJson(request));

        SDMSNationalitiesResponse response = JsonHelper.fromJson(httpResponse.getBody(), SDMSNationalitiesResponse.class);
        logResponse(methodName, response.getResponseCode() +" : "+ response.getResponseMessage());
        logResponse(methodName, "Found : " +response.getCount() +" nationalities");
        completed(methodName);
        return response;
    }
}
