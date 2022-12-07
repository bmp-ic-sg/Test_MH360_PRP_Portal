package sg.ic.pg.rest.service;

import sg.ic.pg.controller.SDMSController;
import sg.ic.pg.controller.model.SDMSCountriesResponse;
import sg.ic.pg.controller.model.SDMSCountry;
import sg.ic.pg.controller.model.SDMSNationalitiesResponse;
import sg.ic.pg.controller.model.SDMSNationality;
import sg.ic.pg.rest.model.CountryResponse;
import sg.ic.pg.rest.model.NationalityResponse;
import sg.ic.pg.rest.service.BaseService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("sdms")
@Singleton
public class SdmsService extends BaseService {

    @Inject
    private SDMSController sdmsController;

    public SdmsService() {
        log = getLogger(this.getClass());
    }

    @GET
    @Path("countries")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountries() {
        final String methodName = "getCountries";
        Response response;
        start(methodName);

        SDMSCountriesResponse sdmsResponse = sdmsController.getCountryOfResidence();
        List<CountryResponse> countries = convertCountries(sdmsResponse.getCountries());

        log.debug(methodName, "result : " + countries.size());
        response = buildSuccessResponse(countries);

        completed(methodName);
        return response;
    }

    @GET
    @Path("nationalities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNationalities() {
        final String methodName = "getNationalities";
        Response response;
        start(methodName);

        SDMSNationalitiesResponse sdmsResponse = sdmsController.getNationalities();
        List<NationalityResponse> nationalities = convertNationalities(sdmsResponse.getNationalities());

        log.debug(methodName, "result : " + nationalities.size());
        response = buildSuccessResponse(nationalities);

        completed(methodName);
        return response;
    }

    private  List<CountryResponse> convertCountries(List<SDMSCountry> sdmsCountries) {
        List<CountryResponse> countries = new ArrayList<>();
        for (SDMSCountry sdmsCountry : sdmsCountries) {
            CountryResponse countryResponse = new CountryResponse();
            countryResponse.setCor(sdmsCountry.getCor());
            countryResponse.setCountryName(sdmsCountry.getCountryName());
            countryResponse.setIsoCode(sdmsCountry.getIsoCode());
            countryResponse.setFlagImageUrl(sdmsCountry.getFlagImageUrl());
            countryResponse.setCountryCode(sdmsCountry.getCountryCode());
            countries.add(countryResponse);
        }
        return countries;
    }

    private  List<NationalityResponse> convertNationalities(List<SDMSNationality> sdmsNationalities) {
        List<NationalityResponse> nationalities = new ArrayList<>();
        for (SDMSNationality sdmsNationality : sdmsNationalities) {
            NationalityResponse nationalityResponse = new NationalityResponse();

            nationalityResponse.setNationality(sdmsNationality.getNationality());
            nationalityResponse.setIsoCode(sdmsNationality.getIsoCode());
            nationalityResponse.setFlagImageUrl(sdmsNationality.getFlagImageUrl());
            nationalities.add(nationalityResponse);
        }
        return nationalities;
    }
}
