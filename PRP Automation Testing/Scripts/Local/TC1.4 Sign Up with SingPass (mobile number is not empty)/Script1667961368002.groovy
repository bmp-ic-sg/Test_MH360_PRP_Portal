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

'Story: User decide to create account with "Singpass"\r\n\r\nStep :\r\n\t1. Open browser and enter MH360 Signup URL\r\n\t2. Click on "Sign Up with Singpass" button\r\n\t3. Page navigated to Singpass mock user\r\n\t4. Select user nric from dropdown\r\n\t5. Click on "Login" button\r\n\t6. Page navigated to "Consent platform" page\r\n\t7. Click "I agree" button\r\n\t8. Page navigated to MyInfo auth page\r\n\t9. Copy code from MyInfo URL\r\n\t10. Enter PRP callback url "/myinfo/callback?code{code}"\r\n\t11. Page navigated to "Review My Profile" page\r\n\t12. Click "Accept and Continue" button\r\n\t13. Enter email address\r\n\t14. Enter password\r\n\t15. Click "Submit" button\r\n\t16. Success create user with SingPass'
WebUI.comment('')

'Open the browser and enter MH360-Signup URL'
WebUI.openBrowser(GlobalVariable.BaseUrl)

'Navigate page to "Create Account" landing page'
WebUI.waitForPageLoad(3)

'Create account landing page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-create-account-landing-page')

'Button for sign up with SingPass account'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/button_Sign Up with'))

'Click "Sign Up with SingPass" button'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/button_Sign Up with'))

'Navigating to SingPass mock user page'
WebUI.waitForElementNotPresent(findTestObject('Object Repository/Page_Patient Registration/singpass_waiting_mock_user'), 
    5)

'Page navigated to SingPass mock user page'
WebUI.waitForPageLoad(10)

'SingPass mock user page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-mock-user-page')

'Dropdown for select user nric'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Mock pass login/select_S6005048AS6005051AS6005052ZS6005053H_d4bc2e'))

'Select user nric from dropdown'
WebUI.selectOptionByValue(findTestObject('Object Repository/Page_Mock pass login/select_S6005048AS6005051AS6005052ZS6005053H_d4bc2e'), 
    'S6005051A_7bd6ce6b-30f5-8432-4b73-e3843b2594cc', true)

'Button for select user and navigate to "Consent platform" page'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Mock pass login/singpass_button_Login'))

'Click "login" button'
WebUI.click(findTestObject('Object Repository/Page_Mock pass login/singpass_button_Login'))

'Page navigated to "Consent Platform" page'
WebUI.waitForPageLoad(3)

'"Consent Platform" page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-myinfo-consent-platform-page')

'"I Agree" Button for accept MyInfo Consent platform'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Consent Platform/singpass_button_I Agree'))

'Click "I Agree" button'
WebUI.click(findTestObject('Object Repository/Page_Consent Platform/singpass_button_I Agree'))

'If using local and lab environment, MyInfo will return URL \'+WebUI.getUrl()+'
String myInfoAuthurl = WebUI.getUrl()

'Extract code from MyInfo URL'
String code = (myInfoAuthurl.split('=')[1]).split('&')[0]

'Setup code to PRP callback API parameter "/rest/myinfo/callback?code={code}"'
String callbackUrl = (GlobalVariable.BaseUrl + GlobalVariable.MyInfoCallbackAPI) + code

'Navigate back page to PRP'
WebUI.navigateToUrl(callbackUrl)

'Page navigated to "Review My Profile" page'
WebUI.waitForPageLoad(3)

'"Review My Profile" page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-review-myinfo-user-page')

'User info field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/singpass_review_user-info-section'))

'Checkbox1 field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/singpass_checkbox1-section'))

'Check "checkbox1"'
WebUI.click(findTestObject('Object Repository/Page_My Info/singpass_input_Mobile_flexCheckChecked'))

'Checkbox2 field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/singpass_checkbox2-section'))

'Check "checkbox2"'
WebUI.click(findTestObject('Object Repository/Page_My Info/singpass_input_Mobile_flexCheckChecked2'))

'Click "Accept and Continue" button'
WebUI.click(findTestObject('Object Repository/Page_My Info/singpass_button_Accept and Continue'))

'Page navigated to "Create Password" page'
WebUI.waitForPageLoad(3)

'"Create Password" page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-create-password-myinfo-user-page')

'Email address field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/singpass_email-section'))

'Enter email address'
WebUI.setText(findTestObject('Object Repository/Page_My Info/singpass_input_Email_email'), GlobalVariable.TestEmail)

'Password field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/singpass_password-section'))

'Enter password'
WebUI.setText(findTestObject('Object Repository/Page_My Info/singpass_input_Password_password'), GlobalVariable.TestPassword)

'"Submit" Button to process user creation'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/singpass_button_Submit'))

'Click "Submit" button to process user creation'
WebUI.click(findTestObject('Object Repository/Page_My Info/singpass_button_Submit'))

'Verify if email address is not exist, if email is taken, katalon will generate another email address'
if (WebUI.verifyElementPresent(findTestObject('Object Repository/Page_My Info/singpass_message_email_already_exists'), 2, 
    FailureHandling.OPTIONAL)) {
    String indexStr = GlobalVariable.TestEmail.substring(13, 15).trim()

    int index = ((indexStr) as Integer)
q
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
            WebUI.setText(findTestObject('Object Repository/Page_My Info/singpass_input_Email_email'), nextEmail)

            'Click "Accept and Continue" button'
            WebUI.click(findTestObject('Object Repository/Page_My Info/singpass_button_Submit'))

            if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/Page_My Info/singpass_message_email_already_exists'), 
                2, FailureHandling.OPTIONAL)) {
                repeat = false
            }
        }
        
        count++
    }
}

'Singpass prp success create myinfo user'
WebUI.takeFullPageScreenshotAsCheckpoint('singpass-prp-success-create-myinfo-user')

//'Page navigate to "OTP" page'
//WebUI.waitForElementPresent(findTestObject('Object Repository/Page_My Info/img-brand-step1-singpass'), 10)
//
//'"OTP" page'
//WebUI.takeFullPageScreenshotAsCheckpoint('OTP Page singpass')
//
//'Scroll find area "OTP" field'
//WebUI.scrollToElement(findTestObject('Page_My Info/OTPInput-area-singpass'), 1)
//
//'Focus find area "OTP" field'
//WebUI.focus(findTestObject('Page_My Info/OTPInput-area-singpass'))
//
//'"OTP" field'
//WebUI.takeElementScreenshot(findTestObject('Page_My Info/OTPInput-area-singpass'))
//
//'Enter "OTP" field'
//WebUI.setText(findTestObject('Object Repository/Page_My Info/otp0-input-singpass'), '123456')
//
//'Scroll find area "Submit" button'
//WebUI.focus(findTestObject('Page_My Info/submit-otp-singpass'))
//
//'Click "submit" button to verify OTP'
//WebUI.click(findTestObject('Object Repository/Page_My Info/submit-otp-singpass'))
//
//WebUI.waitForElementPresent(findTestObject('Page_My Info/img-modal-success-singpass'), 10)
//
//'Page "OTP" success'
//WebUI.takeFullPageScreenshotAsCheckpoint('Page "OTP" success')
//
//'Page navigate to "Login" page'
//WebUI.waitForElementPresent(findTestObject('Page_MyHealth360 - Login/login-mh360-image-singpass'), 10)
//
//'"Login" page'
//WebUI.takeFullPageScreenshotAsCheckpoint('"Login" page')
WebUI.closeBrowser()

