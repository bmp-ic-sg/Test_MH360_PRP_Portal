import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable

'Story: User entered wrong OTP code after clicking submit button.\r\n\r\nStep:\r\n\t1. Open browser and enter MH360 Signup URL\r\n\t2. Click on "Sign Up with an Email" button\r\n\t3. Page navigated to "Create Account" page\r\n\t4. Enter email address\r\n\t5. Enter password\r\n\t6. Click "Accept and Continue" button\r\n\t7. Page navigated to "Create My Profile" page\r\n\t8. Fill in user information\r\n\t9. Click "Submit" button\r\n\t10. Enter wrong OTP code\r\n\t11. Click "Verify" button\n\t12. Error message invalid OTP code shown\r\n'
WebUI.comment('')

'User must sign up with an email, To sign up with an email see TC 1.3 sign up with an email.'
WebUI.callTestCase(findTestCase('Base Sign Up with an Email'), [:], FailureHandling.STOP_ON_FAILURE)

'Scroll find area "Countdown"'
WebUI.waitForElementPresent(findTestObject('Object Repository/Page_Patient Registration/div_Resend the OTP to my mobile phone in 29'), 
    5)

'OTP page'
WebUI.takeFullPageScreenshotAsCheckpoint('OTP page frist load')

'Scroll find area "Countdown"'
WebUI.scrollToElement(findTestObject('Object Repository/Page_Patient Registration/div_Resend the OTP to my mobile phone in 29'), 
    1)

'"Countdown"'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/div_Resend the OTP to my mobile phone in 29'))

'Waiting "Countdown" 60 second'
WebUI.waitForElementNotPresent(findTestObject('Page_Patient Registration/div_Resend the OTP to my mobile phone in 1'), 60)

WebUI.waitForElementPresent(findTestObject('Object Repository/Page_Patient Registration/a_Resend the OTP to my mobile phone'), 
    10)

'"OTP" page'
WebUI.takeFullPageScreenshotAsCheckpoint('OTP page link')

'Focus find area "Resend the OTP to my mobile phone" link'
WebUI.focus(findTestObject('Object Repository/Page_Patient Registration/a_Resend the OTP to my mobile phone'), FailureHandling.STOP_ON_FAILURE)

'"Resend the OTP to my mobile phone" link'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/a_Resend the OTP to my mobile phone'))

'Click "Resend the OTP to my mobile phone" link to resend OTP code'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/a_Resend the OTP to my mobile phone'))

WebUI.waitForElementPresent(findTestObject('Object Repository/Page_Patient Registration/img_Error occured_logo-mh360'), 
    10)

'"OTP" page'
WebUI.takeFullPageScreenshotAsCheckpoint('OTP page - 1st resend the OTP to my mobile phone')

'Scroll find area "Countdown"'
WebUI.scrollToElement(findTestObject('Object Repository/Page_Patient Registration/div_Resend the OTP to my mobile phone in 56'), 
    1)

'Countdown'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/div_Resend the OTP to my mobile phone in 56'))

'Waiting "Countdown" 60 second'
WebUI.waitForElementNotPresent(findTestObject('Page_Patient Registration/div_Resend the OTP to my mobile phone in 1'), 60)

WebUI.waitForElementPresent(findTestObject('Object Repository/Page_Patient Registration/a_Resend the OTP to my mobile phone'), 
    10)

'OTP page'
WebUI.takeFullPageScreenshotAsCheckpoint('OTP page link - 1st resend the OTP to my mobile phone')

'Scroll find area "Resend the OTP to my mobile phone" link'
WebUI.scrollToElement(findTestObject('Object Repository/Page_Patient Registration/a_Resend the OTP to my mobile phone'), 
    1)

'"Resend the OTP to my mobile phone" link'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/a_Resend the OTP to my mobile phone'))

'Click "Resend the OTP to my mobile phone" link to resend OTP code'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/a_Resend the OTP to my mobile phone'))

WebUI.waitForElementPresent(findTestObject('Object Repository/Page_Patient Registration/img_Error occured_logo-mh360'), 
    10)

'"OTP" page'
WebUI.takeFullPageScreenshotAsCheckpoint('OTP page - 2st resend the OTP to my mobile phone')

'Scroll find area "Countdown"'
WebUI.scrollToElement(findTestObject('Object Repository/Page_Patient Registration/div_Call me with an OTP in 43'), 1)

