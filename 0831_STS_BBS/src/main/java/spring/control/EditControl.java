package spring.control;

import java.io.File;

import javax.servlet.ServletContext;

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
public class EditControl {

	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	private ServletContext ctx;
	
	private String uploadPath;	
	
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}


	// 수정화면으로 가려면 먼저 seq와 nowPage를
	// 파라미터로 받은 후 seq를 가지고 BbsVO를 검색한다.
	// 검색된 BbsVO를 ModelAndView에 저장한다.
	@RequestMapping(value="/edit", 
			method=RequestMethod.GET)
	public ModelAndView form(
			String seq, String nowPage)throws Exception{
		BbsVO vo = b_dao.getBbs(seq);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("vo", vo);
		mv.addObject("nowPage", nowPage);
		mv.setViewName("edit");
		
		return mv;
	}
	
	//edit.inc를 post방식으로 호출될 때 수행하는 기능
	@RequestMapping(value="/edit",
			method=RequestMethod.POST)
	public ModelAndView edit(BbsVO vo)throws Exception{
		
		//파일이 첨부되었는지 확인
		if(vo.getUpload().getSize() > 0){
			
			//파일을 저장할 곳("/upload")을 절대경로로 만든다.
			String path = ctx.getRealPath(uploadPath);
			
			//vo로부터 첨부파일을 얻어낸다.
			MultipartFile mf = vo.getUpload();
			
			//파일명 알아내기
			String fname = mf.getOriginalFilename();
			
			//이미 같은 이름으로 파일이 저장되었다면
			//파일명 뒤에 숫자를 붙여 변경하자!
			fname = FileUploadUtil.checkSameFileName(
					fname, path);
			
			//파일 저장!!
			mf.transferTo(new File(path, fname));
			
			vo.setUploadFileName(fname);
		}
		
		//ip 등을 변경하고자 한다면 이쯤에서 작업하면 된다.
		
		int cnt = b_dao.editBbs(vo);// 게시물 수정!
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("seq", vo.getSeq());
		mv.addObject("nowPage", vo.getNowPage());
		if(cnt == 0)
			mv.addObject("del_ok", "1");
		//수정에 실패했을 때만 mv에 "del_ok"라는 값이 저장됨!
		
		mv.setViewName("redirect:/view.inc");
		
		return mv;
	}
}










