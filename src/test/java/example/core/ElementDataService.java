package example.core;

import java.time.LocalDate;

import io.github.xinyangpan.cucumber.element.BaseElement;
import io.github.xinyangpan.cucumber.element.service.ElementDataSuperService;

public class ElementDataService extends ElementDataSuperService<BaseElement> {

	public ElementDataService() {
		super(BaseElement::new);
		funcName2FunctionMap.put("dob", this::dob);
	}

	public long dob(String format) {
		LocalDate localDate = LocalDate.parse(format);
		return localDate.until(LocalDate.now()).getYears();
	}

}
