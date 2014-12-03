package com.norcp.example.module.dbcp.prop;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.osgi.io.OsgiBundleResource;

public class BundlePlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	@Override
	protected void loadProperties(Properties props) throws IOException {
		Resource[] locations = (Resource[]) getFieldValue(this, "locations");
		for (int i = 0; i < locations.length; i++) {
			Resource location = locations[i];
			if (!location.exists()) {
				if (location instanceof OsgiBundleResource) {
					OsgiBundleResource osgiResource = (OsgiBundleResource) location;
					String fileName = osgiResource.getFilename();
					Bundle bundle = (Bundle) getFieldValue(osgiResource,
							"bundle");
					Resource newOsgiResource = getOsgiBundleResourceByBundles(
							bundle, fileName);
					locations[i] = newOsgiResource;
				}
			}
		}
		super.loadProperties(props);
	}

	/**
	 * 在bundle context中加载资源文件
	 * 
	 * @param bundle
	 * @param fileName
	 * @return
	 */
	private Resource getOsgiBundleResourceByBundles(Bundle bundle,
			String bundleResourcePath) {
		Bundle[] bundles = bundle.getBundleContext().getBundles();
		for (int i = 0; i < bundles.length; i++) {
			Bundle bnd = bundles[i];
			Resource osgiResource = getOsgiBundleResource(bnd,
					bundleResourcePath);
			if (osgiResource != null && osgiResource.exists()
					&& isActiveBundle(bundle)) {
				return osgiResource;
			}
		}
		return null;
	}

	/**
	 * 从Osgi Bundle中获取资源
	 * 
	 * @param bnd
	 * @param bundleResourcePath
	 * @return
	 */
	private Resource getOsgiBundleResource(Bundle bundle, String bundleResourcePath) {
		OsgiBundleResource osgiBundleResource = new OsgiBundleResource(bundle,
				bundleResourcePath);
		return osgiBundleResource;
	}

	/**
	 * 如果有多个连接池插件，只加载处于avtive状态的配置文件
	 * 
	 * @param bundle
	 * @return
	 */
	private boolean isActiveBundle(Bundle bundle) {
		return bundle.getState() == Bundle.ACTIVE;
	}

	/**
	 * 通过反射机制获取类的属性值
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private Object getFieldValue(Object object, String fieldName) {
		Field field = getSupperDeclaredField(object.getClass(), fieldName);
		if (field != null) {
			field.setAccessible(true);
			try {
				return field.get(object);
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	private Field getSupperDeclaredField(Class<? extends Object> clazz,
			String fieldName) {
		if (clazz == null)
			return null;
		try {
			Field declaredField = clazz.getDeclaredField(fieldName);
			if (declaredField != null)
				return declaredField;
		} catch (NoSuchFieldException e) {
			Class<?> supperClass = clazz.getSuperclass();
			if (supperClass != null) {
				Field supperDeclaredFiled = getSupperDeclaredField(supperClass,
						fieldName);
				if (supperDeclaredFiled != null)
					return supperDeclaredFiled;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}
}
