package org.zaku.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.zaku.domain.Pageable;

public class GenericDAO<E,K> {
	
	@Inject
	protected SqlSession ssi;
	
	protected String nameSpace;		
	GenericDAO(){
		nameSpace="org.zaku.persistence";
	}
	public E selectOne(K key){
       return ssi.selectOne(nameSpace+".selectOne",key);		
	}
	public int insert(E ele){
		return ssi.insert(nameSpace+".insert",ele);		
	}

	public int deleteOne(K key){
	   return  ssi.delete(nameSpace+".deleteOne",key);	
	}

	public int updateOne(E ele){
		return ssi.update(nameSpace+".updateOne",ele);
	}
	public List<E> selectList(Pageable page){
	return ssi.selectList(nameSpace+".selectList",page);
	}
	
	public int getCount(){
		return ssi.selectOne(nameSpace+".getCount");
	}
	
	public String getNameSpace(){
		return nameSpace;
	}
	
}
