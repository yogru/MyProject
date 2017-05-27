package org.zaku.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.zaku.domain.BoardVO;
import org.zaku.domain.FilenameVO;
import org.zaku.domain.Pageable;
import org.zaku.domain.Searchable;

@Service
public interface BoardService {
 
	public List<BoardVO> getList(Pageable page,Searchable sd);
	public Integer getCount(Searchable sd);
	public void regBoard(BoardVO vo);
	public void regBoard(BoardVO vo,List<FilenameVO> fv);
	public BoardVO getBoard(Integer bno);
	public int deleteBoard(Integer bno);
	public int updateBoard(BoardVO vo);
	public Map<BoardVO,List<FilenameVO>> getBoardAndFiles(Integer bno);
	public void update(BoardVO vo, String[] fno, String[] delfno);
}
