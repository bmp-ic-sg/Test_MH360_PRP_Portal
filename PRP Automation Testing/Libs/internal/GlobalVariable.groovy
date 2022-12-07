package internal

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.main.TestCaseMain


/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */
public class GlobalVariable {
     
    /**
     * <p>Profile default : Profile name
Profile lab : Profile name
Profile prod : Profile name
Profile uat : Profile name</p>
     */
    public static Object ProfileName
     
    /**
     * <p>Profile default : Local MH360 PRP url
Profile lab : Lab MH360 PRP url
Profile prod : PROD MH360 PRP url
Profile uat : UAT MH360 PRP url</p>
     */
    public static Object BaseUrl
     
    /**
     * <p></p>
     */
    public static Object MyInfoCallbackAPI
     
    /**
     * <p></p>
     */
    public static Object TestEmail
     
    /**
     * <p></p>
     */
    public static Object TestPassword
     
    /**
     * <p></p>
     */
    public static Object MaxUserCreate
     

    static {
        try {
            def selectedVariables = TestCaseMain.getGlobalVariables("default")
			selectedVariables += TestCaseMain.getGlobalVariables(RunConfiguration.getExecutionProfile())
            selectedVariables += TestCaseMain.getParsedValues(RunConfiguration.getOverridingParameters())
    
            ProfileName = selectedVariables['ProfileName']
            BaseUrl = selectedVariables['BaseUrl']
            MyInfoCallbackAPI = selectedVariables['MyInfoCallbackAPI']
            TestEmail = selectedVariables['TestEmail']
            TestPassword = selectedVariables['TestPassword']
            MaxUserCreate = selectedVariables['MaxUserCreate']
            
        } catch (Exception e) {
            TestCaseMain.logGlobalVariableError(e)
        }
    }
}
