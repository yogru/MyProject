package org.zaku.service;

import java.util.List;

import org.zaku.domain.ReplyVO;

public interface ReplyService {
   public List<ReplyVO> getList(Integer bno,Integer page, Integer size);
   public int regReply(ReplyVO rv);
   public int getCountOfBno(Integer bno);
   public int delteReply(Integer rno);
   
}
