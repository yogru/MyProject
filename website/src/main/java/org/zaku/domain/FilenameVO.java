package org.zaku.domain;

public class FilenameVO {
	Integer fno;
	Integer bno;
	String filename;
	String	thumbnail;
	
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public Integer getFno() {
		return fno;
	}
	public void setFno(Integer fno) {
		this.fno = fno;
	}
	public Integer getBno() {
		return bno;
	}
	public void setBno(Integer bno) {
		this.bno = bno;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Override
	public String toString() {
		return "[fno:" + fno + ", bno:" + bno + ", filename:" + filename + ", thumbnail:" + thumbnail + "]";
	}	
	 
}
