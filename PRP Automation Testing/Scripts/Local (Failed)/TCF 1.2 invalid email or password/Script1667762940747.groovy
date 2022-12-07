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

'Story: Invalid email or password\r\n\r\nStep:\r\n\t1. Open browser and enter MH360 Signup URL\r\n\t2. Click on "Sign Up with an Email" button\r\n\t3. Page navigated to "Create Account" page\r\n\t4. Enter wrong email address format\r\n\t5. Enter password\r\n\t6. Click "Accept and Continue" button\n\t7. Invalid email or password error message is shown\r\n'
WebUI.comment('')

'Open the browser and enter MH360-Signup URL'
WebUI.openBrowser(GlobalVariable.BaseUrl)

'Page navigated to "Create Account" Landing page'
WebUI.waitForElementPresent(findTestObject('Page_Patient Registration/img_Allow_prp-img-title'), 5)

'"Create account" Landing page'
WebUI.takeFullPageScreenshotAsCheckpoint('Create account landing page')

'Scroll find area "SignUp with an email" button'
WebUI.scrollToElement(findTestObject('Page_Patient Registration/button_Sign Up with an Email'), 1)

'"SignUp with an email" button'
WebUI.takeElementScreenshot(findTestObject('Page_Patient Registration/button_Sign Up with an Email'))

'Click "Sign Up with an email" button to create account with email'
WebUI.click(findTestObject('Page_Patient Registration/a_Sign Up with an Email'))

'Page navigated to "Create Account" page'
WebUI.waitForElementPresent(findTestObject('Page_Patient Registration/img_Error occured_logo-mh360'), 5)

'"Create account" page'
WebUI.takeFullPageScreenshotAsCheckpoint('Create account page')

'Scroll find area "Email" field'
WebUI.scrollToElement(findTestObject('Page_Patient Registration/div_Email'), 1)

'"Email" field'
WebUI.takeElementScreenshot(findTestObject('Page_Patient Registration/div_Email'))

'Enter "Email"'
WebUI.setText(findTestObject('Page_Patient Registration/input_Email_email'), 'katalon.user')

'Scroll find area "Password" field'
WebUI.scrollToElement(findTestObject('Page_Patient Registration/div_Password'), 1)

'"Password" field'
WebUI.takeElementScreenshot(findTestObject('Page_Patient Registration/div_Password'))

'Enter "Password"'
WebUI.setEncryptedText(findTestObject('Page_Patient Registration/input_Password_password'), 'mJQhRx/ih2AEvjZPbR5WXQ==')

'Focus find area "Accept and Continue" button'
WebUI.scrollToElement(findTestObject('Page_Patient Registration/button_Accept and Continue'), 1)

'"Accept and Continue" button'
WebUI.takeElementScreenshot(findTestObject('Page_Patient Registration/button_Accept and Continue'))

'Click "Accept and Continue" button to verify email and password'
WebUI.click(findTestObject('Page_Patient Registration/button_Accept and Continue'))

'Page navigated to "Create Account" Error message invalid email or password page'
WebUI.waitForElementPresent(findTestObject('Page_Patient Registration/div_Please enter valid email address'), 5)

'Scroll find area "Invalid email or password" error message'
WebUI.scrollToElement(findTestObject('Page_Patient Registration/div_Please enter valid email address'), 1)

'"Invalid email or password" error message field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/div_Please enter valid email address'))

WebUI.waitForElementPresent(findTestObject('Page_Patient Registration/img_Error occured_logo-mh360'), 5)

'"Invalid email or password" error message page'
WebUI.takeFullPageScreenshotAsCheckpoint('Create account invalid email address page')

'Finish'
WebUI.closeBrowser()

