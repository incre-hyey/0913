package spring.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;

@Controller
public class DelControl {

	@Autowired
	private BbsDAO b_dao;
	
	@RequestMapping(value="/delete",
			method=RequestMethod.POST)
	public ModelAndView delete(BbsVO vo)throws Exception{
		//post방식으로 delete.inc를 호출했을 때 수행
		// 이때
		// 파라미터가 seq, nowPage, pwd를 받게 된다.
		
		b_dao.delBbs(vo);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/list.inc?nowPage="+
				vo.getNowPage());
		
		return mv;
	}
}



