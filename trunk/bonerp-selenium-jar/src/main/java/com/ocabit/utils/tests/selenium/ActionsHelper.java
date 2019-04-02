package com.ocabit.utils.tests.selenium;



import org.openqa.selenium.interactions.Actions;


public class ActionsHelper extends Actions {

	private SeleniumHelper seleniumHelper;

	public ActionsHelper(SeleniumHelper seleniumHelper) {
		super(seleniumHelper.getDriver());
		setSeleniumHelper(seleniumHelper);
	}

	public SeleniumHelper getSeleniumHelper() {
		return seleniumHelper;
	}

	public void setSeleniumHelper(SeleniumHelper seleniumHelper) {
		this.seleniumHelper = seleniumHelper;
	}

	public ActionsHelper moveByCSS(String selector) {
		return (ActionsHelper) moveToElement(getSeleniumHelper().byCSS(selector));
	}

	public ActionsHelper moveById(String selector) {
		return (ActionsHelper) moveToElement(getSeleniumHelper().byId(selector));

	}

	public ActionsHelper moveByXPath(String selector) {
		return (ActionsHelper) moveToElement(getSeleniumHelper().byXPath(selector));
	}

}
