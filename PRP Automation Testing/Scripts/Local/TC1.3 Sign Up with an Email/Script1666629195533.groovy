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

'Story: User decide to create account with an email\r\n\r\nStep:\r\n\t1. Open browser and enter MH360 Signup URL\r\n\t2. Click on "Sign Up with an Email" button\r\n\t3. Page navigated to "Create Account" page\r\n\t4. Enter email address\r\n\t5. Enter password\r\n\t6. Click "Accept and Continue" button\r\n\t7. Page navigated to "Create My Profile" page\r\n\t8. Fill in user information\r\n\t9. Click "Submit" button\r\n\t10. OTP code is generated and has been sent to mobile number\r\n\t11. Page navigated to "Verify OTP" page\r\n\t12. Enter OTP code\r\n\t13. Click "Verify" button\r\n\t14. Success create account\r\n'
WebUI.comment('')

'Open the browser and enter MH360-Signup URL'
WebUI.openBrowser(GlobalVariable.BaseUrl)

'Navigate page to "Create Account" landing page'
WebUI.waitForPageLoad(10)

'Create account landing page'
WebUI.takeFullPageScreenshotAsCheckpoint('manual-prp-landing-page')

'Button "Sign Up with an Email"'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_div_button_sign-up-with-an-Email'))

'Click "Sign Up with an Email" button'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_button_sign-up-with-an-Email'))

//'Loading "Create Account" page'
//WebUI.waitForElementNotPresent(findTestObject('Object Repository/Page_Patient Registration/cover-spin-to-otp'), 3)
'Navigate page to "Create Account" page'
WebUI.waitForPageLoad(10)

WebUI.waitForElementPresent(findTestObject('Page_Patient Registration/manual_email-section'), 10)

'"Create Account" page'
WebUI.takeFullPageScreenshotAsCheckpoint('manual-prp-create-account-page')

WebUI.scrollToElement(findTestObject('Page_Patient Registration/manual_email-section'), 1)

'Find element email address field'
WebUI.focus(findTestObject('Object Repository/Page_Patient Registration/manual_email-section'))

'Email address field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_email-section'))

'Enter Email'
WebUI.setText(findTestObject('Page_Patient Registration/manual_input_email'), GlobalVariable.TestEmail)

WebUI.scrollToElement(findTestObject('Page_Patient Registration/manual_password-section'), 1)

'Find element password field'
WebUI.focus(findTestObject('Object Repository/Page_Patient Registration/manual_password-section'))

'Password field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_password-section'))

'Enter password'
WebUI.setText(findTestObject('Object Repository/Page_Patient Registration/manual_input_password'), GlobalVariable.TestPassword)

WebUI.scrollToElement(findTestObject('Page_Patient Registration/manual_password-meter-section'), 1)

'Find element password meter field'
WebUI.focus(findTestObject('Object Repository/Page_Patient Registration/manual_password-meter-section'))

'Password meter field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_password-meter-section'))

'Find element checkbox1 field'
WebUI.focus(findTestObject('Object Repository/Page_Patient Registration/manual_checkbox1-section'))

'Checkbox1 field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_checkbox1-section'))

'Check checkbox1'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_checkbox1'))

'Find element checkbox2 field'
WebUI.focus(findTestObject('Object Repository/Page_Patient Registration/manual_checkbox2-section'))

'Checkbox2 field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_checkbox2-section'))

'Check checkbox2'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_checkbox2'))

'Scroll to find "Accept and Continue" button'
WebUI.scrollToElement(findTestObject('Object Repository/Page_Patient Registration/manual_button_accept-and-continue'), 1)

WebUI.focus(findTestObject('Page_Patient Registration/manual_button_accept-and-continue'))

'"Accept and Continue" button'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_button_accept-and-continue'))

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

'Navigate page to "Create My Profile" page'
WebUI.waitForPageLoad(3)

'Scroll to find bottom field'
WebUI.waitForElementPresent(findTestObject('Page_Patient Registration/manual_firstname-section'), 10)

'"Create My Profile" page'
WebUI.takeFullPageScreenshotAsCheckpoint('manual-prp-create-my-pofile-page')

'Scroll to find top field'
WebUI.scrollToElement(findTestObject('Object Repository/Page_Patient Registration/manual-input_firstName'), 1)

WebUI.focus(findTestObject('Page_Patient Registration/manual-input_firstName'))

'First name field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_firstname-section'))

'Enter user first name'
WebUI.setText(findTestObject('Object Repository/Page_Patient Registration/manual-input_firstName'), 'Katalon')

WebUI.scrollToElement(findTestObject('Page_Patient Registration/manual_lastname-section'), 1)

WebUI.focus(findTestObject('Page_Patient Registration/manual_lastname-section'))

'Last name field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_lastname-section'))

'Enter user last name'
WebUI.setText(findTestObject('Object Repository/Page_Patient Registration/manual_input_lastName'), 'User')

