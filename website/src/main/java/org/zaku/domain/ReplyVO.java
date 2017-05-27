package org.zaku.domain;

import java.sql.Date;

public class ReplyVO {
	Integer rno;
    Integer bno;
    String content;
    String writer;
    Date regdata;
	public Integer getRno() {
		return rno;
	}
	public void setRno(Integer rno) {
		this.rno = rno;
	}
	public Integer getBno() {
		return bno;
	}
	public void setBno(Integer bno) {
		this.bno = bno;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getRegdata() {
		return regdata;
	}
	public void setRegdata(Date regdata) {
		this.regdata = regdata;
	}
	@Override
	public String toString() {
		return "ReplyVO [rno=" + rno + ", bno=" + bno + ", content=" + content + ", writer=" + writer + ", regdata="
				+ regdata + "]";
	}
	
	
}
