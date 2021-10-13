package hu.webuni.hr.minta.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.minta.config.HrConfigProperties;
import hu.webuni.hr.minta.config.HrConfigProperties.Smart;
import hu.webuni.hr.minta.model.Employee;

@Service
public class SmartEmployeeService implements EmployeeService {

	@Autowired
	HrConfigProperties config;

	@Override
	public int getPayRaisePercent(Employee employee) {
		
		double yearsWorked = ChronoUnit.DAYS.between(employee.getDateOfStartWork(), LocalDateTime.now()) / 365.0;
		
		Smart smartConfig = config.getSalary().getSmart();
		//1. megoldás
//		if(yearsWorked > smartConfig.getLimit3())
//			return smartConfig.getPercent3();
//		
//		if(yearsWorked > smartConfig.getLimit2())
//			return smartConfig.getPercent2();
//		
//		if(yearsWorked > smartConfig.getLimit1())
//			return smartConfig.getPercent1();
		
		TreeMap<Double, Integer> limits = smartConfig.getLimits();
		
		//2. megoldás
//		Integer maxLimit = null;
//		for(Entry<Double, Integer> entry: limits.entrySet()) {
//			if(yearsWorked > entry.getKey())
//				maxLimit = entry.getValue();
//			else
//				break;
//		}
//		
//		return maxLimit == null ? 0 : maxLimit;

		//3. megoldás
//		Optional<Double> optionalMax = limits.keySet().stream()
//				.filter(k -> yearsWorked >= k)
//				.max(Double::compare);
//		
//		return optionalMax.isEmpty() ? 0 : limits.get(optionalMax.get());

		
		//4. megoldás
		Entry<Double, Integer> floorEntry = limits.floorEntry(yearsWorked);
		return floorEntry == null ? 0 : floorEntry.getValue();
		
	}

}
