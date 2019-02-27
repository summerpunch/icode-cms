package com.icode.cms;

import com.icode.cms.common.response.tree.TreeDictionaryResponse;
import com.icode.cms.service.ICmsDictionaryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IcodeCmsApplicationTests {

	@Autowired
	private ICmsDictionaryService service;

	@Test
	public void contextLoads() {
		TreeDictionaryResponse dictionaryTree = service.getDictionaryTree();
		System.out.println(dictionaryTree);
	}

}
