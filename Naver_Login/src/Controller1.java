

import javax.servlet.annotation.WebServlet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Servlet implementation class Controller1
 */
@WebServlet("*.inc")
public class Controller1 extends DispatcherServlet {
	// WEB-INF/{현재클래스명}-servlet.xml과 연동됨!

}
