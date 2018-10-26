package xyz.mizan.springbootsecurity.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import xyz.mizan.springbootsecurity.boot.entity.Employee;
import xyz.mizan.springbootsecurity.boot.service.EmployeeServiceImp;


@Controller
@RequestMapping(value="/employee")
public class EmployeeController {

	@Autowired
	EmployeeServiceImp employeeServiceImp;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView goIndex() {
		ModelAndView employeeModel = new ModelAndView();
		Employee e = new Employee();
		List<Employee> employees = employeeServiceImp.selectEmployee();
		employeeModel.addObject("employeeForm", e);
		employeeModel.addObject("employees", employees);
		employeeModel.setViewName("employee");
		return employeeModel;
	}
	
	@RequestMapping(value="/insertPage", method=RequestMethod.GET)
	 public ModelAndView addArticle() {
	  ModelAndView model = new ModelAndView();
	  Employee e = new Employee();
	  model.addObject("employeeForm", e);
	  model.setViewName("employee");
	  return model;
	 }
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	 public ModelAndView addArticle(@PathVariable("id") int id) {
	  ModelAndView model = new ModelAndView();
	  Employee e = employeeServiceImp.selectEmployeeById(id);
	  List<Employee> employees = employeeServiceImp.selectEmployee();
	  model.addObject("employees", employees);
	  model.addObject("employeeForm", e);
	  model.setViewName("employee");
	  return model;
	 }
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public ModelAndView insertEmployee(@ModelAttribute("employeeForm") Employee e) {
		employeeServiceImp.insertEmployee(e);
		return new ModelAndView("redirect:/employee/list");
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteEmployee(@PathVariable("id") int id) {
		employeeServiceImp.deleteEmployee(id);
		return new ModelAndView("redirect:/employee/list");
	}
	
	
}
