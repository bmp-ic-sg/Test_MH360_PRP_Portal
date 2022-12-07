import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import org.openqa.selenium.Keys as Keys

'Open the browser and enter MH360-Signup URL'
WebUI.openBrowser(GlobalVariable.BaseUrl)

'Click "Sign Up with an Email" button'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_button_sign-up-with-an-Email'))

'Enter Email'
WebUI.setText(findTestObject('Page_Patient Registration/manual_input_email'), GlobalVariable.TestEmail)

'Enter password'
WebUI.setText(findTestObject('Object Repository/Page_Patient Registration/manual_input_password'), GlobalVariable.TestPassword)

'Check checkbox1'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_checkbox1'))

'Check checkbox2'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_checkbox2'))

'Click "Accept and Continue" button'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_button_accept-and-continue'))

'Verify if email address is not exist'
if (WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Patient Registration/manual_email-error-message'), 
    2, FailureHandling.OPTIONAL)) {
    String indexStr = GlobalVariable.TestEmail.substring(13, 15).trim()

    int index = ((indexStr) as Integer)

    int count = 1

    boolean repeat = true

    while (repeat) {
        int nextIndex = index + count

        def nextEmail = ('katalon.user.' + nextIndex) + '@mail.com'

        println(nextEmail)

        if (count > GlobalVariable.MaxUserCreate) {
            KeywordUtil.logInfo('MAX user to create = ' + GlobalVariable.MaxUserCreate)

            KeywordUtil.markFailed('Reach max creating user')

            repeat = false
        } else {
            'Enter email address'
            WebUI.setText(findTestObject('Object Repository/Page_Patient Registration/manual_input_email'), nextEmail)

            'Click "Accept and Continue" button'
            WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_button_accept-and-continue'))

            if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/Page_Patient Registration/manual_email-error-message'), 
                2, FailureHandling.OPTIONAL)) {
                repeat = false
            }
        }
        
        count++
    }
}

'Enter user first name'
WebUI.setText(findTestObject('Object Repository/Page_Patient Registration/manual-input_firstName'), 'Katalon')

'Enter user last name'
WebUI.setText(findTestObject('Object Repository/Page_Patient Registration/manual_input_lastName'), 'User')

'Click "Female" to select female gender'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_gender-female'))

'Click "Male" to select male gender'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_gender-male'))

'Click dropdown to see list of country'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_dropdown_country_selected-flag'))

'Select "Singapore" as user country'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_span_Singapore'))

'Click dropdown to see list of nationality'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_dropdown_nationality_selected-flag'))

'Select "Singaporean" as user nationality'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_span_Singaporean'))

'Click dropdown to select type of id number'
WebUI.selectOptionByValue(findTestObject('Object Repository/Page_Patient Registration/manual_dropdown_select_id_type'), 
    'Passport', true)

'Enter id number'
WebUI.setText(findTestObject('Object Repository/Page_Patient Registration/manual_input_id_number'), '1234567')

'Click mobile country code dropdown to see list of mobile country code'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_dropdown_mobile-country-code'))

'Select "Indonesia" as mobile country code'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_li_Indonesia62'))

'Enter mobile number'
WebUI.setText(findTestObject('Page_Patient Registration/manual_input_mobile_number'), '82293212111')

'Click "Submit" button to process user creation'
WebUI.click(findTestObject('Page_Patient Registration/manual_button_submit'))

