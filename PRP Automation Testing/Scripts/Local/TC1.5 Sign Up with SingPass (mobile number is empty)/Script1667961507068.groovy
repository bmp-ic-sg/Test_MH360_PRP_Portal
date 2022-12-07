import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import internal.GlobalVariable as FailureHandling
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

'Story: User oversea decide to create account with "Singpass"\r\n\r\nStep :\r\n\t1. Open browser and enter MH360 Signup URL\r\n\t2. Click on "Sign Up with Singpass" button\r\n\t3. Page navigated to Singpass mock user\r\n\t4. Select user nric from dropdown\r\n\t5. Click on "Login" button\r\n\t6. Page navigated to "Consent platform" page\r\n\t7. Click "I agree" button\r\n\t8. Page navigated to MyInfo auth page\r\n\t9. Copy code from MyInfo URL\r\n\t10. Enter PRP callback url "/myinfo/callback?code{code}"\r\n\t11. Page navigated to "Review My Profile" page\r\n\t12. Click "Accept and Continue" button\r\n\t13. Enter email address\r\n\t14. Enter password\n\t15. Enter mobile number\r\n\t16. Click "Submit" button\n\t17. Page navigated to OTP page\n\t18. Enter OTP code\n\t19. Click button verify\n\t20. Success create user with SingPass'
WebUI.comment('')

'Open the browser and enter MH360-Signup URL'
WebUI.openBrowser(GlobalVariable.BaseUrl)

'Navigate page to "Create Account" landing page'
WebUI.waitForPageLoad(3)

'Create account landing page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-create-account-landing-page')

'Button for sign up with SingPass account'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/Page_Patient Registration/button_Sign Up with'))

'Click "Sign Up with SingPass" button'
WebUI.click(findTestObject('Object Repository/Page_My Info/Page_Patient Registration/button_Sign Up with'))

'Navigating to SingPass mock user page'
WebUI.waitForElementNotPresent(findTestObject('Object Repository/Page_My Info/Page_Patient Registration/singpass_waiting_mock_user'), 
    5)

'Page navigated to SingPass mock user page'
WebUI.waitForPageLoad(10)

'SingPass mock user page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-mock-user-page')

'Dropdown for select user nric'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/Page_Mock pass login/select_S6005048AS6005051AS6005052ZS6005053H_d4bc2e'))

'Select user nric from dropdown'
WebUI.selectOptionByValue(findTestObject('Object Repository/Page_My Info/Page_Mock pass login/select_S6005048AS6005051AS6005052ZS6005053H_d4bc2e'), 
    'S6005048A_915267f0-5939-0230-78e7-b8cdbaab8518', true)

'Button for select user and navigate to "Consent platform" page'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/Page_Mock pass login/button_Login'))

'Click "login" button'
WebUI.click(findTestObject('Object Repository/Page_My Info/Page_Mock pass login/button_Login'))

'Page navigated to "Consent Platform" page'
WebUI.waitForElementPresent(findTestObject('Page_Consent Platform/button_I Agree'), 10)

'"Consent Platform" page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-myinfo-consent-platform-page')

'"I Agree" Button for accept MyInfo Consent platform'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/Page_Consent Platform/button_I Agree'))

'Click "I Agree" button'
WebUI.click(findTestObject('Object Repository/Page_My Info/Page_Consent Platform/button_I Agree'))

'If using local and lab environment, MyInfo will return URL \'+WebUI.getUrl()+'
String myInfoAuthurl = WebUI.getUrl()

'Extract code from MyInfo URL'
String code = (myInfoAuthurl.split('=')[1]).split('&')[0]

'Setup code to PRP callback API parameter "/rest/myinfo/callback?code={code}"'
String callbackUrl = (GlobalVariable.BaseUrl + GlobalVariable.MyInfoCallbackAPI) + code

'Navigate back page to PRP'
WebUI.navigateToUrl(callbackUrl)

