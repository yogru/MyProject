package org.zaku.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class TestDAO {
 
	@Inject
	SqlSession sess;
	
	public String getTime(){
		System.out.println("call? ");
		return "now()";
	}
	
	public String get(){
	return 	sess.selectOne("org.zaku.persistence.TestDAO.id",this);
	}
	
}
