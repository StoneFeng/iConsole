package com.singland.console.helper;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

public class PropertiesHelperTest {
	
	@Test
	public void isAllowedWebModule() {
		assertTrue(PropertiesHelper.isAllowedWebModule("/iesb"));
		assertFalse(PropertiesHelper.isAllowedWebModule("/jolokia"));
	}

}
