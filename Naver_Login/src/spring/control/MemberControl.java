package spring.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class MemberControl {
	
	@Autowired
	private HttpServletRequest request;

	// URL을 login.inc라고 요청하면 수행하는 메서드
	// 하는일 없고 login.jsp의 경로를 반환한다.
	@RequestMapping("/login")
	public String login(){
		// 상태 토큰으로 사용할 랜덤 문자열 생성
		String state = generateState();
		// 세션 또는 별도의 저장 공간에 상태 토큰을 저장
//				request.session().attribute("state", state);
		request.getSession().setAttribute("state", state);
//				return state;
		return "naver_login";
	}
	public String generateState()
	{
	    SecureRandom random = new SecureRandom();
	    return new BigInteger(130, random).toString(32);
	}

	
	@RequestMapping("/nextProcess")
	public String naverLogin_callback(){
		String resVal = (String) request.getAttribute("resVal");
		HashMap<String, String> map = null;
		ObjectMapper mapper = new ObjectMapper(); 
		//json정보로 가져온 응답정보 => Map형태로 변환후 접근 토큰 얻을꺼임
		try {
			map = mapper.readValue(resVal, HashMap.class);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		String token = map.get("access_token")==null?"":map.get("access_token");// 네이버 로그인 접근 토큰;
		
        String header = "Bearer " + token; // Bearer 다음에 공백 추가
        try {
            String apiURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            //가져온 사용자 정보 request에 세팅!
            HashMap<String, Map> m = mapper.readValue(response.toString(), HashMap.class);
            HashMap<String, String> resMap = (HashMap<String, String>) m.get("response");
            request.setAttribute("resMap", resMap);
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
		return "login_ok";
	}
	
}









