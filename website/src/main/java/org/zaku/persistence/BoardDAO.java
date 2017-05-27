package org.zaku.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.zaku.domain.BoardVO;
import org.zaku.domain.Pageable;
import org.zaku.domain.Searchable;


@Repository
public class BoardDAO extends GenericDAO<BoardVO, Integer>{
	
	public BoardDAO(){
		this.nameSpace+=".BoardDAO";
	}
	
	public List<BoardVO> dynamicSQL(Pageable page,Searchable sch){
		Map<String,Object> map= new HashMap<>();
		map.put("page", page);
		map.put("list", sch.getOption());
		map.put("keyword", sch.getKeyword());
	 return ssi.selectList(nameSpace+".dynamicList",map);
	}
	
	
	public int getCount(Searchable sch){
		Map<String,Object> map= new HashMap<>();
		map.put("list", sch.getOption());
		map.put("keyword", sch.getKeyword());
		return ssi.selectOne(nameSpace+".getCountInSearchable",map);
	}
	
}
