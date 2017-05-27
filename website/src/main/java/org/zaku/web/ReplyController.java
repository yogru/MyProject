package org.zaku.web;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zaku.domain.Pageable;
import org.zaku.domain.Pager;
import org.zaku.domain.ReplyVO;
import org.zaku.service.ReplyService;

@Controller
@RequestMapping("/reply")
@RestController
public class ReplyController {

 @Inject
 private	ReplyService rser;
	
 private	Logger log = Logger.getLogger(ReplyController.class);
	
	
	@GetMapping("/getReplyList/{page}/{bno}/{size}")
	public List<ReplyVO> getReplyList(
			@PathVariable("bno") Integer bno,
			@PathVariable("page") Integer page,
			@PathVariable("size") Integer size
			){
		return rser.getList(bno,page,size);
	}
	
	@GetMapping("/getPager/{page}/{bno}")
	public Pageable getPager(@PathVariable("bno") Integer bno,
			@PathVariable("page") Integer page){
		Pageable pager=null;
		pager =new Pager(page, rser.getCountOfBno(bno));
		return pager;
	}
	
	@PostMapping("/reg")
	public Integer regReply(ReplyVO rv){
     log.info(rv);		
      return rser.regReply(rv);
	}
	
	@GetMapping("/del/{rno}")
	public int deleteReply(@PathVariable("rno") Integer rno){
		return rser.delteReply(rno);
	}	
}
