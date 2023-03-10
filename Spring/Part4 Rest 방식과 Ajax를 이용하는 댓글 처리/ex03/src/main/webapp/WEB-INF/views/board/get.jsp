<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../includes/header.jsp"%>


<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Read</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">

			<div class="panel-heading">Board Read Page</div>
			<!-- /.panel-heading -->
			<div class="panel-body">

				<div class="form-group">
					<label>Bno</label> <input class="form-control" name='bno' value='<c:out value="${board.bno }"/>' readonly="readonly">
				</div>

				<div class="form-group">
					<label>Title</label> <input class="form-control" name='title' value='<c:out value="${board.title }"/>' readonly="readonly">
				</div>

				<div class="form-group">
					<label>Text area</label>
					<textarea class="form-control" rows="3" name='content' readonly="readonly"><c:out value="${board.content}" /></textarea>
				</div>

				<div class="form-group">
					<label>Writer</label> <input class="form-control" name='writer' value='<c:out value="${board.writer }"/>' readonly="readonly">
				</div>

				<button data-oper='modify' class="btn btn-default">Modify</button>
				<button data-oper='list' class="btn btn-info">List</button>

				<%-- <form id='operForm' action="/boad/modify" method="get">
  <input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno}"/>'>
</form> --%>


				<form id='operForm' action="/boad/modify" method="get">
					<input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno}"/>'> <input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'> <input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'> <input type='hidden' name='keyword' value='<c:out value="${cri.keyword}"/>'> <input type='hidden' name='type' value='<c:out value="${cri.type}"/>'>

				</form>



			</div>
			<!--  end panel-body -->

		</div>
		<!--  end panel-body -->
	</div>
	<!-- end panel -->
</div>
<!-- /.row -->

<div class="col-lg-12">
	<div class="panel panel-default">
		<div class="panel-heading">
			<i claass="fa fa-comments fa-fw"></i> Reply
		</div>
		<!-- /.panel-heading -->
		<div class="panel-body">
			<ul class="chat">
				<!-- start reply -->
				<li class="left clearfix" data-rno='12'>
					<div>
						<div class="header">
							<strong class="primary-font">user00</strong> <small class="pull-right text-muted">2018-01-01 13:13</small>
						</div>
						<p>Good job!</p>
					</div>
				</li>
				<!--  end reply -->
			</ul>
			<!--  ./end ul -->
		</div>
		<!-- ./ end row -->
	</div>
</div>
</div>

<script type="text/javascript" src="/resources/js/reply.js"></script>

<script>
	console.log("===============");
	console.log("JS TEST");

	var bnoValue = '<c:out value="${board.bno}"/>';

	//for replyService add test
	/* replyService.add(

	{
		reply : "JS Test",
		replyer : "tester",
		bno : bnoValue
	}, function(result) {
		alert("RESULT: " + result);
	}); */

	//reply List Test
	/* 	replyService.getList({
	 bno : bnoValue,
	 page : 1
	 }, function(list) {

	 for (var i = 0, len = list.length || 0; i < len; i++) {
	 console.log(list[i]);
	 }
	 }); */

	// rno 34, bno 23 번 댓글 삭제 테스트 
	/* replyService.remove(34, function(count) {

		console.log(count);

		if (count === "success") {
			alert("REMOVED");
		}
	}, function(err) {
		alert('ERROR...');
	}); */

	// rno 69번 댓글 삭제 테스트 
	/* 	replyService.update({
	 rno : 69,
	 bno : bnoValue,
	 reply : "Modified Reply...."
	 }, function(result) {
	 alert("수정 완료...");
	 }); */

	// 댓글 번호 rno를 전달
	var bnoValue = '<c:out value="${board.bno}"/>';
	replyService.get(69, function(data) {
		console.log(data);
	});
</script>

<script type="text/javascript">
	$(document).ready(function() {
		console.log(replyService);
	});
</script>

<script type="text/javascript">
	$(document).ready(function() {

		var operForm = $("#operForm");

		$("button[data-oper='modify']").on("click", function(e) {

			operForm.attr("action", "/board/modify").submit();

		});

		$("button[data-oper='list']").on("click", function(e) {

			operForm.find("#bno").remove();
			operForm.attr("action", "/board/list")
			operForm.submit();

		});
	});
</script>

<%@include file="../includes/footer.jsp"%>
