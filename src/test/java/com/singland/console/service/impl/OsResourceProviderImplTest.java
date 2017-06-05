package com.singland.console.service.impl;

import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.singland.console.service.IOsResourceProvider;
import com.singland.console.vo.OsResVo;
import static org.testng.AssertJUnit.*;

public class OsResourceProviderImplTest {
	
	private IOsResourceProvider provider;
	
	@BeforeTest
	public void setup() {
		provider = new OsResourceProviderImpl();
	}
	
	@AfterTest
	public void tearDown() {
		provider = null;
	}
	
	@Test
	public void getCpuAndMem() throws Exception {
		List<OsResVo> list = provider.getOsResourceList();
		assertFalse(list.isEmpty());
	}

}
