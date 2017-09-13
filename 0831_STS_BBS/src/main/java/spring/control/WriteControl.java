package spring.control;

import java.io.File;

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
public class WriteControl {

	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	private ServletContext ctx;
	
	@Autowired
	private HttpServletRequest request;
	
	private String uploadPath; //"/upload"
	
	public void setUploadPath(String uploadPath){
		this.uploadPath = uploadPath;
	}
	
	//브라우저에서 writeForm.inc라고 호출할 때
	//write.jsp가 불려지도록 하는 mapping
	@RequestMapping("/writeForm")
	public String form(){
		return "write"; 
	}	
	
	//파라미터들을 받아 파일처리 후 dB에 저장하는 mapping
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public ModelAndView write(BbsVO vo)throws Exception{
		
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
		
		//db에 저장될 정보가 모두 vo에 저장되었다.
		b_dao.writeBbs(vo);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/list.inc");
		
		return mv;
	}
}










