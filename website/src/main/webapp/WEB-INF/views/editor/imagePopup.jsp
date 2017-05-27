<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Daum에디터 - 이미지 첨부</title>
<script src="/resources/daumOpenEditor/js/popup.js"
	type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="/resources/daumOpenEditor/css/popup.css"
	type="text/css" charset="utf-8" />


<script
  src="https://code.jquery.com/jquery-1.11.0.min.js"
  integrity="sha256-spTpc4lvj4dOkKjrGokIrHkJgNA0xMS98Pw9N7ir9oI="
  crossorigin="anonymous"></script>
<script>
	// <![CDATA[

	function done(fno,fname) {
		if (typeof (execAttach) == 'undefined') { //Virtual Function
			return;
		}

		var _mockdata = {
			'fno':fno, // 내가 추가한 속성
			'imageurl' : '/upload/getOriginImage/'+fno,
			'filename' :fno+"_"+fname,
			'filesize' : 640,
			'imagealign' : 'C',
			'originalurl' : '/upload/getOriginImage/'+fno,
			'thumburl' : '/upload/getThunmNailImage/'+fno,
			 callbackDelAfter:function(){ 
				$.ajax({ 
					url:'/upload/deleteFile/'+fno,
					success:function(_data){
						console.log(_data); 
					 }
				})
			}
		    //'/upload/deleteFile/'+fno// 내가 추가한 url
		};
		execAttach(_mockdata);
		closeWindow();
	}

	function initUploader() {
		var _opener = PopupUtil.getOpener();
		if (!_opener) {
			alert('잘못된 경로로 접근하셨습니다.');
			return;
		}

		var _attacher = getAttacher('image', _opener);
		registerAction(_attacher);
	}
	// ]]>
	
</script>
</head>
<body onload="initUploader();">
	<div class="wrapper">
		<div class="header">
			<h1>사진 첨부</h1>
		</div>
		<div class="body">
			<dl class=alert>
				<dt>10MB이하 (JPG,GIF,PNG,BMP)</dt>
				<dd>
				   <div class=file>
					<button id="addBtn" >파일 추가하기</button>
 				</div>
					<form  id="daumOpenEditorForm"  encType=multipart/form-data method=post
						action="/upload/reg" onsubmit="return false;" >
					
					
						<input type='file' id='fileInput' name='file'
							style="display: none;">
						<button id="uploadSubmit" style="display: none;"></button>
					</form>

					<div id="fileList">
						<ul id="fileUL">
						</ul>
					</div>
				</dd>
			</dl>
		</div>
		<div class="footer">
			<p>
				<a href="#" onclick="myClose();" title="닫기" class="close">닫기</a>
			</p>
			<ul>
				<li class="submit"><a href="#" onclick="myDone();" title="등록"
					class="btnlink">등록</a></li>
				<li class="cancel"><a href="#" onclick="myClose();"
					title="취소" class="btnlink">취소</a></li>
			</ul>
		</div>
	</div>
<script>
// zaku custom code 
// 안보이는 파일 인풋 버튼으로 계속 파일 추가할 것이다 
console.log("제발");

var $addBtn = $("#addBtn");
var $fileUL = $("#fileUL");
var $fileInput = $("#fileInput");
var $uploadSubmit=$("#uploadSubmit");
var formTag=document.querySelector("#daumOpenEditorForm");


function myDone(){
	  var ch=$fileUL.children();
	  console.log(ch);
	  for(var i=0,len=ch.length;i<len;i++){
		 var fname=ch[i].getAttribute("data-fname");
		 var fno=ch[i].getAttribute("data-fno");
		  done(fno,fname);
	  } 
	  $fileUL.html("");
}

function myClose(){
	  var ch=$fileUL.children();
	  console.log(ch);
	  var len=ch.length;
	  if(len===0)closeWindow(); 
	  
	  var wait=0;
	  for(var i=0;i<len;i++){
			console.log(i);
		   var fname=ch[i].getAttribute("data-fname");
		   var fno=ch[i].getAttribute("data-fno");
			$.ajax({ // 이코드 존나 중복되는데 attachbox_ui.js로 객체로 보낼때 함수로 안보내져서 , 일단 이렇게함
				url:'/upload/deleteFile/'+fno,
				success:function(data){
					console.log(data); 
					wait+=1;
					if(wait===len) //기달려주자 다 삭제 될 때까지 ....
					closeWindow(); 
				 }
			})
	  } 
}

$addBtn.on("click", function() { //일종의 대리자 버튼 
 console.log("addbtn-");
	
	if ($fileUL[0].childElementCount < 5)
	$fileInput.trigger("click");
  else
	alert("최대 파일 5개 까지 입니다.");
})

$fileInput.change(function() {
	$uploadSubmit.trigger("click");
})

 $uploadSubmit.on('click', function(e) {
		e.stopPropagation();
       console.log("submit............");
       var formdata = new FormData(formTag);
 	   $.ajax({
    	   url: "/upload/reg",
    	   type: "POST",
    	   data: formdata,
    	   processData: false,  // tell jQuery not to process the data
    	   contentType: false ,  // tell jQuery not to set contentType,
    	   success : function(data){
     	    console.log(data);
     	   // alert(data.fno);
     	    $fileUL.append("<li class='fileLi' data-fname="+data.fname+" data-fno="+data.fno+" ><img src='/upload/getThunmNailImage/"+data.fno+"'>"+data.fname+"</li>");
     	 } 
   		}); 
    formTag.reset();
 });
</script>


	
</body>
</html>