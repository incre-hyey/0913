<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  <%
    String clientId = "QMuwsFrPpZK7h1ZHgyKa";//애플리케이션 클라이언트 아이디값";
    String redirectURI = URLEncoder.encode("http://127.0.0.1:8088/Naver_Login/jsp/callback.jsp", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += "&client_id=" + clientId;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&state=" + state;
    session.setAttribute("state", state);
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta name="viewport" content = "width=device-width, height=device-height", initial-scale=1">

<title>Login</title>
<style>
	*{
		padding:0;
		margin: 0;
		box-sizing:border-box;
	}
	html{
		width :100%;
		height: 100%;
	}
	body{
		width:100%;
		height:100%;
		color:#000;
		background-color:#fff;
	}
	.container{
		width:100%;
		height:100%;
		display:flex;
		flex-flow : column wrap;
		align-items:center;
		justify-content:center;
	}
	#cardbox{
		width : 70%;
	}
	#iconImage{
		display : inline;
	}
	#titleText{
		font-size:1.4em;
		font-weight:bold;
		color:#777;
	}
	#contentsText{
		color:#999;
	}
	#form1{
		padding:1em;
	}
	.row{
		height:3em;
	}
	.col1{
		width: 5em;
	}
	.inputbox{
		width: 20em;
	}
	#buttonContainer{
		padding-top:0.6em;
		text-align: right;
	}
</style>
<link rel="stylesheet" type="text/css" href="../css/semantic.min.css">
<script src="../js/semantic.min.js"></script>
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
</head>
<body>

<div class="container">
	<div id="cardbox class="ui blue fluid card">
		<div class="content">
			<div class="left floated author">
<!-- 				<img id="iconImage" class="ui avatar image" src="../images/author.png"> -->
			</div>
			<div>
				<div id="titleText" class="head">로그인</div>
				<div id="contentsText" class="description">
					아이디와 비밀번호를 입력하고 로그인 하세요.
				</div>
			</div>
		</div>
		<form id="form1" method="post" action="login.inc">
			<table>
				<tr class="row">
				<td class="col1" ><label id="contentsText">아이디</label></td>
				<td class="col2" colspan="2">
					<div class="ui input">
						<input class="inputbox" type="text" name="id">
					</div>
				</td>
				<td></td>
				</tr>
				
				<tr class="row">
				<td class="col1"><label id="contentsText">비밀번호</label></td>
				<td class="col2" colspan="2">
					<div class="ui input">
						<input class="inputbox" type="password" name="password">
					</div>
				</td>
				<td></td>
				</tr>
				
				<tr valign="baseline">
				<td></td>
				<td>
					<div class="ui toggle checkbox">
						<input type="checkbox" name="svaeOption">
						<label>아이디 저장</label>
					</div>
				</td>
				<td id="buttonContainer" align="right">
					<input id="submitButton" class="ui primary button" type="submit" value="로그인" name=""
				</td>
				</tr>
				
				<tr>
				<td></td><td></td>
				<td id="buttonContainer" align="right">
				<a href="<%=apiURL%>"><img height="40" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
				</td>
				</tr>
			</table>
		</form>
	
	</div>
</div>
</body>
</html>