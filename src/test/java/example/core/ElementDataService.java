package example.core;

import java.time.LocalDate;
import java.time.Month;

import io.github.xinyangpan.cucumber.element.BaseElement;
import io.github.xinyangpan.cucumber.element.service.ElementDataSuperService;

public class ElementDataService extends ElementDataSuperService<BaseElement> {

	public ElementDataService() {
		super(BaseElement::new);
		funcName2FunctionMap.put("dob", this::dob);
	}

	public long dob(String format) {
		LocalDate localDate = LocalDate.parse(format);
		return localDate.until(LocalDate.of(2017, Month.JANUARY, 1)).getYears();
	}

}
