package org.zaku.domain;

import java.util.ArrayList;
import java.util.List;

public class Searchable {
	private List<String> option;
	private String keyword;
   
	public Searchable(){
		this.option= new ArrayList<>();
	}
	public Searchable addOption(String opt){
		this.option.add(opt);
		return this;
	}
	public List<String> getOption() {
		return option;
	}
	public void setOption(List<String> option) {
		this.option = option;
	}
	public String getKeyword() {
		return keyword;
	}
	public Searchable setKeyword(String keyword) {
		this.keyword = keyword;
		return this;
	}
	public int getOptSize(){
		return option.size();
	}
	
	@Override
	public String toString() {
		return "Searchable [option=" + option + ", keyword=" + keyword + "]";
	}


}
