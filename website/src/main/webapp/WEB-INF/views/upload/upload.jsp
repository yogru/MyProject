<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/latest/css/bootstrap.min.css">
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/latest/js/bootstrap.min.js"></script>


<!-- Trigger the modal with a button -->
<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Open Modal</button>
<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Modal Header</h4>
      </div>
      <div class="modal-body">
      
      <button id="addFileBtn">이미지 추가</button>
         
         <div class="fileNames">
            <ul class="fileUL">
            </ul>
         
         </div>
      
       <button id="modalBtn" >완료</button>
      
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>



<form target="zeroFrame" id="fileForm" action="upload" method="post" enctype="multipart/form-data">
 <input type='file' id='fileInput' name='file' style="display: none;">
 <button id="uploadSubmit" style="display: none;"></button>
</form>

<div id="bodyDiv">
  <ul id="bodyUL"></ul>
</div>
<div id="fileViewDiv">
    <div id="thumNailViewDiv"></div>
     <div ><ul id="tnNameUL"></ul> </div>
</div>


<iframe name="zeroFrame" id="zero"></iframe>

<script>
   var $addImageBtn=$("#addFileBtn");
   var $fileInput=$("#fileInput");
   var $submit=$("#uploadSubmit");
   var $fileForm=$("#fileForm");
   var $zeroStr = $("#zero").clone();
   var $fileUL=$(".fileUL");
   var $modalBtn=$("#modalBtn");
   var $bodyUL=$("#bodyUL");
   var $myModal=$("#myModal");
   var $thView=$("#thumNailViewDiv");
   var $thUL=$("#tnNameUL");
   
   $modalBtn.on("click",function(){
	  var ch=$fileUL.children();
	  console.log(ch);
	  for(var i=0,len=ch.length;i<len;i++){
		 var name=ch[i].getAttribute("data-name");
		  $bodyUL.append("<img data-name="+name+" src=/loadImg?fname="+name+"></img>");
		 var ogName=name.substring(name.indexOf("_")+1);
		  $thUL.append("<li data-name="+name+" class=thli>"+ogName+"<button class='liXbtn'> x </button></li>");
	  } 
	  $myModal.modal('hide');
	  $fileUL.html("");
   })
   
   /* 예쁘게 만드면 좋을거같은데 .
    $thUL.on("click",".thli",function(){
    	var name=$(this).attr("data-name");
    	name="-tn-"+name;
    	$thView.html("<img src=/loadImg?fname="+name+"></img>");
    }) */
    
    
    $thUL.on("click",".liXbtn",function(e){
    	e.stopPropagation();
    	var input = confirm('정말로 삭제 하시겠습니까?');
          if(!input)return;
          
    	var that=$(this);
    	var name=that.parent().attr("data-name");
    	console.log(name);
    	$.ajax({
    		url:"/delfile?fname="+name,
    	    success:function(data){
    	       console.log(data);
    	       if(data){
    	    	   var child=$bodyUL.children();
   	        	   console.log("child:",child);
   	        	  for(var i=0,len=$bodyUL.children.length;i<len;i++){
   	        		console.log("for:",child[i]);
   	        		var dataName=child[i].getAttribute("data-name");
   	        		 if(dataName==name){
   	        		 child[i].remove();
   	        		 that.parent().remove();
   	        		 return ;
   	        	    }}    	   
    	       }
    	    }
    	})
    })
   
   $addImageBtn.on("click",function(){
	   if($fileUL[0].childElementCount<5)
 	 	 $fileInput.trigger("click");
	   else 
		  alert("최대 파일 5개 까지 입니다.");
   });
   
   $fileInput.change(function(){
	 //  var that=$(this);
	   $submit.trigger("click");
   })
   
   $submit.on('click', function (e){
	   console.log("submit............");
	   $fileForm.submit();
   });
 
   function addName(name){
	  console.log(name);
	  var originName= name.substring(name.indexOf("_")+1);
	  var des=name.split("-tn-")[1];
	  $fileUL.append("<li data-name="+des+"><img src=/loadImg?fname="+name+">"+originName+"</img></li>");
	  
	  $("#zero").remove();
	  document.getElementById("fileForm").reset();
	  $("body").append($zeroStr);
  }
  
</script>
</body>
</html>