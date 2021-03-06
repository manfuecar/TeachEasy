
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CourseRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Academy;
import domain.Course;
import domain.Finder;

@Service
@Transactional
public class CourseService {

	// Managed repository

	@Autowired
	CourseRepository	courseRepository;

	// Supporting services

	@Autowired
	AcademyService		academyService;


	//Constructors
	public CourseService() {
		super();

	}

	// Simple CRUD methods
	public Course create() {
		Course result;
		Academy a = academyService.findByPrincipal();
		result = new Course();
		result.setAcademy(a);
		result.setCreateMoment(new Date());
		result.setUpdateMoment(new Date());
		result.setAvailable(true);
		return result;
	}

	public Collection<Course> findAll() {
		Collection<Course> result;
		result = courseRepository.findAll();
		return result;
	}

	public Course findOne(int id) {
		Course result;
		result = courseRepository.findOne(id);
		return result;
	}

	public Course save(Course course) {
		Authority a = new Authority();
		a.setAuthority(Authority.ACADEMY);
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Course result;
		result = new Course();
		
		Assert.isTrue(userAccount.getAuthorities().contains(a) && 
					course.getAcademy().getUserAccount().getUsername().equals(userAccount.getUsername()), "notYourCourse");
		
		Date date = new Date(System.currentTimeMillis() - 1000);
		result.setUpdateMoment(date);
		result = courseRepository.save(course);
		
		
		return result;

	}

	public void delete(Course course) {
		courseRepository.delete(course);
	}
	public Collection<Course> findByFinder(Finder finder) {
		Collection<Course> result = new ArrayList<Course>();
		Collection<Course> aux;
		Collection<Course> aux2 = new ArrayList<Course>();
		if (finder.getKeyword() == null && finder.getMatter() == null) {
			aux = courseRepository.findByCity(finder.getCity());
		} else if (finder.getMatter() == null) {
			aux = courseRepository.findByKey(finder.getKeyword(), finder.getCity());
		} else if (finder.getKeyword() == null) {
			aux = courseRepository.findByMatter(finder.getMatter(), finder.getCity());
		} else {
			aux = courseRepository.findByMatterAndKey(finder.getMatter(), finder.getCity(), finder.getKeyword());
		}
		if (finder.getMinimumPrice() == null && finder.getMaximumPrice() == null) {
			result = aux;
		} else if (finder.getMinimumPrice() == null) {
			for (Course p : aux) {
				if (p.getRate() <= finder.getMaximumPrice()) {
					result.add(p);
				}
			}
		} else if (finder.getMaximumPrice() == null) {
			for (Course p : aux) {
				if (p.getRate() >= finder.getMinimumPrice()) {
					result.add(p);
				}
			}
		} else {
			for (Course p : aux) {
				if (p.getRate() >= finder.getMinimumPrice() && p.getRate() <= finder.getMaximumPrice()) {
					result.add(p);
				}
			}
		}

		for (Course c : result) {
			if (c.getAvailable() == true) {
				aux2.add(c);
			}
		}
		return aux2;

	}

	public Collection<Course> findByCreator(Academy academy) {
		Collection<Course> result = courseRepository.findByCreator(academy);
		return result;
	}

	public Collection<Course> findAvailable() {
		Collection<Course> result = courseRepository.findAvailable();
		return result;
	}
}
