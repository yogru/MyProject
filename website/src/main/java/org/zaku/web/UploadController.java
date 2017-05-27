package org.zaku.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zaku.domain.FilenameVO;
import org.zaku.service.FilenameService;
import org.zaku.util.MediaUtils;
import org.zaku.util.UploadFileUtill;

@RequestMapping("/upload")
@Controller
public class UploadController {
	
	public static Logger log=Logger.getLogger(UploadController.class);
	@Inject
	FilenameService fser;
	
	@Resource(name="uploadPath")
    private	String uploadPath;
	
	@RequestMapping("/imagePopup")
	public String getImagePopupPage(){
		return "editor/imagePopup";
	}
	
	@PostMapping("/reg")
	@ResponseBody
	public  Map<String,String> uploadPost(MultipartFile file) throws Exception{
		log.info(file.getName());
		log.info(file.getOriginalFilename());
		log.info(file.getBytes());
		log.info("up:"+uploadPath);
	    List<String> names=UploadFileUtill.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
	   
	    FilenameVO fv= new FilenameVO();
	    fv.setFilename(names.get(0));
	    fv.setThumbnail(names.get(1));
	    fser.reg(fv);

	    Map<String,String> ret=  new HashMap<String,String>();
	    ret.put("fno",""+fv.getFno());
	    ret.put("fname", file.getOriginalFilename());
	  return  ret;
	}

	private ResponseEntity<byte[]> getFile(String absoultePath) throws Exception{
		 ResponseEntity <byte[]> entity=null;
		 InputStream in=null;
		try {
		 File file=new File(absoultePath);
		  in= new FileInputStream(file);
		 String formatName= absoultePath.substring(absoultePath.lastIndexOf(".")+1);
		 MediaType type= MediaUtils.get(formatName);
		 HttpHeaders headers=new HttpHeaders();
		 if(type !=null){
			 headers.setContentType(type);
		 }else{
			 absoultePath=absoultePath.substring(absoultePath.indexOf("_")+1);
			 headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			 headers.add("Content-Disposition","attachment; filename\""+
			 new String(absoultePath.getBytes("UTF-8"),"ISO-8859-1")+"\"");
		 }
		 entity= new ResponseEntity<byte[]>(IOUtils.toByteArray(in),
				 headers,
				 HttpStatus.CREATED);
		}catch(Exception e){
			e.printStackTrace();
			entity= new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally{
			  in.close();
		 }
		return 	 entity;
	}
	
	@GetMapping("/getThunmNailImage/{fno}")
	@ResponseBody
	public  ResponseEntity<byte[]> getThunmNailImage(@PathVariable("fno") Integer fno ){
		ResponseEntity<byte[]> ret=null;
		FilenameVO fv= fser.getFilename(fno);
		if(fv!=null){
			try{ret= getFile(fv.getThumbnail()); return ret;} 
			catch (Exception e) {e.printStackTrace();}
		}
		ret= new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		return ret;
	}
	
	@GetMapping("/deleteFile/{fno}")
	@ResponseBody
	public String delfile(@PathVariable("fno") Integer fno ){
		 log.info("파일 삭제 시도 ");
		FilenameVO fv= fser.delAndGetFileVO(fno);
		boolean of= new File(fv.getFilename()).delete();
		boolean tf= new File(fv.getThumbnail()).delete();
		return "original: "+of+", thumnali: "+tf;
	}	
	
	
	@GetMapping("/getOriginImage/{fno}")
	@ResponseBody
	public  ResponseEntity<byte[]> getOriginImage(@PathVariable("fno") Integer fno ){
		ResponseEntity<byte[]> ret=null;
		FilenameVO fv= fser.getFilename(fno);
		if(fv!=null){
			try{ret= getFile(fv.getFilename()); return ret;} 
			catch (Exception e) {e.printStackTrace();}
		}
		ret= new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		return ret;
	}
	/*
	@GetMapping("/delfile")
	@ResponseBody // 李⑤씪由� bno not null �젣�빟 議곌굔 �뾾�븷怨� , fno濡� 愿�由ы븯硫댁꽌 ,/reg�뿉�꽌 bno �뾽�뜲�씠�듃 �븯�뒗 諛⑹떇�쑝濡� �븷源�
	public boolean delFile(String fname,String option) throws Exception{
		String path=null;
		if(option==null || option.equals("new"))//reg�뿉�꽌�쓽 洹몃깷 �궘�젣,
		 path=UploadFileUtill.calPath(uploadPath);
		else{path=fser.getPathAndDelete(fname);}		
		
		path=uploadPath+path;
		 UploadFileUtill.deleteFile(path,UploadFileUtill.getThumbnailSeparator()+fname);
		return UploadFileUtill.deleteFile(path, fname);
	}
	
	@GetMapping("/loadImg")
	@ResponseBody
	public  ResponseEntity<byte[]> getImage(String fname,String option) throws Exception{
		String path=null;
		fser.getPath(fname);
		if(option==null){
			  path=fser.getPath(fname);
		}else{
			path=UploadFileUtill.calPath(uploadPath);
		}
		
		path=uploadPath+path;
		
	System.out.println("�솗�옣�옄"+fname);
		 InputStream in=null;
		 ResponseEntity<byte[]> entity=null;
		 log.info("�뙆�씪�씠由� "+fname);
		 try {
			 String formatName= fname.substring(fname.lastIndexOf(".")+1);
			 MediaType type= MediaUtils.get(formatName);
			 
			 File file=new File(path,fname);
			 in=new FileInputStream(file);
			 
			 HttpHeaders headers=new HttpHeaders();
			 if(type !=null){
				 headers.setContentType(type);
			 }else{
				 fname=fname.substring(fname.indexOf("_")+1);
				 headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				 headers.add("Content-Disposition","attachment; filename\""+
				 new String(fname.getBytes("UTF-8"),"ISO-8859-1")+"\"");
			 }
			 entity= new ResponseEntity<byte[]>(IOUtils.toByteArray(in),
					 headers,
					 HttpStatus.CREATED);
		 }
		 catch (Exception e) {
				e.printStackTrace();
				entity= new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		  }finally{
			  in.close();
		  }
		return 	 entity;
	}
*/	
	
}
