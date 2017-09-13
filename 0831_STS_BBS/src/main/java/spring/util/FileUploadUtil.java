package spring.util;

import java.io.File;

public class FileUploadUtil {

	
	//파일이 저장될 때 같은 이름이 있다면
	// 파일명을 변경한 후 파일명을 반환하는 기능
	public static String checkSameFileName(
			String filename, String path){
		//파일명과 확장자를 가려낸다.
		// 가려내는 방법은 (.)을 뒤에서 부터 찾아서
		// 분리시키면 된다. ( test.txt )
		int period = filename.lastIndexOf(".");
		
		String f_name = filename.substring(0, period);//test
		String suffix = filename.substring(period);//.txt
		
		//전체경로 만들기(경로 + "/" + 파일명)
		//String saveFileName = path + "/" + filename;
		String saveFileName = path + 
			System.getProperty("file.separator") + filename;
		
		//파일명이 같이 것이 있는지를 알아내기 위해 무조건
		//java.io.File객체를 만들어서 exists()로 확인한다.
		File f = new File(saveFileName);
		
		//같은 이름이 있을 경우 이름뒤에 숫자를 붙여주기 위해
		//변수를 선언하자!
		int idx = 1;
		while(f != null && f.exists()){
			//같은 이름의 파일이 있는 경우!!
			
			//파일명 변경!
			StringBuffer sb = new StringBuffer();
			sb.append(f_name);
			sb.append(idx++);
			sb.append(suffix);
			
			filename = sb.toString();// test1.txt
			
			//다시 존재여부 확인을 위해 전체경로를
			// 다시 만들자!
			saveFileName = path +
			  System.getProperty("file.separator") + filename;
			
			
			//새로 만들어진 파일명과 경로를 이용하여
			// 새로운 경로를 위에서 생성했으니 다시
			// File객체를 생성하여 반복문을 수행하도록 한다.
			f = new File(saveFileName);			
			
		}// while문의 끝
		
		return filename;
	}
}








