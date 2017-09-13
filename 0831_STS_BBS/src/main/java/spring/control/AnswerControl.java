package spring.control;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import spring.util.FileUploadUtil;

@Controller
public class AnswerControl {

	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	private ServletContext ctx;
	
	@Autowired
	private HttpServletRequest request;
	
	private String uploadPath;

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	
	//view.jsp에서 [답변] 버튼을 클릭했을 때 수행하는 부분
	//전달되는 파라미터(seq, groups, step, lev, nowPage)를
	// BbsVO로 받는다.
	@RequestMapping(value="/answer",method=RequestMethod.GET)
	public ModelAndView answer(BbsVO vo)throws Exception{
		
		ModelAndView mv = new ModelAndView();
		//받은 인자를 mv에 저장
		mv.addObject("vo", vo);
		mv.setViewName("answer");
		
		return mv;
	}
	
	
	@RequestMapping(value="/answer",method=RequestMethod.POST)
	public ModelAndView addAns(BbsVO vo)throws Exception{
		System.out.println("::::::::::::::"+
	vo.getTitle()+"/"+vo.getGroups()+"/"+
				vo.getStep()+"/"+vo.getLev());
		//첨부파일이 있는지를 확인
		if(vo.getUpload().getSize() > 0){
			
			//파일 첨부시 파일명이 같은 파일이
			//이미 저장되었는지를 확인하기 위해
			//절대경로를 만들어야 한다.
			String path = ctx.getRealPath(uploadPath);
			
			//인자로 전달받은 vo에서 첨부파일만 얻어낸다.
			MultipartFile mf = vo.getUpload();
			
			//첨부파일의 실제이름
			String f_name = mf.getOriginalFilename();
			
			//파일명 체크!
			f_name = FileUploadUtil.checkSameFileName(
					f_name, path);
			
			mf.transferTo(new File(path, f_name));//파일 저장
			
			//DB에 저장할 파일명을 vo에 저장!
			vo.setUploadFileName(f_name);
		}else
			vo.setUploadFileName("");
		
		vo.setIp(request.getRemoteAddr());//ip저장!
		vo.setBname("BBS");
		
		//******************************
		//답변을 저장하기 전에 참조글의 groups와 같고,
		// 참조글의 lev보다 큰 값을 가진 게시물의
		// lev를 1씩 증가시켜주는 update를 수행해야 한다.
		Map<String, String> map = 
				new HashMap<String, String>();
		map.put("groups", vo.getGroups());
		map.put("lev", vo.getLev());
		
		b_dao.updateLev(map);// lev변경!!!!!!!!
		
		//이제 답변을 저장해야 한다. 이때 groups는 
		//그대로 사용해야하지만 step과 lev는 1씩 증가시켜야 함
		int step = Integer.parseInt(vo.getStep()) + 1;
		int lev = Integer.parseInt(vo.getLev()) + 1;
		
		//위에서 증가한 step과 lev를 vo에 다시 저장해야 함
		vo.setStep(String.valueOf(step));
		vo.setLev(String.valueOf(lev));
		
		//db에 저장될 정보가 모두 vo에 저장되었다.
		b_dao.addAns(vo);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/list.inc?nowPage="+
					vo.getNowPage());
		
		return mv;
	}
}






