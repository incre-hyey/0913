
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" 
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<link href="resources/css/text.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function download(fname){
	
		location.href="FileDownload?dir=upload&filename="+fname;
		//위의 FileDownload는 서블릿이다.
	}
	
	function del(){
		// 현재문서에서 아이디가 del_win인 요소를 찾아
		// style의 display값을 "block"으로 변경한다.
		document.getElementById(
			"del_win").style.display = "block";//보여주기
			
		//$("#del_win").css("display","block");
	}
	
	function del_cancel(){
		document.getElementById(
		"del_win").style.display = "none";
	}
	
	function closeWin(){
		// viewData라는 클래스가 적용되면서
		// 중요도가 최우선이 되었기 때문에
		// style.display = "none"으로 설정해도 소용없다.
		// 그러므로 viewData라는 클래스를 없앤다.
		// 방법1
		//document.getElementById("res_win").className = "";
		
		// 방법2
		//document.getElementById(
		//	"res_win").removeAttribute("class");
		
		// 방법3
		document.getElementById(
			"res_win").classList.remove("viewData");
		
		//jQuery사용
		// $("#res_win").removeClass("viewData");
	}
</script>
<style type="text/css">
	#del_win, #res_win{
		width: 250px;
		height: 90px;
		padding: 20px;
		border: 1px solid #00baee;
		border-radius: 10px;
		background-color: #efefef;
		text-align: center;
		position: absolute;
		top: 150px;
		left: 250px;
		display: none;
	}
	.viewData{
		display: block !important;
	}
</style>
</head>
<body>
<table width="556" border="0" cellspacing="0" cellpadding="0" align="center">
		  <tr>
		    <td align="center"><u><b>BBS 내용보기</b><u></td>
		  </tr>
		  <tr>
			<td height="2" bgcolor="#C3C3C3"></td>
		  </tr>
		  <tr>
			<td valign="top" bgcolor="#E5E5E5">

			<table width="100%" border="0" cellspacing="1" cellpadding="3">
				<tr>
				  <td width="80" height="20" align="center" bgcolor="#669AB3"><font color="#FFFFFF">글쓴이</font></td>
                  <td bgcolor="#F2F7F9">${vo.writer}</td>
				  <td width="80" align="center" bgcolor="#669AB3"><font color="#FFFFFF">등록일</font></td>
				  <td width="150" align="center" bgcolor="#F2F7F9">${vo.regdate}</td>
				</tr>
				<tr>
				  <td height="20" align="center" bgcolor="#669AB3"><font color="#FFFFFF">메일</font></td>
				  <td bgcolor="#F2F7F9"></td>
				  <td align="center" bgcolor="#669AB3"><font color="#FFFFFF">조회수</font></td>
				  <td align="center" bgcolor="#F2F7F9">${vo.hit}</td>
				</tr>
				<tr>
				  <td height="20" align="center" bgcolor="#669AB3"><font color="#FFFFFF">첨부파일</font></td>
				  <td colspan="3" bgcolor="#F2F7F9">
				  <a href="javascript:download('${vo.uploadFileName}')">
				  	${vo.uploadFileName}
				  </a>
				  </td>
				</tr>
				<tr>
				  <td height="20" align="center" bgcolor="#669AB3"><font color="#FFFFFF">제목</font></td>
				  <td colspan="3" bgcolor="#F2F7F9">${vo.title}</td>
				</tr>
				<tr valign="top">
				  <td height="23" colspan="4" bgcolor="#FFFFFF">
					<table width="100%" border="0" cellspacing="0" cellpadding="15">
					  <tr>
						<td valign="top"> <pre>${vo.content}</pre>
						  <p>&nbsp;</p></td>
					  </tr>
					</table></td>
				</tr>
			  </table>
			</td>
		  </tr>
		</table>
		<table width="556" border="0" cellspacing="0" cellpadding="0" align="center">
		  <tr>
			<td height="20" valign="middle"><img src="/images/sub_it/point_line.gif" width="556" height="3"></td>
		  </tr>
		  <tr>
			<td align="right"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td align="left">
					&nbsp;
				  </td>
				  <td width="241" align="right"><img src="resources/images/button/but_answer.gif" width="56" height="21" onClick="JavaScript:location.href='answer.inc?seq=${seq}&groups=${vo.groups }&step=${vo.step }&lev=${vo.lev }&nowPage=${nowPage }'" style="cursor:pointer">
					<img src="resources/images/button/but_modify.gif" width="56" height="21" onClick="javascript:location.href='edit.inc?seq=${seq}&nowPage=${nowPage }'" style="cursor:pointer">
					<img src="resources/images/button/but_list.gif" width="56" height="21" onClick="JavaScript:location.href='list.inc?nowPage=${nowPage}'" style="cursor:pointer">

					<img src="resources/images/button/but_undel.gif" width="56" height="21"
					onclick="del()" style="cursor:pointer">

					</td>
				</tr>
			  </table></td>
		  </tr>
		  <tr>
			<td height="19">&nbsp;</td>
		  </tr>		  

		</table>
		<iframe id="check_f" border='0' width="0" height="0" frameborder='0' marginwidth='0' marginheight='0' scrolling="no"></iframe>
		
		
		<div id="del_win">
			<form action="delete.inc" method="post">
				<input type="hidden" name="nowPage"
					value="${nowPage}"/>
				<input type="hidden" name="seq"
					value="${vo.seq}"/>
				<label for="pwd">비밀번호:</label>
				<input type="password" id="pwd" name="pwd"/>
				<br/>
				<input type="submit" value="삭제"/>
				<input type="button" id="cancel" value="취소"
				 onclick="del_cancel()"/>
			</form>
		</div>
		
		<div id="res_win" <c:if test="${param.del_ok != null }">class="viewData"</c:if>>
			
			<span style="color:#0066cc; font-weight:bold;">
				비밀번호가 틀립니다.!
			</span><p/>
			<input type="button" id="close" value="닫기"
				 onclick="closeWin()"/>
			
		</div>
  </body>
  </html>
  
  
  
  
  
  
  
  
  
  
