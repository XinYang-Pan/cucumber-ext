package example.test;

import static java.util.Locale.ENGLISH;

import java.lang.reflect.Type;
import java.util.Locale;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterByTypeTransformer;

public class TypeRegistryConfiguration implements TypeRegistryConfigurer {

	@Override
	public Locale locale() {
		return ENGLISH;
	}

	@Override
	public void configureTypeRegistry(TypeRegistry typeRegistry) {
		typeRegistry.setDefaultParameterTransformer(new Transformer());
	}

	private class Transformer implements ParameterByTypeTransformer {

		@Override
		public Object transform(String s, Type type) throws Exception {
			Class<?> clazz = (Class<?>) type;
			return clazz.getConstructor(String.class).newInstance(s);
		}

	}
}