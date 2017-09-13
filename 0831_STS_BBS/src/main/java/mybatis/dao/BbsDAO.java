package mybatis.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import mybatis.vo.BbsVO;

public class BbsDAO {

	@Autowired
	private SqlSessionTemplate template;
	
	
	// ------ 비지니스 로직들 ----------------
	//전체 게시물의 수를 반환하는 기능
	public int getTotalCount(String bname){
		return template.selectOne("bbs.totalCount", bname);
	}
	
	//리스트 화면을 위한 목록 반환 기능
	//  (ListControl에서 호출함)
	public BbsVO[] getList(Map<String, String> map){
		List<BbsVO> list = template.selectList(
				"bbs.list", map);
		
		BbsVO[] ar = null; //반환값
		if(list != null && list.size() > 0){
			ar= new BbsVO[list.size()];
			
			//list의 요소들을 배열에 복사한다.
			list.toArray(ar);
		}
		return ar;
	}
	
	//원글을 저장하는 기능
	public boolean writeBbs(BbsVO vo){
		int cnt = template.insert("bbs.addBbs", vo);
		
		if(cnt > 0)
			return true;
		else
			return false;
	}
	
	//기본키(seq)를 인자로 받아 검색하여 BbsVO로 반환!
	public BbsVO getBbs(String seq){
		return template.selectOne("bbs.getBbs", seq);
	}
	
	//답변을 저장하기 전에 lev를 조정하는 기능
	public void updateLev(Map<String, String> map){
		template.update("bbs.updateLev", map);
	}
	
	//답변 저장 기능
	public void addAns(BbsVO vo){
		template.insert("bbs.addAns", vo);
	}
	
	//게시물을 수정하는 기능
	public int editBbs(BbsVO vo){
		return template.update("bbs.edit", vo);
		//수정된 행의 수가 반환됨!
	}
	
	//게시물을 삭제하는 기능
	public void delBbs(BbsVO vo){
		template.update("bbs.del", vo);
	}
}











