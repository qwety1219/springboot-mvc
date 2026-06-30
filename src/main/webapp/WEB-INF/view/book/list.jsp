<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Book List</title>
	</head>
	<body>
		<%@ include file="/WEB-INF/view/book/meau.jsp" %>
		<div>
			<form method="post" action="/ssr/book/add">
				書名: <input type="text" name="name" required /><br />
				價格: <input type="number" name="price" step="0.1" required /><br />
				數量: <input type="number" name="amount" required /><br />
				出刊: <input type="checkbox" name="pub" /><br />
				<button type="submit">送出</button>
			</form>
		</div>
		<div>
			<table border="1" id="bookTable">
				<thead>
					<tr>
						<th>序號</th><th>書名</th><th>價格</th><th>數量</th><th>出刊</th><th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="book" items="${ books }">
						<tr>
							<td>${ book.id }</td>
							<td>${ book.name }</td>
							<td>${ book.price }</td>
							<td>${ book.amount }</td>
							<td>${ book.pub }</td>
							<td>
								<button onclick="location.href='/ssr/book/edit/${ book.id }'">修改</button>
								<form style="display:inline" method="post" action="/ssr/book/delete/${ book.id }">
									<input type="hidden" name="_method" value="DELETE" />
									<button type="submit">刪除</button>	
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				
			</table>
		</div>
		
	</body>
</html>