'"Countdown"'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/div_Call me with an OTP in 43'))

WebUI.waitForElementNotPresent(findTestObject('Object Repository/Page_Patient Registration/div_Call me with an OTP in 1'), 
    60)

'Waiting "Countdown" 60 second'
WebUI.waitForElementPresent(findTestObject('Object Repository/Page_Patient Registration/a_Call me with an OTP'), 10)

'"OTP" page by voice call'
WebUI.takeFullPageScreenshotAsCheckpoint('OTP page link - 2st resend the OTP to my mobile phone')

'Scroll find area "Call me with an OTP" link'
WebUI.scrollToElement(findTestObject('Object Repository/Page_Patient Registration/a_Call me with an OTP'), 1)

'"Call me with an OTP" link'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/a_Call me with an OTP'))

'Click "Call me with an OTP" link to resend OTP code by voice call'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/a_Call me with an OTP'))

WebUI.waitForElementPresent(findTestObject('Object Repository/Page_Patient Registration/img_Error occured_logo-mh360'), 
    10)

'"OTP" page by voice call'
WebUI.takeFullPageScreenshotAsCheckpoint('OTP page by voice call 1st')

'scroll find area "Countdown"'
WebUI.scrollToElement(findTestObject('Object Repository/Page_Patient Registration/div_Call me again in  48'), 1)

'"Countdown"'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/div_Call me again in  48'))

'Waiting "Countdown" 60 second'
WebUI.waitForElementNotPresent(findTestObject('Object Repository/Page_Patient Registration/div_Call me again in  1'), 60)

WebUI.waitForElementPresent(findTestObject('Object Repository/Page_Patient Registration/a_Call me again'), 10)

'"OTP" page by voice call'
WebUI.takeFullPageScreenshotAsCheckpoint('OTP page link by voice call 1st')

'Scroll find area "Call me again" link'
WebUI.scrollToElement(findTestObject('Object Repository/Page_Patient Registration/a_Call me again'), 1)

'"Call me again" link'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/a_Call me again'))

'Clik "Call me again" link to resend OTP code by voice call'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/a_Call me again'))

WebUI.waitForElementPresent(findTestObject('Object Repository/Page_Patient Registration/img_Error occured_logo-mh360'), 
    10)

'"OTP" page by voice call'
WebUI.takeFullPageScreenshotAsCheckpoint('OTP page by voice call 2st')

'Scroll find area "OTP" field'
WebUI.scrollToElement(findTestObject('Object Repository/Page_Patient Registration/div_A one-time PIN has been sent via SMS to_be2b53'), 
    1)

'"OTP" field'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/div_A one-time PIN has been sent via SMS to_be2b53'))

'Enter wrong "OTP" code'
WebUI.setText(findTestObject('Object Repository/Page_Patient Registration/input_A one-time PIN has been sent via SMS _fd653b'), 
    '123455')

'Focus find "OTP" button'
WebUI.focus(findTestObject('Object Repository/Page_Patient Registration/submit-otp-button'))

'"OTP" button'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/submit-otp-button'))

'Click "OTP" button to verify otp code'
WebUI.click(findTestObject('Object Repository/Page_Patient Registration/submit-otp-button'))

'Page navigated to "OTP" Error message page'
WebUI.waitForElementPresent(findTestObject('Object Repository/Page_Patient Registration/div_You may have entered an invalid pin, pl_5b384d'), 
    5)

'Scroll find area "OTP" error message'
WebUI.scrollToElement(findTestObject('Object Repository/Page_Patient Registration/div_You may have entered an invalid pin, pl_5b384d'), 
    1)

'Focus find area "OTP" error message'
WebUI.focus(findTestObject('Object Repository/Page_Patient Registration/div_You may have entered an invalid pin, pl_5b384d'))

'"OTP" error message invalid OTP code'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Registration/div_You may have entered an invalid pin, pl_5b384d'))

'Enter wrong "OTP" code'
WebUI.setText(findTestObject('Object Repository/Page_Patient Registration/input_A one-time PIN has been sent via SMS _fd653b'), 
    '123455')

'Invalid OTP code page "OTP" error message'
WebUI.takeFullPageScreenshotAsCheckpoint('OTP Page invalid OTP code')

'Finish'
WebUI.closeBrowser()

