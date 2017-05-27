package org.zaku.web;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zaku.domain.BoardColunm;
import org.zaku.domain.BoardVO;
import org.zaku.domain.FilenameVO;
import org.zaku.domain.Pageable;
import org.zaku.domain.Pager;
import org.zaku.domain.Searchable;
import org.zaku.domain.SimplePager;
import org.zaku.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	public static Logger log = Logger.getLogger(BoardController.class);
	@Inject
	BoardService bser;

	@GetMapping("/list")
	public void getIndexHTML(Integer page, Integer size, String keyword, String[] option, Model model) {
		List<BoardVO> list = null;
		if (page == null)
			page = 1;
		if (size == null)
			size = 10;
		log.info("-------------option" + option);
		Pageable pgb = new SimplePager((page - 1) * size, size);
		Searchable sc = new Searchable();
		if (option != null) {
			for (String opt : option) {
				log.info("ooooooooooooop------------" + opt);
				try {
					BoardColunm.valueOf(opt);
					sc.addOption(opt);
				} catch (Exception e) {
				}
			}
			sc.setKeyword(keyword);
		}
		list = bser.getList(pgb, sc);
		log.info("list controller");
		model.addAttribute("list", list);// ssibal 리스트처리
		model.addAttribute("option", sc.getOption());
		model.addAttribute("keyword", keyword);
		model.addAttribute("curPage", page);
	}
	
	@GetMapping("/view")
	public void getViewPage(Integer bno, Model model) {
		log.info("view COntrolllllllelelel");
		model.addAttribute("boardVO", bser.getBoard(bno));
	}

	@GetMapping("/reg")
	public void getRegPage() {
	}

	@PostMapping("/reg")
	public String setRegData(String title, String writer, String content, String fno) {
		log.info("----------------C: " + content);
		log.info("----------------T: " + title);
		log.info("----------------W: " + writer);
		log.info("----------------fno: " + fno);

		BoardVO bv = new BoardVO();
		bv.setContent(content);
		bv.setWriter(writer);
		bv.setTitle(title);

		if (fno == null) {
			bser.regBoard(bv);
		} else {
			List<FilenameVO> fList = new ArrayList<>();
			String[] fArr = fno.split(",");
			for (String fn : fArr) {
				FilenameVO fv = new FilenameVO();
				fv.setFno(Integer.parseInt(fn));
				fList.add(fv);
			}
			bser.regBoard(bv, fList);
		}
		return "redirect:/board/list";
	}

	@GetMapping("/delete")
	@ResponseBody
	public boolean delBoard(Integer bno) {
		boolean ret = bser.deleteBoard(bno) != 0 ? true : false;
		return ret;
	}

	@GetMapping("/getPager/{pageNum}/{option}/{keyword}")
	@ResponseBody
	public Pager getPager(@PathVariable("pageNum") Integer pageNum, @PathVariable("option") String[] option,
			@PathVariable("keyword") String keyword) {
		Searchable sc = new Searchable();
		if (option != null) {
			for (String opt : option) {
				try {
					BoardColunm.valueOf(opt);
					sc.addOption(opt);
				} catch (Exception e) {
				}
			}
			sc.setKeyword(keyword);
		}
		return new Pager(pageNum, bser.getCount(sc));
	}
	
	@GetMapping("/update")
	public String getUpdatePage(Integer bno, Model model){
		 if(bno==null)return "redirect:/board/list";
		  Map<BoardVO, List<FilenameVO>> map= bser.getBoardAndFiles(bno);
		  
		BoardVO voS= (BoardVO) (map.keySet().iterator().next());
		List<FilenameVO> fVos=map.get(voS);
		List<Integer> fnoS=new LinkedList<>();
		for(FilenameVO fv : fVos){
			fnoS.add(fv.getFno());
		}
		model.addAttribute("BoardVO", voS);
		model.addAttribute("fnos", fnoS);
		
		return "/board/update";
	}
	
	
	@PostMapping("/update")
	public String update(BoardVO board,String fno,String delfno){
		log.info("bv:"+board);
		log.info("f: "+fno);
		log.info("df: "+delfno);
		String [] fnoArr=null;
		String [] delfnoArr=null;
		if(!fno.equals(""))
		 fnoArr=fno.split(",");
		if(!delfno.equals(""))
	      delfnoArr=delfno.split(",");
		
		bser.update(board, fnoArr, delfnoArr);
	  return "redirect:/board/list";
	}
	
}
