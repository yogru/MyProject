<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-md-12">
	<div class="content-panel">
		<h4>
			<i class="fa fa-angle-right"></i> Advanced Table
		</h4>
		<hr>
		<table class="table table-striped table-advance table-hover">
			<thead>
				<tr>
					<th><i class="fa fa-list-ol"></i> #</th>
					<th class="hidden-phone"><i class="fa fa-edit"></i> 제목</th>
					<th><i class="fa fa-user-circle"></i> 작성자</th>
					<th><i class=" fa fa-edit"></i> 등록일</th>
				</tr>
			</thead>


			<tbody class="tbd">
				<c:forEach items="${list}" var="board">
					<tr data-bno='${board.bno}'>
						<td>${board.bno}</td>
						<td class="hidden-phone" style="width: 500px"><a class="locateAtag" data-bno='${board.bno}' >${board.title} </a> </td>
						<td>${board.writer}</td>
						<td><span class="label label-info label-mini"></span>${board.updatedate}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
    
  
    
       <div class="bottomLine" style="width:100%;margin:auto">
       
       <button type="button" id="initPageBtn" class="btn btn-default" style="width:20%;float:left" >전체 목록 </button>
       <button type="button" id="regBtn" class="btn btn-default"  style="width:20%;float:right" >새 글 쓰기 </button>		
   	  
   	    <div style="width:60%;margin:auto">
			<div class="pageWidthTag">
			</div>
		</div>    
				<div name="select searchbox  searchbtn">
					<select name="type"id="selectSearch" class="form-control"  
					  style="width:20%;float:left">
			
					
						<option  value="title,content,writer">All</option>
						<option  value="title">title</option>
						<option  value="content">content</option>
						<option  value="writer">writer</option>
						<option  value="title,content">title+content</option>
					</select> 
					<div  style="width:40%;float:left" >
					<input id="keyword" name="keyword" type="text" >
					<button id='searchBtn' class="btn btn-info"><i class="fa fa-search" aria-hidden="true"></i></button>
					</div>
				</div>
		</div>
		

	<%@ include file="../include/footer.jsp"%>
	<script >
     $(document).ready(function(){
    	 
  		 var $regBtn=$("#regBtn");    	 
         var $searchBtn=$("#searchBtn");
         var $keyword=$("#keyword");
         var curPage=${curPage};
    	 var keyword='${keyword}'==='' ? 'null':'${keyword}';
    	 var option='${option}'.slice(1,'${option}'.length-1)===''?'null':'${option}'.slice(1,'${option}'.length-1);
    	 var $pageWidthTag=$(".pageWidthTag");
    	 $("#selectSearch").val(option==='null'?"title":option); // 셀렉트박스 기본 선택 
    	 $keyword.val(keyword==='null'?'':keyword);
    	 
    	 console.log("k o p",keyword,option,curPage);
    	 console.log("opURL: ",option);
    	 
    	  $(".locateAtag").on("click",function(e){
    	    	// e.preventDefault(); 
    	    	 var bno=$(this).attr("data-bno");
    	       location.href="/board/view?bno="+bno+"&page="+curPage+"&option="+option+"&keyword="+keyword;
    	   })
    	 
    	 $searchBtn.on("click",function(){
    		 var selVar=$("#selectSearch option:selected").val(); 
    		 console.log("sel: ",selVar);
    		  var key=$keyword.val()
    		  console.log("key:" , key);
    		 location.href="/board/list?page="+1+"&option="+selVar+"&keyword="+key;
    	 })
    	  $regBtn.on("click",function(){
    		 location.href="/board/reg";
    	 })
    	 
    	 var getPager=(function(_page){
    		 $.ajax({
        		 url:"/board/getPager/"+_page+"/"+option+"/"+keyword,
        		 success:function(data){
        			 console.log(data);
        			 var si=data.pageWidth_start;
        		     var ei=data.pageWidth_end;
        		     $pageWidthTag.html("");
        		     if(si==0)return;
        		     if(data.prev)
        		        $pageWidthTag.append("<button class='pageNumButton' data-rect="+(si-1)+" > << </button>");
        		     for(var i=si;i<=ei;i++)
        		    	$pageWidthTag.append("<button class='pageNumButton' data-rect="+i+" >"+i+"</button>");
        		     if(data.next)
        		    	   $pageWidthTag.append("<button class='pageNumButton' data-rect="+(ei+1)+" > >> </button>");
        		 }
        	 })
    	 });
    	 getPager(curPage);
    	 
    	 $pageWidthTag.on("click",".pageNumButton",function(){
    		 var $this=$(this);
    		 var index= $this.attr("data-rect");
    		 var innerHtml=$this.html();
    		 if( innerHtml.match("&lt;&lt;") || innerHtml.match("&gt;&gt;")){
    			 getPager(index);
		      }else{
		    	  location.href="/board/list?page="+index+"&option="+option+"&keyword="+keyword;
		      }
    	 })
     })

     
	</script>
	</body>
	</html>