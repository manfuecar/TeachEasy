
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.StudentService;
import domain.Student;
import form.StudentForm;

@Controller
@RequestMapping("/student")
public class StudentController extends AbstractController {

	// Services ------------------------------------------------

	@Autowired
	private StudentService	studentService;


	// Constructor ---------------------------------------------

	public StudentController() {
		super();
	}

	// Display ------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Student student;

		student = studentService.findByPrincipal();
		result = new ModelAndView("student/display");
		result.addObject("student", student);
		result.addObject("comments", student.getComments());
		result.addObject("requestURI", "student/display.do");
		result.addObject("socialIdentities", student.getSocialIdentity());

		return result;
	}

	// Creation ------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		StudentForm studentForm;

		studentForm = studentService.generateForm();
		result = createEditModelAndView(studentForm, null);

		return result;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid StudentForm studentForm, BindingResult binding) {
		ModelAndView result;
		Student student;

		if (binding.hasErrors()) {
			result = createEditModelAndView(studentForm, null);
		} else {
			try {
				student = studentService.reconstruct(studentForm, binding);

				if (student.getId() == 0) {
					studentService.save(student);
					result = new ModelAndView("redirect:../security/login.do");
				} else {
					studentService.save2(student);
					result = display();
				}

			} catch (Throwable oops) {
				String msgCode = "student.register.error";
				if (oops.getMessage().equals("notEqualPassword")) {
					msgCode = "student.register.notEqualPassword";
				} else if (oops.getMessage().equals("agreedNotAccepted")) {
					msgCode = "student.register.agreedNotAccepted";
				}
				if (oops.getMessage().equals("badCreditCard")) {
					msgCode = "student.badCreditCard";
				}else if (oops.getMessage().equals("not18Old"))
					msgCode = "student.register.not18Old";
				if(oops.getMessage().equals("notYou")){
					msgCode="student.notYou";
				}
				result = createEditModelAndView(studentForm, msgCode);
			}
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		StudentForm studentForm;

		studentForm = studentService.generateForm(studentService.findByPrincipal());
		result = new ModelAndView("student/edit");
		result.addObject("studentForm", studentForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save1(@Valid StudentForm studentForm, BindingResult binding) {
		ModelAndView result;
		Student student;

		if (binding.hasErrors()) {
			result = createEditModelAndView2(studentForm, null);
		} else {
			try {
				student = studentService.reconstruct(studentForm, binding);

				if (student.getId() == 0) {
					studentService.save(student);
					result = new ModelAndView("redirect:../security/login.do");
				} else {
					studentService.save2(student);
					result = display();
				}

			} catch (Throwable oops) {
				String msgCode = "student.register.error";
				if (oops.getMessage().equals("notEqualPassword")) {
					msgCode = "student.register.notEqualPassword";
				} else if (oops.getMessage().equals("agreedNotAccepted")) {
					msgCode = "student.register.agreedNotAccepted";
				}
				if (oops.getMessage().equals("badCreditCard")) {
					msgCode = "student.badCreditCard";
				}else if (oops.getMessage().equals("not18Old"))
					msgCode = "student.register.not18Old";
				if(oops.getMessage().equals("notYou")){
					msgCode="student.notYou";
				}
				result = createEditModelAndView2(studentForm, null);
			}
		}

		return result;

	}

	// Ancillary methods ---------------------------------------------------

	protected ModelAndView createEditModelAndView(StudentForm studentForm, String message) {
		ModelAndView result;

		result = new ModelAndView("student/register");
		result.addObject("studentForm", studentForm);
		result.addObject("message", message);

		return result;
	}
	
	protected ModelAndView createEditModelAndView2(StudentForm studentForm, String message) {
		ModelAndView result;

		result = new ModelAndView("student/edit");
		result.addObject("studentForm", studentForm);
		result.addObject("message", message);

		return result;
	}

}
