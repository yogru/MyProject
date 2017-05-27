package org.zaku.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.zaku.domain.Pageable;
import org.zaku.domain.ReplyVO;
import org.zaku.persistence.ReplyDAO;

@Service
public class ReplyServiceImp implements ReplyService{

	@Inject
	private	ReplyDAO rDao;

	@Override
	public int regReply(ReplyVO rv) {
         return   rDao.insert(rv);	
	}

	@Override
	public List<ReplyVO> getList(Integer bno, Integer page, Integer size) {
		return rDao.getListOfBno(bno, page, size);
	}
	@Override
	public int getCountOfBno(Integer bno) {
		return rDao.getCountOfBno(bno);
	}

	@Override
	public int delteReply(Integer rno) {
	  return rDao.deleteOne(rno);
	}
}
