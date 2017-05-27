package org.zaku.web;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
@WebAppConfiguration //
public class servletTest {
	
	@Inject
	    WebApplicationContext wax;  
	   MockMvc mockMvc;
	 @Before
	 public void setup(){
	  this.mockMvc=MockMvcBuilders.webAppContextSetup(this.wax).build();
	}

	 @Test
	 public void test() throws Exception{
		 mockMvc.perform(MockMvcRequestBuilders.get("/doTest"));
	 }
	
}
