package net.atos.survey.gui.services;

import net.atos.survey.core.dao.UserDao;
import net.atos.xa.resourcelocator.ResourceLocator;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.ServiceBuilder;
import org.apache.tapestry5.ioc.ServiceResources;


public class SurveyModule {
	
	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.START_PAGE_NAME, "Index");
		configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
		
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "fr,en");
		
		
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
	}
}
