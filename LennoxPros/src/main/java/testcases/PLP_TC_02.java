package testcases;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import common.LennoxProsMethods;
import pages.LennoxProsLaunchPage;
import utils.DataInputProvider;

public class PLP_TC_02 extends LennoxProsMethods {
	@Test()
	public void testCase() {
		DataInputProvider dp = new DataInputProvider(dataSheetName);
		String emailId = dp.getCellData("UserName", runningTestcaseName);
		String password = dp.getCellData("Password", runningTestcaseName);

		new LennoxProsLaunchPage().clickSignIn().enterUserName(emailId).enterPassword(password).clickSignInBtn()
				.clickHamBurgerMenu().clickMainMenu().clickSubMenu().clickCompressorsLink().verifyDescription()
				.searchProduct().productComparison();

	}

}
