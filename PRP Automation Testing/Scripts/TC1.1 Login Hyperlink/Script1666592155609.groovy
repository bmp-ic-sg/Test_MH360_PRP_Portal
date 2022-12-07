import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
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
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import org.openqa.selenium.Keys as Keys

'Story: If user already has an account, user can click "Login now" to navigate user to the login page\r\n\r\nStep :\r\n\t1. Open browser and enter MH360 Signup URL\r\n\t2. Click on "Login now" hyperlink\r\n\t3. Page navigated to login page'
WebUI.comment('')

'Open the browser and enter MH360-Signup URL'
WebUI.openBrowser(GlobalVariable.BaseUrl)

'Navigate page to "Create Account" page'
WebUI.waitForPageLoad(10)

WebUI.waitForElementPresent(findTestObject('Page_Patient Obj Registration/a_Login now'), 1)

'Create account landing page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-create-account-landing-page')

'Hyperlink text  to navigate page to Login page'
WebUI.takeElementScreenshot(findTestObject('Object Repository/Page_Patient Obj Registration/a_Login now'))

'Click "Login now"  to navigate page to Login page'
WebUI.click(findTestObject('Object Repository/Page_Patient Obj Registration/a_Login now'))

'Navigate page to "Login" page'
WebUI.waitForPageLoad(10)

WebUI.waitForElementPresent(findTestObject('Page_MyHealth360 - Login/mh360-image-login-page'), 10)

'Login page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-login-page')

'Close Browser'
WebUI.closeBrowser()

