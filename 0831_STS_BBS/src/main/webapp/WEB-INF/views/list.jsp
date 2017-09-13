
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.List, mybatis.vo.BbsVO" %>


<%-- JSP 태그라이브러리(JSTL)
	톰켓 사이트(http://tomcat.apache.org/taglibs/)에 접속하여
	화면 내용 상단에 있는 [Apache Standard Taglib]이라는
	링크부분을 선택한다.
	JSTL 1.1버전의 [download]선택함! 그리고 [binaries/]를 선택!
	화면 아래쪽의 [jakarta-taglibs-standard-1.1.2.zip]파일을
	다운로드 후 압축해제한다.
 --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

<link href="resources/css/text.css" rel="stylesheet" type="text/css">

</head>
<body topmargin=0 leftmargin=0 marginwidth="0" marginheight="0">

<!--주요내용시작 -->
<form name="ff2" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
	<td valign="top">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td align="center" height="10"></td>
		</tr>
		<tr>
		  <td align="center"><u><b>BBS 목록</b><u></td>
		</tr>
		<tr>
		  <td align="center" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td>&nbsp;</td>
			  </tr>
			</table>
			<table width="556" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td height="2" bgcolor="#C3C3C3"></td>
			  </tr>
			  <tr>
				<td bgcolor="#E5E5E5">
				<table width="100%" border="0" cellspacing="1" cellpadding="2">
					<tr>
					  <td height="20" align="center" bgcolor="#669AB3" width="56"><font color="#FFFFFF">번호</font></td>
					  <td height="20" align="center" bgcolor="#669AB3" width="270"><font color="#FFFFFF">제목</font></td>
					  <td height="20" align="center" bgcolor="#669AB3" width="80"><font color="#FFFFFF">글쓴이</font></td>
					  <td height="20" align="center" bgcolor="#669AB3" width="100"><font color="#FFFFFF">날짜</font></td>
					  <td height="20" align="center" bgcolor="#669AB3" width="50"><font color="#FFFFFF">조회수</font></td>
					</tr>
       <c:forEach var="vo" items="${list }" varStatus="stat">
					<tr>
					  <td bgcolor="#F2F7F9">
<!--                           <s:property value="rowTotal-((nowPage-1)*blockList+#stat.index)"/> -->
						${rowTotal-((nowPage-1)*blockList+stat.index) }
                      </td>
					  <td bgcolor="#F2F7F9" style="text-align:left">
					  	
					  	<%-- step값만큼 들여쓰기하는 반복문 --%>
					  	<c:forEach begin="1" end="${vo.step }">
					  		<c:out value="&nbsp;&nbsp;" escapeXml="false"/>
					  	</c:forEach>
					  	
					  	<%-- step이 0이 아닌 경우엔 화살표 이미지 출력 --%>
					  	<c:if test="${vo.step > 0 }">
					  		<img src="resources/images/arrow.JPG"/>
					  	</c:if>
					  	
<!--                           <s:a href="%{viewURL}"> -->
<!--                               <s:property value="title"/> -->
<!--                           </s:a> -->
						<a href="view.inc?seq=${vo.seq }&nowPage=${nowPage}"> 
							${vo.title }
						</a>
                      </td>
					  <td bgcolor="#F2F7F9">${vo.writer }</td>
					  <td bgcolor="#F2F7F9">${vo.regdate }</td>
					  <td bgcolor="#F2F7F9">${vo.hit }</td>
					</tr>
       </c:forEach>
       <c:if test="${empty list }">
				<tr>
				  <td bgcolor="#F2F7F9" colspan="5" height="70" align="center">등록된 게시물이 없습니다.</td>
				</tr>
       </c:if>
				  </table></td>
			  </tr>
			</table>
			<table width="556" border="0" cellspacing="0" cellpadding="0">
			  
			  <tr>
				<td align="right"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					  <td width="315" align="left">
                          ${pageCode }
					  </td>
					  <td width="241" align="right"> <img src="resources/images/but_write.gif" width="56" height="21" style="cursor:pointer" onClick="javascript:location.href='writeForm.inc'">
					  </td>
					</tr>
				  </table></td>
			  </tr>
			</table></td>
		</tr>
		<tr>
		  <td height="19">&nbsp;</td>
		</tr>
	  </table>
	</td>
  </tr>
</table>
</form>

 <!--주요내용끝 -->

</body>
</html>
