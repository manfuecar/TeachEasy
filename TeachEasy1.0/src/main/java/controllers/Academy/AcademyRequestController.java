
package controllers.Academy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AcademyService;
import services.CourseService;
import services.RequestService;
import controllers.AbstractController;
import domain.Academy;
import domain.Course;
import domain.Request;

@Controller
@RequestMapping("/academy/request")
public class AcademyRequestController extends AbstractController {

	@Autowired
	private RequestService	requestService;

	@Autowired
	private AcademyService	academyService;

	@Autowired
	private CourseService	courseService;


	// Constructors -----------------------------------------------------------

	public AcademyRequestController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> requests = new ArrayList<Request>();
		Map<Integer, Double> amount = new HashMap<Integer, Double>();

		Academy academy;
		academy = academyService.findByPrincipal();

		Collection<Course> courses = courseService.findByCreator(academy);
		for (Course c : courses) {
			for (Request r : c.getRequests()) {
				requests.add(r);
				amount.put(r.getId(), r.getRclass().getRate());
			}
		}
		result = new ModelAndView("request/list");
		result.addObject("requestURI", "academy/request/list.do");
		result.addObject("requests", requests);
		result.addObject("amount", amount);

		return result;
	}

	// Accept -----------------------------------------------------------

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam int requestId) {
		String msg=null;
		try {
			Request request = requestService.findOne(requestId);
			request.setStatus("AWAITING PAYMENT");
			requestService.save(request);
		} catch (Throwable oops) {
			String msgCode = "request.register.error";
			if (oops.getMessage().equals("notYours")){
				msgCode = "request.notYours";
				msg="This request is not yours,it has been redirected to your list";
			}
				
		}

		ModelAndView result=list();
		result.addObject("msg",msg);
		return result;
	}

	// Deny -----------------------------------------------------------

	@RequestMapping(value = "/deny", method = RequestMethod.GET)
	public ModelAndView deny(@RequestParam String requestId) {
		String msg=null;
		Request request=null;
		try {
			
			try{
				if(requestId.length()<10){
					int id = Integer.valueOf(requestId);
					 request = requestService.findOne(id);
				}else
					request=null;
				
				if(request==null){
					 msg = "request.notYours";
				}else{
					request.setStatus("DENIED");
					requestService.save(request);
				}
			}catch (Throwable oops) {
				ModelAndView result=list(); 
				 msg = "request.notYours";
				result.addObject("msg",msg);
			}	
			
		} catch (Throwable oops) {
			String msgCode = "request.register.error";
			if (oops.getMessage().equals("notYours")){
				msgCode = "request.notYours";
				msg="This request is not yours,it has been redirected to your list";
			}
				
		}

		ModelAndView result=list(); 
		result.addObject("msg",msg);
		return result;
	}

}
