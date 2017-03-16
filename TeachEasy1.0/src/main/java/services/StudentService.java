
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.StudentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.CreditCard;
import domain.Finder;
import domain.Request;
import domain.SocialIdentity;
import domain.Student;
import form.StudentForm;

@Service
@Transactional
public class StudentService {

	// Managed repository

	@Autowired
	private StudentRepository	studentRepository;


	// Supporting services

	@Autowired
	private Validator validator;
	
	@Autowired
	private FinderService finderService;
	//Constructors
	public StudentService() {
		super();

	}

	// Simple CRUD methods
	public Student create() {
		Student result;
		Collection<SocialIdentity> socialIdentity = new ArrayList<SocialIdentity>();
		Collection<Comment> comments = new ArrayList<Comment>();
		Collection<Request> requests = new ArrayList<Request>();
		result = new Student();
		
		Finder finder=finderService.create();
		
		
		Finder f2 = finderService.save2(finder);
		result.setRequests(requests);
		result.setSocialIdentity(socialIdentity);
		result.setComments(comments);
		result.setFinder(f2);
		return result;
	}

	public Collection<Student> findAll() {
		Collection<Student> result;
		result = studentRepository.findAll();
		return result;
	}

	public Student findOne(int id) {
		Student result;
		result = studentRepository.findOne(id);
		return result;
	}

	public Student save(Student student) {
		Student result;
		
		String password = student.getUserAccount().getPassword();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String md5 = encoder.encodePassword(password, null);
		student.getUserAccount().setPassword(md5);
		
		result = studentRepository.save(student);
		return result;

	}

	public void delete(Student student) {
		studentRepository.delete(student);
	}
	
	// Form methods ------------------------------------------------

		public StudentForm generateForm() {
			StudentForm result;

			result = new StudentForm();
			return result;
		}

		public StudentForm generateForm(Student student) {
			StudentForm result;

			result = new StudentForm();

			result.setId(student.getId());
			result.setUsername(student.getUserAccount().getUsername());
			result.setPassword(student.getUserAccount().getPassword());
			result.setPassword2(student.getUserAccount().getPassword());
			result.setName(student.getName());
			result.setAgreed(true);
			result.setSurname(student.getSurname());
			result.setPhone(student.getPhone());
			result.setPicture(student.getPicture());
			result.setCreditCard(student.getCreditCard());
			result.setEmail(student.getEmail());
			result.setDate(student.getDate());
			result.setCity(student.getCity());
			result.setAddress(student.getAddress());

			return result;
		}

		public Student reconstruct(StudentForm studentForm, BindingResult binding) {

			Student result;

			String password;
			password = studentForm.getPassword();

			Assert.isTrue(studentForm.getPassword2().equals(password), "notEqualPassword");
			Assert.isTrue(studentForm.getAgreed(), "agreedNotAccepted");
			Assert.isTrue(check(studentForm.getCreditCard()), "badCreditCard");

			if(studentForm.getId()==0){
				result = create();
			}else{
				result = studentRepository.findOne(studentForm.getId());
			}
			UserAccount userAccount;
			userAccount = new UserAccount();
			userAccount.setUsername(studentForm.getUsername());
			userAccount.setPassword(password);

			Authority authority;
			authority = new Authority();
			authority.setAuthority(Authority.STUDENT);
			userAccount.addAuthority(authority);
			result.setUserAccount(userAccount);

			result.setName(studentForm.getName());
			result.setSurname(studentForm.getSurname());
			result.setEmail(studentForm.getEmail());
			result.setPhone(studentForm.getPhone());
			result.setPicture(studentForm.getPicture());
			result.setCreditCard(studentForm.getCreditCard());
			result.setCity(studentForm.getCity());
			result.setAddress(studentForm.getAddress());
			result.setDate(studentForm.getDate());

			validator.validate(result, binding);

			return result;

		}

		public static boolean check(CreditCard creditCard) {
			boolean validador = false;
			int sum = 0;
			Calendar fecha = Calendar.getInstance();
			String numero = creditCard.getNumber();
			int mes = fecha.get(Calendar.MONTH) + 1;
			int a�o = fecha.get(Calendar.YEAR);

			if (creditCard.getExpirationYear() > a�o) {
				validador = true;
			} else if (creditCard.getExpirationYear() == a�o) {
				if (creditCard.getExpirationMonth() >= mes) {
					validador = true;
				}
			}

			if (validador) {
				validador = false;
				for (int i = numero.length() - 1; i >= 0; i--) {
					int n = Integer.parseInt(numero.substring(i, i + 1));
					if (validador) {
						n *= 2;
						if (n > 9) {
							n = (n % 10) + 1;
						}
					}
					sum += n;
					validador = !validador;
				}
				if (sum % 10 == 0) {
					validador = true;
				}
			}

			return validador;
		}
		
	public Student findByPrincipal() {
		Student result;
		int userAccountId;

		userAccountId = LoginService.getPrincipal().getId();
		result = studentRepository.findByUserAccountId(userAccountId);

		return result;
	}
	
	public Student encryptCreditCard(Student student) {
		Student result = new Student();
		CreditCard caux = new CreditCard();
		String aux;

		result.setId(student.getId());
		result.setUserAccount(student.getUserAccount());
		result.setComments(student.getComments());
		result.setEmail(student.getEmail());
		result.setName(student.getName());
		result.setSurname(student.getSurname());
		result.setPhone(student.getPhone());
		result.setPicture(student.getPicture());
		result.setSocialIdentity(student.getSocialIdentity());
		result.setCity(student.getCity());
		result.setAddress(student.getAddress());
		result.setDate(student.getDate());

		caux.setBrandName(student.getCreditCard().getBrandName());
		caux.setCvv(student.getCreditCard().getCvv());
		caux.setExpirationMonth(student.getCreditCard().getExpirationMonth());
		caux.setExpirationYear(student.getCreditCard().getExpirationYear());
		caux.setHolderName(student.getCreditCard().getHolderName());
		aux = "************" + student.getCreditCard().getNumber().substring(12);
		caux.setNumber(aux);
		result.setCreditCard(caux);

		return result;
	}
}
