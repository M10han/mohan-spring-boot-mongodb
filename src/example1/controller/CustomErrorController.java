package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class CustomErrorController {
	private static final String VIEW_PATH_ERROR = "pages/error";
	
	@RequestMapping(produces = "text/html")
	public ModelAndView onError(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView(VIEW_PATH_ERROR);
	}
	
	public String getErrorPath() {
		return VIEW_PATH_ERROR;
	}
}
