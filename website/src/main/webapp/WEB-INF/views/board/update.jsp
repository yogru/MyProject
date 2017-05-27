<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- BASIC FORM ELELEMNTS -->
<div class="row mt">
	<div class="col-lg-12">
		<div class="form-panel">
			<h4 class="mb">
			</h4>
			<form class="form-horizontal style-form" id="regPost"
				method="post" action="/board/update">
				<div class="form-group">
					<label class="col-sm-2 col-sm-2 control-label">제목</label>
					<div class="col-sm-10">
						<input name="title" id="Inp_title" type="text" class="form-control"
							style="width: 550px">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 col-sm-2 control-label">작성자</label>
					<div class="col-sm-10">
						<label id="writer" class="col-sm-2 col-sm-2 control-label">작성자</label>
						<input name="writer" id="Inp_writer" type="hidden" class="form-control"
							style="width: 550px">
						
					</div>
				</div>
				
				<!--  editor -->
				<%@ include file="../editor/editor.jsp"%>
				
			</form>
		</div>
	</div>
	<!-- col-lg-12-->
</div>
<!-- /row -->


<div align="right">
	<button type="button" class="btn btn-success" id="regiBtn" onclick="saveContent()">
		<i class="fa fa-upload" aria-hidden="true"></i> 등록하기
	</button>
	<button type="button" class="btn btn-warning" id="refrBtn" onclick="reload()" >
		<i class="fa fa-refresh" aria-hidden="true"></i> 다시쓰기
	</button>
	<button type="button" class="btn btn-danger" id="updateToView" onclick="updateToView()">
		<i class="fa fa-times" aria-hidden="true"></i> 돌아가기
	</button>
</div>
<script type="text/javascript">
  function reload(){
	  location.href=location.href;
  }
  function updateToView(){
	  // 새로 고침하면, 아직 bno값이 정해지지 않는 이미지들은 
	  // 유실된다. 방법은 에디터에서 이미지태그들을  구해서 
	  // 새로운 레스트컨트롤러만들어 그 이미지태그들을 보내고
	  // 데이터베이스에서 bno값이 null인 이미지 파일들을 삭제하는 방법인데 
	  // 그런 방식이 아닌 에디터 객체에 콜백함수를 등록해서 세련되게 하고싶은데
	  // 지금 솔직히 내가 에디터 자체를 다시 만들고 싶다.
	  location.href="/board/view?bno=${BoardVO.bno}";
  }
</script>


<%@ include file="../include/footer.jsp"%>

