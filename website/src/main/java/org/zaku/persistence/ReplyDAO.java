package org.zaku.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.zaku.domain.Pageable;
import org.zaku.domain.ReplyVO;

@Repository
public class ReplyDAO extends GenericDAO<ReplyVO, Integer> {
	
	public ReplyDAO(){
		nameSpace+=".ReplyDAO";
	}
	
	public List<ReplyVO> getListOfBno(Integer bno,int page, int size){
		Map<String , Integer> map= new HashMap<>();
		 map.put("bno", bno);
		 map.put("skip", (page-1)*size);
		 map.put("amount", size);
		 return ssi.selectList(nameSpace+".selectOfBnoList",map);
	}
	public int getCountOfBno(Integer bno){
		return ssi.selectOne(nameSpace+".getCountOfBno",bno);
	}
}
