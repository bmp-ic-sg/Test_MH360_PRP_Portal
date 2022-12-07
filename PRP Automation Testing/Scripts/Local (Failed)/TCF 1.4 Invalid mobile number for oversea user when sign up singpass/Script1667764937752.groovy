import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

'Story: Validate email/mobile number when sign up via singpass.\r\n\r\nStep :\r\n\t1. Open browser and enter MH360 Signup URL\r\n\t2. Click on "Sign Up with Singpass" button\r\n\t3. Page navigated to Singpass mock user\r\n\t4. Select user nric from dropdown\r\n\t5. Click on "Login" button\r\n\t6. Page navigated to "Consent platform" page\r\n\t7. Click "I agree" button\r\n\t8. Page navigated to MyInfo auth page\r\n\t9. Copy code from MyInfo URL\r\n\t10. Enter PRP callback url "/myinfo/callback?code{code}"\r\n\t11. Page navigated to "Review My Profile" page\r\n\t12. Click "Accept and Continue" button\r\n\t13. Enter email address\r\n\t14. Enter password\r\n\t15. Enter mobile number oversea\r\n\t16. Error message invalid mobile number shown'
WebUI.comment('')

'Open the browser and enter MH360-Signup URL'
WebUI.openBrowser(GlobalVariable.BaseUrl)

'Page navigated to "Create Account" landing page'
WebUI.waitForElementPresent(findTestObject('Page_Patient Registration/img_Create an account_prp-img-title'), 5)

'"Create account" landing page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-create-account-landing-page')

'Focus find area "Sign Up with singpass" button'
WebUI.focus(findTestObject('Page_Patient Registration/button_Sign Up with'))

'"Sign Up with singpass" button'
WebUI.takeElementScreenshot(findTestObject('Page_Patient Registration/button_Sign Up with'))

'Click "Sign Up with SingPass" button to create accont with singpass'
WebUI.click(findTestObject('Page_Patient Registration/button_Sign Up with'))

'Navigating to SingPass mock user page'
WebUI.waitForElementPresent(findTestObject('Page_Mock pass login/h2_Mock pass login'), 5)

WebUI.waitForPageLoad(10)

'SingPass mock user page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-mock-user-page')

'Focus find area "user nric" select'
WebUI.focus(findTestObject('Page_Mock pass login/select_S6005048AS6005051AS6005052ZS6005053H_d4bc2e'))

'Dropdown for select user nric'
WebUI.takeElementScreenshot(findTestObject('Page_Mock pass login/select_S6005048AS6005051AS6005052ZS6005053H_d4bc2e'))

'Focus find area "Login" button'
WebUI.focus(findTestObject('Page_Mock pass login/button_Login'))

'Button for select user and navigate to "Consent platform" page'
WebUI.takeElementScreenshot(findTestObject('Page_Mock pass login/button_Login'))

'Click "login" button'
WebUI.click(findTestObject('Page_Mock pass login/button_Login'))

'Page navigated to "Consent Platform" page'
WebUI.waitForElementPresent(findTestObject('Page_Consent Platform/img_This digital service is requesting the _98b49d'), 
    5)

'"Consent Platform" page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-myinfo-consent-platform-page')

'Focus find area "I Agree" button'
WebUI.focus(findTestObject('Page_Consent Platform/button_I Agree'))

'"I Agree" Button for accept MyInfo Consent platform'
WebUI.takeElementScreenshot(findTestObject('Page_Consent Platform/button_I Agree'))

'Click "I Agree" button'
WebUI.click(findTestObject('Page_Consent Platform/button_I Agree'))

'If using local and lab environment, MyInfo will return URL \'+WebUI.getUrl()+'
String myInfoAuthurl = WebUI.getUrl()

'Extract code from MyInfo URL'
String code = (myInfoAuthurl.split('=')[1]).split('&')[0]

'Setup code to PRP callback API parameter "/rest/myinfo/callback?code={code}"'
String callbackUrl = (GlobalVariable.BaseUrl + GlobalVariable.MyInfoCallbackAPI) + code

'Navigate back page to PRP'
WebUI.navigateToUrl(callbackUrl)

'Page navigated to "Review My Profile" page'
WebUI.waitForElementPresent(findTestObject('Page_My Info/img_Error occured_prp-img-brand-step1'), 5)

'"Review My Profile" page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-review-myinfo-user-page')

'Focus find area"Accept and Continue" button'
WebUI.scrollToElement(findTestObject('Page_My Info/button_Accept and Continue'), 1)

'"Accept and Continue" button'
WebUI.takeElementScreenshot(findTestObject('Page_My Info/button_Accept and Continue'))

'Click the "Accept and Continue" button to accept the profile if it is correct'
WebUI.click(findTestObject('Page_My Info/button_Accept and Continue'))

'Page navigated to "Create Password" page'
WebUI.waitForElementPresent(findTestObject('Page_My Info/img_Error occured_prp-img-brand-step1'), 5)

'"Create Password" page'
WebUI.takeFullPageScreenshotAsCheckpoint('Step 2')

WebUI.setText(findTestObject('Page_My Info/input_Email_email'), 'myinfotesting@gmail.com')

WebUI.setEncryptedText(findTestObject('Page_My Info/input_Password_password'), 'iFGeFYmXIrUhQZHvW7P22w==')

'Scroll find area "Mobile no" field'
WebUI.scrollToElement(findTestObject('Page_My Info/div_Mobile No.                             _c46865'), 1)

'"Mobile no" field'
WebUI.takeElementScreenshot(findTestObject('Page_My Info/div_Mobile No.                             _c46865'))

'Enter "Mobile no" field"'
WebUI.setText(findTestObject('Page_My Info/input_Zimbabwe_phone'), '91234567')

'Focus find area "submit" button'
WebUI.focus(findTestObject('Page_My Info/button_Submit'))

'"submit" button'
WebUI.takeElementScreenshot(findTestObject('Page_My Info/button_Submit'))

'Click "submit" button to verify personal details'
WebUI.click(findTestObject('Page_My Info/button_Submit'))

'Page navigated to ""Create Password" Error message page'
WebUI.waitForElementPresent(findTestObject('Page_My Info/div_Email   This email already exists'), 5)

WebUI.scrollToElement(findTestObject('Page_My Info/div_Email   This email already exists'), 1)

WebUI.takeElementScreenshot(findTestObject('Page_My Info/div_Email   This email already exists'))

WebUI.clearText(findTestObject('Page_My Info/input_Zimbabwe_phone'))

'Enter "Mobile no" field"'
WebUI.setText(findTestObject('Page_My Info/input_Zimbabwe_phone'), '82293212606')

'Page navigated to ""Create Password" Error message page'
WebUI.waitForElementPresent(findTestObject('Page_My Info/div_Mobile No._Singpass'), 5)

'Scroll find area "Invalid mobile number" error message'
WebUI.scrollToElement(findTestObject('Page_My Info/div_Mobile No._Singpass'), 1)

'"Invalid mobile number" error message'
WebUI.takeElementScreenshot(findTestObject('Page_My Info/div_Mobile No._Singpass'))

'"Invalid mobile number error message" page'
WebUI.takeFullPageScreenshotAsCheckpoint('Create Password myinfo page')

'Finish'
WebUI.closeBrowser()

