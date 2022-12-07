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

'Story: User decide to create account with "Singpass"\r\n\r\nStep :\r\n\t1. Open browser and enter MH360 Signup URL\r\n\t2. Click on "Sign Up with Singpass" button\r\n\t3. Page navigated to Singpass mock user\r\n\t4. Select user nric from dropdown\r\n\t5. Click on "Login" button\r\n\t6. Page navigated to "Consent platform" page\r\n\t7. Click "I agree" button\r\n\t8. Page navigated to MyInfo auth page\r\n\t9. Copy code from MyInfo URL\r\n\t10. Enter PRP callback url "/myinfo/callback?code{code}"\r\n\tetc'
WebUI.comment('')

'Open the browser and enter MH360-Signup URL'
WebUI.openBrowser(GlobalVariable.BaseUrl)

'Navigate page to "Create Account" landing page'
WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Patient Registration/div_Already have an account  Login now'), 
    3)

'Create account landing page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-create-account-landing-page')

'Button for sign up with SingPass account'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/button_Sign Up with'))

'Click "Sign Up with SingPass" button'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/button_Sign Up with'))

'Page navigated to SingPass mock user page'
WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Mock pass login/div_Mock pass loginSelect userS6005048AS600_3a6567'), 
    3)

'SingPass mock user page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-mock-user-page')

'Dropdown for select user nric'
WebUI.takeElementScreenshot(findTestObject('Page_Mock pass login/select_S6005048AS6005051AS6005052ZS6005053H_d4bc2e'))

'Select user nric from dropdown'
WebUI.selectOptionByValue(findTestObject('Object Repository/Page_Mock pass login/select_S6005048AS6005051AS6005052ZS6005053H_d4bc2e'), 
    'S6005051A_7bd6ce6b-30f5-8432-4b73-e3843b2594cc', true)

'Button login'
WebUI.click(findTestObject('Object Repository/Page_Mock pass login/singpass_button_Login'))

'Page navigated to "Consent Platform" page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-myinfo-consent-platform-page')

'"I Agree" Button for accept MyInfo Consent platform'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Consent Platform/singpass_button_I Agree'))

'Click "I Agree" button'
WebUI.click(findTestObject('Object Repository/Page_Consent Platform/singpass_button_I Agree'))

'if using local and lab environtment, MyInfo will return URL https://uat.parkwaydigihealth.com/amyinfo?code={code}'
def myInfoAuthurl = WebUI.getUrl()

'Get code from MyInfo URL'
def code = (myInfoAuthurl.split('=')[1]).split('&')[0]

'Set code to PRP callback URL "/rest/myinfo/callback?code={code}"'
def callbackUrl = (GlobalVariable.BaseUrl + GlobalVariable.MyInfoCallbackAPI) + code

'Navigate back page to PRP'
WebUI.navigateToUrl(callbackUrl)

WebUI.click(findTestObject('Object Repository/Page_My Info/input_Mobile_flexCheckChecked'))

WebUI.click(findTestObject('Object Repository/Page_My Info/input_Mobile_flexCheckChecked2'))

WebUI.click(findTestObject('Object Repository/Page_My Info/singpass_button_Accept and Continue'))

WebUI.setText(findTestObject('Object Repository/Page_My Info/singpass_input_Email_email'), 'katalon.singpass.2@mail.com')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_My Info/singpass_input_Password_password'), 'iFGeFYmXIrUhQZHvW7P22w==')

//WebUI.click(findTestObject('Object Repository/Page_My Info/singpass_button_Submit'))
//WebUI.verifyElementPresent(findTestObject('Object Repository/Page_My Info/div_Sign-up Successful'), 3)
WebUI.closeBrowser()

