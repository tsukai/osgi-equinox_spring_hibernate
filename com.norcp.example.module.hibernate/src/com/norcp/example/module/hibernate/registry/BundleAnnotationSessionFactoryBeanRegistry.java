package com.norcp.example.module.hibernate.registry;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.Platform;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.util.ClassUtils;

public class BundleAnnotationSessionFactoryBeanRegistry extends
		AnnotationSessionFactoryBean implements IRegistryChangeListener {
	public static String PLUGIN_ID = "com.norcp.example.module.hibernate";
	public static String EXTENSION_NAME = "BundleAnnotationSessionFactoryBean";
	public static String EXTENSIONPOINT_ID = PLUGIN_ID + "." + EXTENSION_NAME;
	private IExtensionRegistry extensionRegistry = Platform
			.getExtensionRegistry();
	private Set<IExtension> sessionFactoryExtensions = new HashSet<IExtension>();

	// 完成对BundleAnnotationSessionFactoryBean扩展的获取，并将结果添加到sessionFactoryExtensions
	public BundleAnnotationSessionFactoryBeanRegistry() {
		super();
		extensionRegistry.addRegistryChangeListener(this, EXTENSIONPOINT_ID);
		IExtensionPoint extensionPoint = extensionRegistry
				.getExtensionPoint(EXTENSIONPOINT_ID);
		if (extensionPoint != null) {
			IExtension[] extensions = extensionPoint.getExtensions();
			if (extensions != null) {
				for (int i = 0; i < extensions.length; i++) {
					sessionFactoryExtensions.add(extensions[i]);
				}
			}
		}
	}

	/*
	 * 从扩展节点中解析配置的annotatedClass信息
	 */
	protected Class[] extensionAnnotatedClass(
			Set<IExtension> sessionFactoryExtensions) {
		Set<Class> classes = new HashSet<Class>();
		for (IExtension extension : sessionFactoryExtensions) {
			IConfigurationElement[] elements = extension
					.getConfigurationElements();
			for (int i = 0; i < elements.length; i++) {
				IConfigurationElement element = elements[i];
				if (element.getName().equalsIgnoreCase("annotatedClasses")) {
					IConfigurationElement[] annotatedClasses = element
							.getChildren("annotatedClass");
					for (IConfigurationElement annotatedClass : annotatedClasses) {
						String className = annotatedClass
								.getAttribute("className");
						try {
							Class annotatedClazz = Class.forName(className);
							classes.add(annotatedClazz);
							System.out.println("annotatedClazz:"
									+ extension.getContributor() + ":"
									+ annotatedClazz);
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/*
	 * 从扩展点中解析配置的annotatedPackages信息
	 */
	protected String[] extensionAnnotatedPackages(
			Set<IExtension> sessionFactoryExtensions) {
		Set<String> packageNames = new HashSet<String>();
		for (IExtension extension : sessionFactoryExtensions) {
			IConfigurationElement[] elements = extension
					.getConfigurationElements();
			for (IConfigurationElement element : elements) {
				if (element.getName().equalsIgnoreCase("annotatedPackages")) {
					IConfigurationElement[] annotatedPackages = element
							.getChildren("annotatedPackage");
					for (IConfigurationElement annotatedPackage : annotatedPackages) {
						String packageName = annotatedPackage
								.getAttribute("packageName");
						packageNames.add(packageName);
					}
				}
			}
		}
		return packageNames.toArray(new String[packageNames.size()]);
	}

	/*
	 * 从扩展点中解析配置的packagesToScan信息
	 */
	protected String[] extensionPakcagesToScan(
			Set<IExtension> sessionFactoryExtensions) {
		Set<String> packageNames = new HashSet<String>();
		for (IExtension extension : sessionFactoryExtensions) {
			IConfigurationElement[] elements = extension
					.getConfigurationElements();
			for (IConfigurationElement element : elements) {
				if (element.getName().equalsIgnoreCase("packagesToScan")) {
					IConfigurationElement[] packagesToScan = element
							.getChildren("packageToScan");
					for (IConfigurationElement packageToScan : packagesToScan) {
						String packageName = packageToScan
								.getAttribute("packageName");
						packageNames.add(packageName);
						System.out.println(" packageToScan:"
								+ extension.getContributor() + ":"
								+ packageName);
					}
				}
			}
		}
		return packageNames.toArray(new String[packageNames.size()]);
	}

	@Override
	protected void postProcessMappings(Configuration config)
			throws HibernateException {
		super.postProcessMappings(config);
		AnnotationConfiguration annConfig = (AnnotationConfiguration) config;
		Class[] extensionAnnotatedClasses = this
				.extensionAnnotatedClass(sessionFactoryExtensions);
		if (extensionAnnotatedClasses != null) {
			for (Class annotatedClass : extensionAnnotatedClasses) {
				annConfig.addAnnotatedClass(annotatedClass);
			}
		}
		String[] extensionAnnotatedPakcages = extensionAnnotatedPackages(sessionFactoryExtensions);
		if (extensionAnnotatedPakcages != null) {
			for (String annotatedPackage : extensionAnnotatedPakcages) {
				annConfig.addPackage(annotatedPackage);
			}
		}
	}

	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	private static final String RESOURCE_PATTERN = "/*.class";
	private TypeFilter[] entityTyleFilters = new TypeFilter[] {
			new AnnotationTypeFilter(Entity.class, false),
			new AnnotationTypeFilter(Embeddable.class, false),
			new AnnotationTypeFilter(MappedSuperclass.class, false) };

	@Override
	protected void scanPackages(AnnotationConfiguration config) {
		super.scanPackages(config);
		String[] extensionPackagesToScan = extensionPakcagesToScan(sessionFactoryExtensions);
		if (extensionPackagesToScan != null) {
			try {
				for (String pkg : extensionPackagesToScan) {
					String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
							+ ClassUtils.convertClassNameToResourcePath(pkg)
							+ RESOURCE_PATTERN;
					Resource[] resources = this.resourcePatternResolver
							.getResources(pattern);
					MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(
							this.resourcePatternResolver);
					for (Resource resource : resources) {
						if (resource.isReadable()) {
							MetadataReader reader = readerFactory
									.getMetadataReader(resource);
							String className = reader.getClassMetadata()
									.getClassName();
							if (matchesFilter(reader, readerFactory)) {
								config.addAnnotatedClass(this.resourcePatternResolver
										.getClassLoader().loadClass(className));
								System.out.println("ScanPackagesClass:"
										+ className);
							}
						}
					}
				}
			} catch (IOException e) {
				throw new MappingException(
						"Failed to scan calsspath for unlisted classes", e);
			} catch (ClassNotFoundException ex) {
				throw new MappingException(
						"Failed to load annnoated calsses from classpath", ex);
			}
		}
	}

	/*
	 * 检查是否与实体类型过滤器匹配
	 */
	private boolean matchesFilter(MetadataReader reader,
			MetadataReaderFactory readerFactory) throws IOException {
		if (this.entityTyleFilters != null) {
			for (TypeFilter filter : this.entityTyleFilters) {
				if (filter.match(reader, readerFactory))
					return true;

			}
		}
		return false;
	}

	@Override
	public void registryChanged(IRegistryChangeEvent event) {
		IExtensionDelta[] deltas = event.getExtensionDeltas(PLUGIN_ID,
				EXTENSION_NAME);
		if(deltas.length == 0)
			return;
		for (int i = 0; i < deltas.length; i++) {
			switch (deltas[i].getKind()) {
			case IExtensionDelta.ADDED:
				sessionFactoryExtensions.add(deltas[i].getExtension());
				break;
			case IExtensionDelta.REMOVED:
				sessionFactoryExtensions.remove(deltas[i].getExtension());
				break;

			default:
				break;
			}
		}
	}

}
