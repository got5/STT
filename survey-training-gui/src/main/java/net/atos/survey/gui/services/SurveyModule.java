package net.atos.survey.gui.services;

import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.Choice;
import net.atos.survey.core.usecase.CategoryManager;
import net.atos.survey.core.usecase.ChoiceManager;
import net.atos.survey.core.usecase.InitManager;
import net.atos.survey.core.usecase.PDFGeneratorManager;
import net.atos.survey.core.usecase.ResponseSurveyManager;
import net.atos.survey.core.usecase.SimpleMCQResponseManager;
import net.atos.survey.core.usecase.SimpleMCQuestionManager;
import net.atos.survey.core.usecase.SurveyManager;
import net.atos.survey.core.usecase.TrainingManager;
import net.atos.survey.core.usecase.TrainingSessionManager;
import net.atos.survey.core.usecase.UserManager;
import net.atos.xa.resourcelocator.ResourceLocator;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.ServiceBuilder;
import org.apache.tapestry5.ioc.ServiceResources;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.services.ValueEncoderSource;


public class SurveyModule {
	
	
	
	@Contribute(ValueEncoderSource.class)
	public static void provideEncoders(
			MappedConfiguration<Class, ValueEncoderFactory>
		configuration,
		@Local @Inject final ChoiceManager choiceManager){
		
		ValueEncoderFactory<Choice> factory = new ValueEncoderFactory<Choice>(){
			public ValueEncoder<Choice> create(Class<Choice> clazz){
				return new ChoiceEncoder(choiceManager);
			}
			
		};
		configuration.add(Choice.class, factory);
	}
	
	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.START_PAGE_NAME, "Index");
		configuration.add(SymbolConstants.PRODUCTION_MODE, "true");
		configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0.1.2");
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "fr,en");
		
		configuration.add(SymbolConstants.HMAC_PASSPHRASE, "GPS_rules_the_world_689");
		 
		
	}
	
	public static void contributeIgnoredPathsFilter(Configuration<String> configuration) {
	}
	
	public static void bind(ServiceBinder binder)
	{ 
		binder.bind(UserDao.class, new ServiceBuilder<UserDao>()  {
			public UserDao buildService(ServiceResources serviceResources) {
				return ResourceLocator.lookup(UserDao.class);
			}
		});
		binder.bind(InitManager.class, new ServiceBuilder<InitManager>()  {
			public InitManager buildService(ServiceResources serviceResources) {
				return ResourceLocator.lookup(InitManager.class);
			}
		});
		binder.bind(TrainingSessionManager.class, new ServiceBuilder<TrainingSessionManager>()  {
			public TrainingSessionManager buildService(ServiceResources serviceResources) {
				return ResourceLocator.lookup(TrainingSessionManager.class);
			}
		});
		
		binder.bind(TrainingManager.class, new ServiceBuilder<TrainingManager>()  {
			public TrainingManager buildService(ServiceResources serviceResources) {
				return ResourceLocator.lookup(TrainingManager.class);
			}
		});
		
		binder.bind(SurveyManager.class, new ServiceBuilder<SurveyManager>()  {
			public SurveyManager buildService(ServiceResources serviceResources) {
				return ResourceLocator.lookup(SurveyManager.class);
			}
		});
		
		binder.bind(CategoryManager.class, new ServiceBuilder<CategoryManager>()  {
			public CategoryManager buildService(ServiceResources serviceResources) {
				return ResourceLocator.lookup(CategoryManager.class);
			}
		});
		binder.bind(SimpleMCQuestionManager.class, new ServiceBuilder<SimpleMCQuestionManager>()  {
			public SimpleMCQuestionManager buildService(ServiceResources serviceResources) {
				return ResourceLocator.lookup(SimpleMCQuestionManager.class);
			}
		});
		binder.bind(SimpleMCQResponseManager.class, new ServiceBuilder<SimpleMCQResponseManager>()  {
			public SimpleMCQResponseManager buildService(ServiceResources serviceResources) {
				return ResourceLocator.lookup(SimpleMCQResponseManager.class);
			}
		});
		
		binder.bind(UserManager.class, new ServiceBuilder<UserManager>()  {
			public UserManager buildService(ServiceResources serviceResources) {
				return ResourceLocator.lookup(UserManager.class);
			}
		});
		
		binder.bind(ChoiceManager.class, new ServiceBuilder<ChoiceManager>()  {
			public ChoiceManager buildService(ServiceResources serviceResources) {
				return ResourceLocator.lookup(ChoiceManager.class);
			}
		});
		
		binder.bind(ResponseSurveyManager.class, new ServiceBuilder<ResponseSurveyManager>()  {
			public ResponseSurveyManager buildService(ServiceResources serviceResources) {
				return ResourceLocator.lookup(ResponseSurveyManager.class);
			}
		});
		
		binder.bind(PDFGeneratorManager.class, new ServiceBuilder<PDFGeneratorManager>()  {
			public PDFGeneratorManager buildService(ServiceResources serviceResources) {
				return ResourceLocator.lookup(PDFGeneratorManager.class);
			}
		});
	}
}
