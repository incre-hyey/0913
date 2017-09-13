package spring.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import spring.util.Paging;

public class ListControl implements Controller {

	@Autowired
	private BbsDAO bbsDao;
	
	//페이징 기법을위한 변수들
	public static final int BLOCK_LIST = 5;
	public static final int BLOCK_PAGE = 3;//한블럭당 페이지 수
	
	int nowPage; // 현재 페이지 값
	int rowTotal; // 총 게시물 수
	String pageCode; //페이징 처리된 HTML코드
	
	//나중에 검색 기능을 위한 필요한 변수들
	String searchType, searchValue;
	
	@Override
	public ModelAndView handleRequest(
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		// 현재영역은 브라우저에서 사용자가
		// list.inc로 요청했을 때 수행하는 곳!!!
		
		// 현재 페이지 값 구하기
		String c_page = request.getParameter("nowPage");
		
		if(c_page == null)
			nowPage = 1;
		else
			nowPage = Integer.parseInt(c_page);
		
		//게시판을 구별하는 문자열
		String bname = request.getParameter("bname");
		if(bname == null)
			bname = "BBS";//일반 게시판
		
		//총 게시물의 수를 구한다.
		rowTotal = bbsDao.getTotalCount(bname);
		
		//페이징 기법을 구현하는 객체를 생성!
		Paging page = new Paging(
			nowPage, rowTotal, BLOCK_LIST, BLOCK_PAGE);
		
		//페이징 HTML코드 얻기 - JSP에서 표현해야 함!
		pageCode = page.getSb().toString();
		
		//페이징 구현객체를 통하여 begin과 end를 구한다.
		int begin = page.getBegin();
		int end = page.getEnd();
		
		//Map구조를 생성하여 필요한 변수들 지정(bname, 
		//	begin, end)
		Map<String, String> map =
			new HashMap<String, String>();
		map.put("bname", bname);
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		//목록 얻어내기
		BbsVO[] ar = bbsDao.getList(map);
		
		//반환객체인 ModelAndView를 생성한 후
		//필요한 정보들을 저장한다.
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", ar);
		mv.addObject("nowPage", nowPage);
		mv.addObject("pageCode", pageCode);
		mv.addObject("rowTotal", rowTotal);
		mv.addObject("blockList", BLOCK_LIST);
		
		mv.setViewName("list");//뷰 페이지 지정		
		
		return mv;
	}

}






