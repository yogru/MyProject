package org.zaku.domain;

public class Pager implements Pageable{

	private int pageHeight=10;
	private int pageWidth= 10;
    private int total;
    private int currentPage;
    private int pageWidth_end;
    private int pageWidth_start;
    private boolean isNext,isPrev;
	public Pager(int _currentPage,int _total){
		this.currentPage=_currentPage;
		this.total=_total;
		calPager();
	}
	private void calPager(){
		
		// 음수 페이지 방지
		if(currentPage<1)currentPage=1;
		
		int tempEnd=((int)
		(Math.ceil((double)currentPage/pageWidth)))*pageWidth;
		this.pageWidth_start=tempEnd-(pageWidth-1);
		this.pageWidth_end= tempEnd*pageHeight>total?
				    (int)Math.ceil((double)total/pageHeight)
					:tempEnd;
		this.isPrev=this.pageWidth_start==1?false:true;
		this.isNext=this.pageWidth_end*this.pageHeight>=total?false:true;
		
		// 최대 페이지 막기
	   if(this.currentPage* this.pageHeight>total){
		   this.currentPage=this.pageWidth_end;
	   }	
	}
	
	public int getPageWidth_end() {
		return pageWidth_end;
	}
	public int getPageWidth_start() {
		return pageWidth_start;
	}
	@Override
	public int getSkip(){
		return (currentPage-1)*pageHeight;
	}
	
	@Override
	public int getAmount(){
		  return pageHeight;
	}
    public boolean getNext(){
    	return this.isNext;
    }
	public boolean getPrev(){
		return this.isPrev;
	}
	
@Override
public String toString() {
	return "Pager [pageHeight=" + pageHeight + ", pageWidth=" + pageWidth + ", total=" + total + ", currentPage="
			+ currentPage + ", pageWidth_end=" + pageWidth_end + ", pageWidth_start=" + pageWidth_start + ", isNext="
			+ isNext + ", isPrev=" + isPrev + "]";
		}
}