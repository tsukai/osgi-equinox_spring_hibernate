package com.rcp.example.module.a;

import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.*;
import org.osgi.util.tracker.ServiceTracker;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class BundleSpringBeanUtils {

	/**
	 * @param context
	 * @return
	 */
	public static Set<ApplicationContext> getApplicationContexts(
		BundleContext context) {	

		Set<ApplicationContext> acs = new HashSet<ApplicationContext>();
		Bundle[] bundles = context.getBundles();
		for (Bundle bundle : bundles) {
			ApplicationContext ac = 
			getApplicationContext(context, bundle.getSymbolicName());
			if(ac!=null) acs.add(ac);
		}

		return acs;
	}

	/**
	 * get ApplicationContext by bundleName
	 */
	public static ApplicationContext getApplicationContext(
		BundleContext context, String bundleName) {	

		try {
			String filter = "(org.springframework.context.service.name="+bundleName+")";
			ServiceReference[] serviceReferences = 
			context.getAllServiceReferences(
			"org.springframework.context.ApplicationContext", filter);
			
			if(serviceReferences!=null){
				for (int i = 0; i < serviceReferences.length; i++) {
					ServiceReference sr = serviceReferences[i];
					ApplicationContext ac = 
					(ApplicationContext) context.getService( sr );
					if(ac!=null) return ac;
				}
			}	
		} catch (InvalidSyntaxException e) {	
		}
		return null;
	}

	/**
	 * get bean by bundleName
	 */
	public static Object getSpringBean(
		BundleContext context, String bundleName, String beanName) {
		
		ApplicationContext ac = 
		getApplicationContext(context, bundleName);
		return ac.getBean(beanName);
	}

	/**
	 * get bean by beanName
	 */
	public static Object getSpringBean(
		BundleContext context, String beanName) {
		
		Set<ApplicationContext> acs = 
		getApplicationContexts(context);

		for (ApplicationContext ac : acs) {
			Object bean;
			try {
				bean = ac.getBean(beanName);
				if(bean!=null) return bean;
			} catch (BeansException e) {
			}
		}
		return null;
	}
}
