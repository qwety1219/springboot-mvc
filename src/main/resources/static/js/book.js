/**
 * 功能:
 * 1.查詢全部
 * 2.查詢單筆
 * 3.顯示訊息
 * 4.顯示表格
 */
//API基本路徑
const API_BASE_URL="/book";
//抓取畫面元素(id)
const messageBox=document.getElementById("messageBox");
const searchId=document.getElementById("searchId");
const findOneBth=document.getElementById("findOneBth");
const findAllBth=document.getElementById("findAllBth");
const singleResult=document.getElementById("singleResult");
const bookTableBody=document.getElementById("bookTableBody");
//綁定查詢按鈕事件
findOneBth.addEventListener("click",findBookById);
findAllBth.addEventListener("click",findAllBooks);
//顯示訊息
function showMessage(message,type="info"){
	messageBox.textContent=message;
	messageBox.className=`message-box ${type}`;
}
//統一處理fetch回應
async function handleResponse(response){
	const result=await response.json();
	if(!response.ok){
		throw new Error(result.message||"發生錯誤");
	}
	return result;
}

//查詢單筆
async function findBookById(){
	
}
//查詢全部
async function findAllBooks(){
	try{
		const response=await fetch(API_BASE_URL);
		const result=await handleResponse(response);
		console.log(result)//debug用
		renderBookTable(result.data);//資浪渲染到表格中
		showMessage(result.message||"查詢全部成功","success");
	}catch(error){
		showMessage(error.message,"error");
	}
}
//渲染表格
function renderBookTable(books){
	if(!books||books.length===0){
		bookTableBody.innerHTML=`
			<tr>
				<td colspan="5" class="empty-now">目前沒有資料</td>
			</tr>
		`;
		return;
	}
	let html="";
	books.forEach(book=>{
		html+=`
			<tr>
				<td>${book.id}</td>
				<td>${book.name}</td>
				<td>${book.price}</td>
				<td>${book.amount}</td>
				<td>${book.pub}</td>
			</tr>
		`;
	});
	bookTableBody.innerHTML=html;
	
}