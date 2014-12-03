package com.rcp.example.module.a;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class BundleContextServiceUtils {

	/**
	 * @param context
	 * @param clazzName
	 * @return get a service or null
	 */
	public static Object getService(BundleContext context, String clazzName) {
		if(context==null) return null;		
		ServiceReference serviceReference = context.getServiceReference(clazzName);
		if(serviceReference==null) return null;	
		return context.getService(serviceReference);
	}
	
	/**
	 * based on org.eclipse.osgi-3.6.0.v20100517
	 * @param context
	 * @param clazz
	 * @return get a service or null
	 */
	public static Object getService(BundleContext context, Class<?> clazz) {
		if(context==null) return null;	
		ServiceReference serviceReference = context.getServiceReference(clazz.getName());
		if(serviceReference==null) return null;		
		return context.getService(serviceReference);
	}
}
