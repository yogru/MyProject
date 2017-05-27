package org.zaku.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zaku.domain.BoardVO;
import org.zaku.domain.Searchable;
import org.zaku.domain.SimplePager;
import org.zaku.persistence.BoardDAO;
import org.zaku.persistence.TestDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/root-context.xml" })
public class DAOTester {

	private Logger log=Logger.getLogger(DAOTester.class);
	
	 @Inject 
	 BoardDAO dao;
	
	 @Inject 
	 TestDAO tDao;
	 
	 
	 @Test
	 public void getList(){
		 List<BoardVO> ret= dao.selectList(new SimplePager(0,10));
		 log.info(ret);
	 }
	 
	 @Test
	 public void getCount(){
		 log.info("getCount(): "+dao.getCount());
	 }
	 
//	 @Test
//	 public void getDynamic(){
//		//log.info
//		 List<Searchable> list= new ArrayList<>();
//	//	 list.add(new Searchable("title","1"));
//	//	 list.add(new Searchable("content","1"));
//		 SimplePager sp= new SimplePager(0,10);
//		 
//		 log.info(dao.dynamicSQL(sp, list));
//		 
//	 }
	 
}
