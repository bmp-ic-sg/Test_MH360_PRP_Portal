import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable


boolean emailExist = true

if(!emailExist)
{
	WebUI.verifyElementPresent(findTestObject('Object Repository/Page_My Info/div_Sign-up Successful'), 3)
	WebUI.closeBrowser()
}
else
{
	String indexStr = GlobalVariable.TestEmail.substring(17, 18).trim()
	
	int  index = indexStr as Integer
	int count = 1
	
	while (emailExist) {
				
		def nextEmail = 'katalon.singpass.'+ index++ +'@mail.com';
		println (nextEmail);
		
		if( count > GlobalVariable.MaxUserCreate )
		{
			KeywordUtil.logInfo('MAX user to create = ' + GlobalVariable.MaxUserCreate)
			KeywordUtil.markFailed('Reach max creating user')
			emailExist = false
		}
		
		count++

	}
}