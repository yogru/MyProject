package org.zaku.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zaku.domain.FilenameVO;
import org.zaku.persistence.FilenameDAO;
@Service
public class FilmenameServiceImpl implements FilenameService {

	@Inject 
	FilenameDAO fDao;
	@Override
	public int reg(FilenameVO fv) {
		return fDao.insertAndGetFno(fv);
	}
	/*@Override
	public String getPath(String name) {
		return fDao.getPath(name);
	}

	@Override
	public void deleteToName(String name) {
		fDao.deleteToName(name);
	}

	@Override
	@Transactional
	public String getPathAndDelete(String name) {
		String ret= this.getPath(name);
		this.deleteToName(name);
		return ret;
	}*/
	@Override
	public FilenameVO getFilename(Integer fno) {
		return fDao.selectOne(fno);
	}
	@Override
	public void delFile(Integer fno) {
		 fDao.deleteOne(fno);
	}
	
	@Override
	@Transactional
	public FilenameVO delAndGetFileVO(Integer fno) {
		FilenameVO ret= fDao.selectOne(fno);
		fDao.deleteOne(fno);
		return ret;
	}

}
