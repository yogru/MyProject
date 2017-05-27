package org.zaku.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zaku.domain.BoardVO;
import org.zaku.domain.FilenameVO;
import org.zaku.domain.Pageable;
import org.zaku.domain.Searchable;
import org.zaku.persistence.BoardDAO;
import org.zaku.persistence.FilenameDAO;

@Service
public class BoardServiceImp implements BoardService {
	public static final int PAGE_HEIGHT=10;
	public static final int PAGE_WIDTH=10;
	public static Logger log= Logger.getLogger(BoardServiceImp.class);
	
	@Inject
	private BoardDAO bDao;

	@Inject 
	private FilenameDAO fDao;

	@Override
	public void regBoard(BoardVO vo) {
	    bDao.insert(vo);
	}

	@Override
	public BoardVO getBoard(Integer bno) {
		return bDao.selectOne(bno);
	}

	@Override
	@Transactional
	public int deleteBoard(Integer bno) {
		fDao.deleteList(bno);
		int ret=bDao.deleteOne(bno);
		return ret;
	}

	@Override
	public int updateBoard(BoardVO vo) {
       return  bDao.updateOne(vo);
	}

	@Override
	public List<BoardVO> getList(Pageable page, Searchable sd) {
		List<BoardVO> ret=null;
		if(sd.getOptSize()!=0)
			ret=bDao.dynamicSQL(page, sd);
		else ret= bDao.selectList(page);
		
		return	ret;
	}

	@Override
	public Integer getCount(Searchable sd) {
		int ret=0;
		if(sd.getOptSize()!=0)
		 ret =bDao.getCount(sd);
		else 
		 ret=bDao.getCount();
		return ret;
	}

	@Override
	@Transactional
	public void regBoard(BoardVO vo, List<FilenameVO> fv) {
		 bDao.insert(vo);
		int bno= vo.getBno();
		for(FilenameVO sfv: fv){
			sfv.setBno(bno);
			//updateBNO;
			fDao.updateOne(sfv);
		}
	}

	//반환값 보면 존나 괴물이지만, 그래도 컨트롤러에서 어떻게든 db에 한방에 가져오고싶었다. 
	@Override
	@Transactional
	public Map<BoardVO, List<FilenameVO>> getBoardAndFiles(Integer bno) {
		BoardVO bv= bDao.selectOne(bno);
		List<FilenameVO> fv= fDao.getAllListBno(bno);
		Map<BoardVO, List<FilenameVO>>  ret=  new HashMap<BoardVO, List<FilenameVO>>();
		ret.put(bv, fv); 
		return ret;
	}

	@Override
	@Transactional
	public void update(BoardVO vo, String[] fno, String[] delfno) {
		bDao.updateOne(vo);
  	     Integer bno=vo.getBno();
  	    if(fno!=null){ 
	      for(String sfno:fno){
	    	Integer ifno=Integer.parseInt(sfno);
	    	FilenameVO fv= new FilenameVO();
	    	fv.setBno(bno);
	    	fv.setFno(ifno);
	   	    fDao.updateOne(fv);
	      }
  	    }
  	    if(delfno!=null){
	     for(String sfno: delfno){
	     	 Integer ifno=Integer.parseInt(sfno);
	    	 fDao.deleteOne(ifno);
	      }
  	    }
	}
}
