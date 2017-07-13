<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>

	#m_files{
		overflow: hidden;
	}
	
	#m_files div.m_file{
		width : 110px;
		height : 160px;
		padding : 5px;
		float: left;
		position: relative;
	}
	
	 div.m_file img{
		width : 100px;
		height : 150px;
	}
	
	#m_files .m_file > a.delFile{
		position : absolute;
		top : 10px;
		right : 10px;
		padding : 3px;
		color : black;
		background: rgba(255,255,255,0.6);
		border : none;
	}
	#m_files .m_file > a.delFile:hover{
		color : red;
		background: rgba(255,255,255,1.0);
	}
</style>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">REGISTER BOARD</h3>
				</div>
				<!-- BOX-header  -->
				
				<form role="form" method="post" action="modify" enctype="multipart/form-data">
					<div class="box-body">
							<input type="hidden" name="page" value=${cri.page }>
							<input type="hidden" name="perPageNum" value=${cri.perPageNum }>
							<input type="hidden" name="searchType" value=${cri.searchType }>
							<input type="hidden" name="keyword" value=${cri.keyword }>
							<input type="hidden" name="bno" value="${board.bno }">
							<input type="hidden" name="viewcnt" value="${board.viewcnt }">
							<input type="hidden" name="sRegdate" value="${board.regdate }">
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" class="form-control" placeholder="Enter Title" value="${board.title }">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" cols="" name="content" class="form-control" 
							placeholder="Enter Content">${board.content }</textarea>
							
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" placeholder="Enter Writer" value="${board.writer }">
						</div>
						<div class="form-group">
							<label>Files</label>
							<input type="file" name="addFiles" multiple="multiple"> 
							<div id="m_files">
								<c:if test="${board.files !=null}">
									<c:forEach items="${board.files }" var="file">
										<div class="m_file">
											<img src="displayFile?filename=${file}">
											<a class="delFile" href="${file }">X</a>
										</div>
										<input type="hidden" name="files" value="${file }">
									</c:forEach>
								</c:if>
								
							</div>
						</div> <!-- 업로드된 파일 이미지들 보기 -->
					<!-- BOX BODY -->
					<div class="box-footer">
						<button type="submit" class="btn btn-primary">Modify</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>

<script>

$(document).on("click", ".delFile", function(event) {
	event.preventDefault();
	alert("야호");
	console.log($(this).attr("href"));
	
	var inputTag = $("<input>");
	inputTag.attr("name", "delFiles");
	inputTag.attr("type", "hidden");
	inputTag.val($(this).attr("href"));

	$("#m_files").append(inputTag);
	
	$(this).parent().css("display","none");

})

</script>

<%@ include file="../include/footer.jsp" %>      