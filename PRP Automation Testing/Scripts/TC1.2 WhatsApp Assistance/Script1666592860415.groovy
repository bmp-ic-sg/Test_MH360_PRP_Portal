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

'Story: If user need assistace, user can click "Need assistance? Chat with us!" button to open "WhatsApp"\r\n\r\nStep :\r\n\t1. Open browser and enter MH360 Signup URL\r\n\t2. Click on "Need assistance? Chat with us!" button\r\n\t3. Page navigated to "WhatsApp" page'
WebUI.comment('')

'Open the browser and enter MH360-Signup URL'
WebUI.openBrowser(GlobalVariable.BaseUrl)

'Navigate page to "Create Account" page'
WebUI.waitForPageLoad(10)

'Create account landing page'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-create-account-landing-page')

'Button to navigate page to WhatsApp'
WebUI.takeElementScreenshot(findTestObject('Page_Patient Obj Registration/a_Need assistance Chat with us'))

'Click "Need assistance? Chat with us!" button to navigate page to WhatsApp'
WebUI.click(findTestObject('Page_Patient Obj Registration/a_Need assistance Chat with us'))

'Navigate page to WhatsApp'
WebUI.waitForPageLoad(10)

WebUI.waitForElementPresent(findTestObject('Page_Share on WhatsApp/img_Get in touch__9vx6'), 10)

'WhatsApp Page Shown'
WebUI.takeFullPageScreenshotAsCheckpoint('prp-whatsapp-assistance-page')

'Close Browser'
WebUI.closeBrowser()

