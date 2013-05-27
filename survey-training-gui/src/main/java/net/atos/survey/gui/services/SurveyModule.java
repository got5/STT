package net.atos.survey.gui.services;

import net.atos.survey.core.entity.Choice;
import net.atos.survey.core.usecase.ChoiceManager;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.services.ValueEncoderSource;

public class SurveyModule {

	@Contribute(ValueEncoderSource.class)
	public static void provideEncoders(
			MappedConfiguration<Class, ValueEncoderFactory> configuration,
			final  @Inject ChoiceManager choiceManager) {

		ValueEncoderFactory<Choice> factory = new ValueEncoderFactory<Choice>() {
			public ValueEncoder<Choice> create(Class<Choice> clazz) {
				return new ChoiceEncoder(choiceManager);
			}

		};
		configuration.add(Choice.class, factory);
	}

	public static void contributeApplicationDefaults(
			MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.START_PAGE_NAME, "Index");
		configuration.add(SymbolConstants.PRODUCTION_MODE, "true");
		configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0.1.2");
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "fr,en");

		configuration.add(SymbolConstants.HMAC_PASSPHRASE,
				"GPS_rules_the_world_689");

	}

	public static void contributeIgnoredPathsFilter(
			Configuration<String> configuration) {
	}

//	public static void bind(ServiceBinder binder) {
//		binder.bind(ChoiceManager.class,ChoiceManagerImpl.class);
//	
//	}
	
//	public ChoiceManager buildChoiceManager(){
//		return BeanHelper.get(ChoiceManager.class);
//	}
}
