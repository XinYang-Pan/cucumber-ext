package example.service;

import io.github.xinyangpan.cucumber.element.BaseElement;
import io.github.xinyangpan.cucumber.element.service.ElementDataSuperService;

public class ElementDataService extends ElementDataSuperService<BaseElement> {

	public ElementDataService() {
		super(BaseElement::new);
	}

}
