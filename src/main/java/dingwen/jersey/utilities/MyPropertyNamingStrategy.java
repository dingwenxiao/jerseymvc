package dingwen.jersey.utilities;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class MyPropertyNamingStrategy extends PropertyNamingStrategy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6324284436153124776L;

	@Override
	public String nameForField(MapperConfig<?> config, AnnotatedField field,
			String defaultName) {
		return convert(field.getName());
	}

	@Override
	public String nameForGetterMethod(MapperConfig<?> config,
			AnnotatedMethod method, String defaultName) {
		return convert(method.getName().toString());
	}

	@Override
	public String nameForSetterMethod(MapperConfig<?> config,
			AnnotatedMethod method, String defaultName) {
		return convert(method.getName().toString());
	}

	private String convert(String input) {
		return input.substring(3);
	}
}