'Page navigated to "Review My Profile" page'
WebUI.waitForElementPresent(findTestObject('Page_My Info/img_Error occured_prp-img-brand-step1'), 10)

'User info page'
WebUI.takeFullPageScreenshotAsCheckpoint('Myinfo page')

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

'"Accept and Continue" button'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/button_Accept and Continue'))

'Click "Accept and Continue" button'
WebUI.click(findTestObject('Object Repository/Page_My Info/button_Accept and Continue'))

'Page navigated to "Create Account" page'
WebUI.waitForElementPresent(findTestObject('Page_My Info/img_Error occured_prp-img-brand-step1'), 10)

'Create Account" page'
WebUI.takeFullPageScreenshotAsCheckpoint('Create Password Page')

'"Email" field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/div_Email'))

'Enter "Email" field'
WebUI.setText(findTestObject('Page_Patient Registration/input_Email_email'), GlobalVariable.TestEmail)

'"Password" field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/div_Password'))

'Enter "Password" field'
WebUI.setEncryptedText(findTestObject('Object Repository/Page_My Info/input_Password_password'), 'iFGeFYmXIrUhQZHvW7P22w==')

WebUI.comment('Dropdown flag number not working')

WebUI.scrollToElement(findTestObject('Page_My Info/div_Mobile No_Singpass'), 1)

'"Phone number" field'
WebUI.takeElementScreenshot(findTestObject('Page_My Info/div_Mobile No_Singpass'))

//WebUI.click(findTestObject('Object Repository/Page_My Info/div_65'))
//
//WebUI.click(findTestObject('Object Repository/Page_My Info/li_Indonesia62'))
'Enter "Phone Number" field'
WebUI.setText(findTestObject('Object Repository/Page_My Info/input_Zimbabwe_phone'), '91234567')

WebUI.waitForElementPresent(findTestObject('Page_My Info/div_Your password should have   At least on_cfd8d5'), 5)

'"Password meter"'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/div_Your password should have   At least on_cfd8d5'))

'"Submit" Button to process user creation'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/button_Submit'))

'Click "Submit" Button to process user creation'
WebUI.click(findTestObject('Object Repository/Page_My Info/button_Submit'))

'Verify if email address is not exist, if email is taken, katalon will generate another email address'
if (WebUI.verifyElementPresent(findTestObject('Object Repository/Page_My Info/singpass_message_email_already_exists'), 2, 
    FailureHandling.OPTIONAL)) {
    String indexStr = GlobalVariable.TestEmail.substring(13, 15).trim()

    println(indexStr)

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

'Page navigated to "OTP" page'
WebUI.waitForElementPresent(findTestObject('Page_My Info/img_Error occured_prp-img-brand-step1'), 10)

'"OTP" page'
WebUI.takeFullPageScreenshotAsCheckpoint('OTP Singpass Page')

'Scroll find area "OTP" field'
WebUI.scrollToElement(findTestObject('Page_My Info/div_A one-time PIN has been sent via SMS to_be2b53'), 1)

'"OTP" field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/div_A one-time PIN has been sent via SMS to_be2b53'))

'Enter "OTP" field'
WebUI.setText(findTestObject('Object Repository/Page_My Info/input_A one-time PIN has been sent via SMS _fd653b'), '123456')

'"varify" button'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_My Info/button_Verify'))

'Click "verify" button to verify OTP code'
WebUI.click(findTestObject('Object Repository/Page_My Info/button_Verify'))

'Page naigated to "Success" page'
WebUI.waitForElementPresent(findTestObject('Page_My Info/img_Allow_prp-img-modal-success'), 5)

'Success Page'
WebUI.takeFullPageScreenshotAsCheckpoint('Success Page')

'Page navigated to "Login" page'
WebUI.waitForElementPresent(findTestObject('Page_MyHealth360 - Login/img_Allow_login-mh360-image'), 10)

'"Login" page'
WebUI.takeFullPageScreenshotAsCheckpoint('Login page')

'Finish'
WebUI.closeBrowser()

