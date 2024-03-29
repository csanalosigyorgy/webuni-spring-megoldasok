package hu.webuni.hr.minta.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.minta.dto.EmployeeDto;
import hu.webuni.hr.minta.model.Employee;
import hu.webuni.hr.minta.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private Map<Long, EmployeeDto> employees = new HashMap<>();
	
	@Autowired
	private EmployeeService employeeService;
	
	//1. megoldás
//	@GetMapping
//	public List<EmployeeDto> getAll(){
//		return new ArrayList<>(employees.values());
//	}
//	
//	@GetMapping(params = "minSalary")
//	public List<EmployeeDto> getByMinSalary(@RequestParam int minSalary){
//		return employees.values().stream()
//				.filter(e -> e.getSalary() > minSalary)
//				.collect(Collectors.toList());
//	}
	
	@GetMapping
	public List<EmployeeDto> getEmployees(@RequestParam(required = false) Integer minSalary) {
		return minSalary == null ?
				new ArrayList<>(employees.values()):
					employees.values().stream()
					.filter(e -> e.getSalary() > minSalary)
					.collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getById(@PathVariable long id) {
		EmployeeDto employeeDto = employees.get(id);
		if(employeeDto != null)
			return ResponseEntity.ok(employeeDto);
		else
			return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
		employees.put(employeeDto.getId(), employeeDto);
		return employeeDto;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
		if(!employees.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		
		employeeDto.setId(id);
		employees.put(id, employeeDto);
		return ResponseEntity.ok(employeeDto);
	}
	
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable long id) {
		employees.remove(id);
	}
	
	@PostMapping("/payRaise")
	public int getPayRaise(@RequestBody Employee employee) {
		return employeeService.getPayRaisePercent(employee); 
	}
}
