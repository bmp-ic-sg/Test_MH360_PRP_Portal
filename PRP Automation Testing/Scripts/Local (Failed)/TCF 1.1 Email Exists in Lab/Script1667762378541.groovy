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

'Story: User enters registered email \r\n\r\nStep:\r\n\t1. Open browser and enter MH360 Signup URL\r\n\t2. Click on "Sign Up with an Email" button\r\n\t3. Page navigated to "Create Account" page\r\n\t4. Enter email address registered\r\n\t5. Enter password\r\n\t6. Click "Accept and Continue" button\n\t7. Email already exists error message is shown'
WebUI.comment('')

'Open the browser and enter MH360-Signup URL'
WebUI.openBrowser(GlobalVariable.BaseUrl)

'Page navigated to "Create Account" Landing page'
WebUI.waitForElementPresent(findTestObject('Page_Patient Registration/img_Allow_prp-img-title'), 5)

'"Create account" Landing page'
WebUI.takeFullPageScreenshotAsCheckpoint('Create account landing page')

'Focus find area "Sign Up with an Email" button'
WebUI.focus(findTestObject('Page_Patient Registration/button_Sign Up with an Email'))

'"Sign Up with an Email" button'
WebUI.takeElementScreenshot(findTestObject('Page_Patient Registration/button_Sign Up with an Email'))

'Click "Sign Up with an email" button to create account with email'
WebUI.click(findTestObject('Page_Patient Registration/a_Sign Up with an Email'))

'Page navigated to "Create Account" page'
WebUI.waitForElementPresent(findTestObject('Page_Patient Registration/img_Error occured_logo-mh360'), 5)

'"Create Account" page'
WebUI.takeFullPageScreenshotAsCheckpoint('Create account page')

'Scroll find area "Email" field'
WebUI.scrollToElement(findTestObject('Page_Patient Registration/div_Email'), 1)

'"Email" field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/div_Email'))

'Enter "Email"'
WebUI.setText(findTestObject('Page_Patient Registration/input_Email_email'), 'katalon.user.1@mail.com')

'Scroll find area "Password" field'
WebUI.scrollToElement(findTestObject('Page_Patient Registration/div_Password'), 1)

'"Password" field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/div_Password'))

'Enter "Password" field'
WebUI.setEncryptedText(findTestObject('Page_Patient Registration/input_Password_password'), 'iFGeFYmXIrUhQZHvW7P22w==')

'Scroll find area "Accept and Continue" button'
WebUI.scrollToElement(findTestObject('Page_Patient Registration/button_Accept and Continue'), 1)

'"Accept and Continue" button'
WebUI.takeElementScreenshot(findTestObject('Page_Patient Registration/button_Accept and Continue'))

'Click "Accept and Continue" button to verify email and password'
WebUI.click(findTestObject('Page_Patient Registration/button_Accept and Continue'))

'Scroll find area "Email already exists" error message'
WebUI.scrollToElement(findTestObject('Page_Patient Registration/div_This email already exists'), 1)

'"Email already exists" error message'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/div_This email already exists'))

WebUI.waitForElementPresent(findTestObject('Page_Patient Registration/div_This email already exists'), 5)

'"Email already exists" error message page'
WebUI.takeFullPageScreenshotAsCheckpoint('Create account page Email already exists.')

'Finish'
WebUI.closeBrowser()