WebUI.scrollToElement(findTestObject('Page_Patient Registration/manual_gender-section'), 1)

WebUI.focus(findTestObject('Page_Patient Registration/manual_gender-section'))

'Gender field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_gender-section'))

'Click "Female" to select female gender'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_gender-female'))

'Click "Male" to select male gender'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_gender-male'))

WebUI.scrollToElement(findTestObject('Page_Patient Registration/manual_dob-section'), 1)

WebUI.focus(findTestObject('Page_Patient Registration/manual_dob-section'))

'Date of birth field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_dob-section'))

WebUI.scrollToElement(findTestObject('Page_Patient Registration/manual_country-section'), 1)

WebUI.focus(findTestObject('Page_Patient Registration/manual_country-section'))

//'Click to select user date if birth'
//WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_input_dateOfBirth'))
//
//'Select user month of date of birth'
//WebUI.selectOptionByValue(findTestObject('Object Repository/Page_Patient Registration/manual_select_month'), '0', true)
//
//'Select user year of date of birth'
//WebUI.selectOptionByValue(findTestObject('Object Repository/Page_Patient Registration/manual_select_year'), '2021', true)
'Country field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_country-section'))

'Click dropdown to see list of country'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_dropdown_country_selected-flag'))

'Select "Singapore" as user country'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_span_Singapore'))

WebUI.scrollToElement(findTestObject('Object Repository/Page_Patient Registration/manual_nationality-section'), 1)

WebUI.focus(findTestObject('Object Repository/Page_Patient Registration/manual_nationality-section'))

'Nationality field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_nationality-section'))

'Click dropdown to see list of nationality'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_dropdown_nationality_selected-flag'))

'Select "Singaporean" as user nationality'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_span_Singaporean'))

'Click dropdown to select type of id number'
WebUI.selectOptionByValue(findTestObject('Object Repository/Page_Patient Registration/manual_dropdown_select_id_type'), 
    'Passport', true)

WebUI.scrollToElement(findTestObject('Object Repository/Page_Patient Registration/manual_id-section'), 1)

WebUI.focus(findTestObject('Object Repository/Page_Patient Registration/manual_id-section'))

'Id number field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_id-section'))

'Enter id number'
WebUI.setText(findTestObject('Object Repository/Page_Patient Registration/manual_input_id_number'), '1234567')

'Click mobile country code dropdown to see list of mobile country code'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_dropdown_mobile-country-code'))

'Select "Indonesia" as mobile country code'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/manual_li_Indonesia62'))

WebUI.scrollToElement(findTestObject('Object Repository/Page_Patient Registration/manual_mobile-section'), 1)

WebUI.focus(findTestObject('Object Repository/Page_Patient Registration/manual_mobile-section'))

'Mobile number field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_mobile-section'))

'Enter mobile number'
WebUI.setText(findTestObject('Object Repository/Page_Patient Registration/manual_input_mobile_number'), '85747079410')

'Scroll to find "Submit" button'
WebUI.focus(findTestObject('Object Repository/Page_Patient Registration/manual_div_button-submit'), FailureHandling.STOP_ON_FAILURE)

'"Submit" button'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/manual_div_button-submit'))

'Click "Submit" button to process user creation'
WebUI.click(findTestObject('Page_Patient Registration/manual_button_submit'))

'Page navigate to "OTP" page'
WebUI.waitForElementPresent(findTestObject('Page_Patient Registration/logo-mh360-otp-page'), 10)

'"OTP" page'
WebUI.takeScreenshotAsCheckpoint('OTP Page')

'Scroll find "OTP" field'
WebUI.scrollToElement(findTestObject('Page_Patient Registration/OTPInput-area'), 1)

'Focus find "OTP" field'
WebUI.focus(findTestObject('Page_Patient Registration/OTPInput-area'))

'"OTP" field'
WebUI.takeElementScreenshot(findTestObject('Page_Patient Registration/OTPInput-area'))

'Enter "OTP" field'
WebUI.setText(findTestObject('Object Repository/Page_Patient Registration/otp0-otp-page'), '123456')

'Focus find "OTP" button'
WebUI.focus(findTestObject('Page_Patient Registration/submit-otp-button'))

'"OTP" button'
WebUI.takeElementScreenshot(findTestObject('Page_Patient Registration/submit-otp-button'))

'Click "OTP" button to submit otp'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/submit-otp-button'))

'Page navigate to "OTP" success '
WebUI.waitForElementPresent(findTestObject('Page_Patient Registration/icon-success-otp-page'), 10)

'Page "OTP" success'
WebUI.takeFullPageScreenshotAsCheckpoint('OTP page success')

'Page naviigate to "Sign Up" page'
WebUI.waitForElementPresent(findTestObject('Page_MyHealth360 - Login/mh360-image-login-page'), 10)

'"Sign Up" page'
WebUI.takeFullPageScreenshotAsCheckpoint('Sign Up Page')

WebUI.closeBrowser()

