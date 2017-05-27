package org.zaku.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.zaku.domain.FilenameVO;

@Repository
public class FilenameDAO extends GenericDAO<FilenameVO, Integer> {
	
	public FilenameDAO(){
		nameSpace+=".FilenameDAO";
	}
   public int insertAndGetFno(FilenameVO fv){
	  return  ssi.insert(nameSpace+".inAndGetFno",fv);
   }
   
   public void deleteList(int bno){
	   ssi.delete(nameSpace+".deleteList",bno);
   }
   
   public List<FilenameVO> getAllListBno(Integer bno){
	   return ssi.selectList(nameSpace+".selectBnoListAll",bno);
   }
   
}
