<%@page import="jdk.nashorn.api.scripting.JSObject"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>네이버로그인</title>
  </head>
  <body>
  <%
    String clientId = "QMuwsFrPpZK7h1ZHgyKa";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "bEnoSb0P1a";//애플리케이션 클라이언트 시크릿값";
    String code = request.getParameter("code");
    String state = request.getParameter("state");
    String redirectURI = URLEncoder.encode("http://127.0.0.1:8088/Naver_Login/jsp/callback.jsp", "UTF-8");
    String apiURL;
    apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
    apiURL += "client_id=" + clientId;
    apiURL += "&client_secret=" + clientSecret;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&code=" + code;
    apiURL += "&state=" + state;
    String access_token = "";
    String refresh_token = "";
    System.out.println("apiURL="+apiURL);
    try {
      URL url = new URL(apiURL);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("GET");
      int responseCode = con.getResponseCode();
      BufferedReader br;
      System.out.print("responseCode="+responseCode);
      if(responseCode==200) { // 정상 호출
        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
      } else {  // 에러 발생
        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      String inputLine;
      StringBuffer res = new StringBuffer();
      while ((inputLine = br.readLine()) != null) {
        res.append(inputLine);
      }
      br.close();
      if(responseCode==200) {
    	  System.out.println("로그인 완료 *** " + res.toString());
    	  //접근 토큰 받아서 로그인된 사용자 정보 가져와야해서 응답정보 전체 session에 일단 넣겠음
    	  request.setAttribute("resVal", res.toString());
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    

    RequestDispatcher disp =
    	    request.getRequestDispatcher("../nextProcess.inc");
    	   //forward 수행
    	   disp.forward(request, response); 
//     response.sendRedirect("../nextProcess.inc"); 

    
  %>
  </body>
</html>