<script type="text/javascript">
	/* 예제용 함수 */
	var $Inp_title=$("#Inp_title");
	var $Label_writer=$("#writer");
	var $updatePost=$("#regPost");
	
	var fnoList='${fnos}'.replace(/\s/gi, "").replace("[","").replace("]","").split(",");
	                      //중간 공백 제거하는 정규식. 
	$Inp_title.val('${BoardVO.title}');
	$Label_writer.html('${BoardVO.writer}');
	$("#Inp_writer").val('${BoardVO.writer}');
	
	console.log(fnoList);
	
	var deleteFnoList=[];
	
	var attachments = {};
	attachments['image'] = [];
   if(fnoList[0]!==""){//아 이해가 안되네, 공백문자 배열 때문에 , 어태치 하나 붙으니까 에디터 완전꼬임
	for(var i=0,len = fnoList.length;i<len ; i++){
	 (function(lFno){// for 문에서 i값 납치해오자 ㅇㅇ
	   attachments['image'].push({
	      'attacher': 'image',
	       'data': {
	     	  'fno':lFno, // 내가 추가한 속성
	          'imageurl':'/upload/getOriginImage/'+lFno,
	          'filename': lFno,
	          'filesize': 640,
	          'originalurl':'/upload/getOriginImage/'+lFno,
	          'thumburl': '/upload/getThunmNailImage/'+lFno,
	          callbackDelAfter:function(){ 
	        	  deleteFnoList.push(lFno); 
	        	  console.log("삭제 예정 파일들.",deleteFnoList);
			 }
	      }
	    });
	  })(fnoList[i])
	}} 
	/*파일 처리 안함; 언젠간 해야될텐데 씨벌
	attachments['file'] = [];
	attachments['file'].push({
	    'attacher': 'file',
	    'data': {
	        'attachurl': 'http://cfile297.uf.daum.net/attach/207C8C1B4AA4F5DC01A644',
	        'filemime': 'image/gif',
	        'filename': 'editor_bi.gif',
	        'filesize': 640
	    }
	});*/
	/* 저장된 컨텐츠를 불러오기 위한 함수 호출 */
	Editor.modify({
	    "attachments": function() { /* 저장된 첨부가 있을 경우 배열로 넘김, 위의 부분을 수정하고 아래 부분은 수정없이 사용 */
	        var allattachments = [];
	        for (var i in attachments) {
	        	console.log(attachments);
	        	
	            allattachments = allattachments.concat(attachments[i]);
	        }
	        return allattachments;
	    }(),
	    "content":'${BoardVO.content}'/* 내용 문자열, 주어진 필드(textarea) 엘리먼트 */
	});
	
	
	function saveContent() {
		Editor.save(); // 이 함수를 호출하여 글을 등록하면 된다.
	}
	/**
	 * Editor.save()를 호출한 경우 데이터가 유효한지 검사하기 위해 부르는 콜백함수로
	 * 상황에 맞게 수정하여 사용한다.
	 * 모든 데이터가 유효할 경우에 true를 리턴한다.
	 * @function
	 * @param {Object} editor - 에디터에서 넘겨주는 editor 객체
	 * @returns {Boolean} 모든 데이터가 유효할 경우에 true
	 */
	function validForm(editor) {
		// Place your validation logic here
		// sample : validate that content exists
		var title= $Inp_title.val();
	    var writer = $Label_writer.html();
		
		var validator = new Trex.Validator();
		var content = editor.getContent();
		if (!validator.exists(content)) {
			alert('내용을 입력하세요');
			return false;
		}
		if(!title){
			alert('제목을 입력하세요 ');
			return false;
		}
		return true;
	}
	/**
	 * Editor.save()를 호출한 경우 validForm callback 이 수행된 이후
	 * 실제 form submit을 위해 form 필드를 생성, 변경하기 위해 부르는 콜백함수로
	 * 각자 상황에 맞게 적절히 응용하여 사용한다.
	 * @function
	 * @param {Object} editor - 에디터에서 넘겨주는 editor 객체
	 * @returns {Boolean} 정상적인 경우에 true
	 */
	function setForm(editor) {
		var i, input;
		var form = editor.getForm();
		var content = editor.getContent();

		// 본문 내용을 필드를 생성하여 값을 할당하는 부분
		var textarea = document.createElement('textarea');
		textarea.name = 'content';
		textarea.value = content;
		form.createField(textarea);

		/* 아래의 코드는 첨부된 데이터를 필드를 생성하여 값을 할당하는 부분으로 상황에 맞게 수정하여 사용한다.
		 첨부된 데이터 중에 주어진 종류(image,file..)에 해당하는 것만 배열로 넘겨준다. */
		var images = editor.getAttachments('image');
        var textAreaFno=[];		
		
		for (i = 0; i < images.length; i++) {
			// existStage는 현재 본문에 존재하는지 여부
			if (images[i].existStage) {
				// data는 팝업에서 execAttach 등을 통해 넘긴 데이터
			
				/* alert('attachment information - image[' + i + '] \r\n'
						+ JSON.stringify(images[i].data));
				 */
				input = document.createElement('input');
				input.type = 'hidden';
				input.name = 'attach_image';
				input.value = images[i].data.imageurl; // 예에서는 이미지경로만 받아서 사용
				textAreaFno.push(images[i].data.fno);
				console.log(images[i].data)
				form.createField(input);
			}
		}
		
		// fno 파싱하자..
		$updatePost.append("<input name='fno' value='"+textAreaFno+"' >");
		// delete 예정인 fno 파싱하자.
		$updatePost.append("<input name='delfno' value='"+deleteFnoList+"' >");
		$updatePost.append("<input name='bno' value='"+${BoardVO.bno}+"' >");
		var files = editor.getAttachments('file');
		for (i = 0; i < files.length; i++) {
			input = document.createElement('input');
			input.type = 'hidden';
			input.name = 'attach_file';
			input.value = files[i].data.attachurl;
			form.createField(input);
		}
		return true;
	}
</script>
</body>
</html>