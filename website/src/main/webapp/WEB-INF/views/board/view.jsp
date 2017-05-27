<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<h1>viewsssssssssssssssss</h1>

<div class="row mt">
	<div class="col-lg-12">

		<div class="showback">
			<label class="col-sm-2 col-sm-2 control-label">제목:</label>
			${boardVO.title}
		</div>

		<div class="showback">
			<label class="col-sm-2 col-sm-2 control-label">작성자:</label>
			${boardVO.writer}
		</div>
		<label class="col-sm-2 col-sm-2 control-label">내용:</label>
		<div class="showback">${boardVO.content}</div>
	</div>
</div>

<div align="right">


	<button type="button" class="btn btn-success" id="toListBtn"
		onclick="toList()">
		<i class="fa fa-upload" aria-hidden="true"></i> 목록으로
	</button>

	<button type="button" class="btn btn-warning" id="updateBtn"
		onclick="toUpdatePage()">
		<i class="fa fa-refresh" aria-hidden="true"></i> 수정
	</button>

	<button type="button" class="btn btn-danger" id="deleteBtn"
		onclick="delBtn()">
		<i class="fa fa-times" aria-hidden="true"></i> 삭제
	</button>
</div>



<!-- BASIC FORM ELELEMNTS -->
<div class="row mt">
	<div class="col-lg-12">
		<div class="form-panel">
			<h3>댓글</h3>
			<h4 class="mb"></h4>
			<form class="form-horizontal style-form" id="replyForm" method="post"
				onsubmit="return false;">
				<div class="form-group">
					<label class="col-sm-2 col-sm-2 control-label">작성자</label>
					<div class="col-sm-10">

						<input name="bno" id="inp_bno" type="hidden" class="form-control"
							style="width: 250px" value="${boardVO.bno}"> <input
							name="writer" id="reply_writer" type="text" class="form-control"
							style="width: 250px">

					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 col-sm-2 control-label">내용</label>
					<div class="col-sm-10">
						<textarea name="content" id="tx_content"
							style="height: 100px; width: 500px;"></textarea>
						<button class="btn btn-success" onclick="regReply()">등록하기
						</button>
					</div>
				</div>



			</form>
		</div>
	</div>
	<!-- col-lg-12-->
</div>
<!-- /row -->

<div class="row mt">
	<div class="col-md-12">
		<section class="task-panel tasks-widget">
			<div class="panel-heading">
				<div class="pull-left">
					<h5>
						<i class="fa fa-tasks"></i> 댓글 리스트
					</h5>
				</div>
			</div>
			<div class="panel-body">
				<div class="task-content">
					<ul class="task-list" id="replyList">
					 <li>
					    <div class="showback">
      					<h4><i class="fa fa-angle-right"></i>작성자</h4>
						<div class="alert alert-warning alert-dismissable">
						  <button type="button" class="close" >×</button>
						  <strong>Warning!</strong> Better check yourself, you're not looking too good.
						 </div>      				
      				     </div>
					  </li>
					  
					   <li>
					    <div class="showback">
      					<h4><i class="fa fa-angle-right"></i> 작성자</h4>
						<div class="alert alert-warning alert-dismissable">
						  <button type="button" class="close"  >×</button>
						  <strong>Warning!</strong> Better check yourself, you're not looking too good.
						 </div>      				
      				     </div>
					  </li>
					 	
					</ul>
				</div>
			</div>
		</section>
	</div>
	<!-- /col-md-12-->
</div>

<div class="pageWidthTag">
</div>


<%@ include file="../include/footer.jsp"%>
<script>
	var bno = ${boardVO.bno};
	var size=10;
	var $replyForm = $("#replyForm");
	var $replyTxContent = $("#tx_content");
	var $replyInpWriter = $("#reply_writer");
    var $replyList=$("#replyList");
    var $pageWidthTag=$(".pageWidthTag");
    var curPage=1;
    
	 var getPager=(function(_page){
		 $.ajax({
   		 url:"/reply/getPager/"+_page+"/"+bno,
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
	 
	 var getContent=(function(_page){
		 $replyList.html("");
		 $.ajax({
	   		 url:"/reply/getReplyList/"+_page+"/"+bno+"/"+size,
	   		 success:function(data){
	   			 console.log(data);
	   			for(var i=0,len=data.length;i<len;i++){
	   				$replyList.append(
	   					"<li id=li"+data[i].rno+">"+
	 				    "<div class='showback'>"+
	 					"<h4><i class='fa fa-angle-right'></i>"+data[i].writer+"</h4>"+
	 					"<div class='alert alert-warning alert-dismissable'>"+
	 					"  <button type='button' class='close' data-rno="+data[i].rno+" >×</button>"+
	 					 data[i].content+
	 				     "</div>"+      				
	 				     "</div>"+
	 				  "</li>"
	   				 )
	   			} 
	   		 }
	   	 })
	 })
	 getContent(curPage);
	 
	 $("#replyList").on("click",".close",function(){
		 
		 var $that=$(this);
		 var rno = $that.attr("data-rno");
		 console.log("rno: ",rno);
		 
		 $.ajax({
	   		 url:"/reply/del/"+rno,
	   		 success:function(data){
	   			 console.log(data);
	   			 $("#li"+rno).remove();
	   		 }
	   	 })
	 })
	 
	 
	 $pageWidthTag.on("click",".pageNumButton",function(){
		 var $this=$(this);
		 var index= $this.attr("data-rect");
		 var innerHtml=$this.html();
		 if( innerHtml.match("&lt;&lt;") || innerHtml.match("&gt;&gt;")){
			 getPager(index);
	      }else{
	    	  curPage=index;
	    	  getContent(index);
	      }
	 })
    
	 
	 
    
    
    
	function toUpdatePage() {
		location.href = "/board/update?bno=" + bno;
	}

	function toList() { // view jsp 얻는  컨트롤러에서 키워드,옵션같은 객체를 받아서   
		var orgULR = document.location.href;// 모델로 넣어주는게 짜증나서 
		var param = orgULR.split('&'); // 그냥 이런식으로 url 파싱했다.
		var listURL = "/board/list?";
		for (var i = 1, len = param.length; i < len; i++) {
			listURL += "" + param[i] + "&";
		}
		listURL = listURL.slice(0, -1);
		console.log(listURL);
		document.location.href = listURL;
	}

	function delBtn() {
		$.ajax({
			url : "/board/delete?bno=" + bno,
			success : function(isdelete) {
				console.log(isdelete);
				if (isdelete) {
					alert("삭제 되었습니다.");
					location.href = "/board/list?page=1";
				}
			}
		})
	}

	function regReply() {
		var context = $replyTxContent.val();
		var writer = $replyInpWriter.val();

		if (!(context && writer)) {
			alert("입력이 잘못 되었습니다.");
			return;
		}

		var formData = $replyForm.serialize();
		$.ajax({
			url : "/reply/reg",
			type : "POST",
			data : formData,
			processData : false,
			success : function(isReg) {
				if (isReg) {
					alert("등록 되었습니다. ");
					getContent(curPage);
				}
			}
		})
		$replyTxContent.val("");
		$replyInpWriter.val("");
	}
</script>

</body>
</html>