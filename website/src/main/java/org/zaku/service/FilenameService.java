package org.zaku.service;

import org.zaku.domain.FilenameVO;

public interface FilenameService {
 /* public String getPath(String name);
  public void deleteToName(String name);
  public String getPathAndDelete(String name);
  */
	public int reg(FilenameVO fv);
	public FilenameVO getFilename(Integer fno);
	public void delFile(Integer fno);
	public FilenameVO delAndGetFileVO(Integer fno);
}
