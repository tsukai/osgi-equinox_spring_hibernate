package com.rcp.example.module.a.actions;

import java.util.ArrayList;
import java.util.List;

import org.com.norcp.example.module.a.pojo.User;
import org.com.norcp.example.module.a.service.api.IUserService;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.springframework.context.ApplicationContext;

import com.norcp.example.module.h.domain.Organ;
import com.norcp.example.module.h.service.api.IOrganService;
import com.rcp.example.module.a.Activator;
import com.rcp.example.module.a.BundleSpringBeanUtils;
import com.rcp.example.module.a.views.SampleView;

public class SomeAction extends Action {
	private IWorkbenchWindow window;

	public SomeAction(String id, IWorkbenchWindow window) {
		super();
		this.window = window;
		setId(id);
		setText(id);
	}

	@Override
	public void run() {
		System.out.println("----------someaction----------");
		BundleContext context = Activator.getDefault().getBundle()
				.getBundleContext();
		ApplicationContext fcontext = BundleSpringBeanUtils
				.getApplicationContext(context, "com.norcp.example.module.a");
		/*Bundle[] bundles = context.getBundles();
		for (Bundle b : bundles) {
			System.out.println(b.getSymbolicName() + "----" + b.getState());
		}*/
		System.out.println("ApplicationContext-:" + fcontext);
		List<String> userlist = new ArrayList<String>();
		if (null != fcontext) {
			// System.out.println("userDao:" + fcontext.getBean("userDao"));
			IUserService service = (IUserService) fcontext
					.getBean("userService");
			List<User> users = service.queryUsers();
			for (User user : users) {
				System.out.println(user.getName());
				userlist.add(user.getName());
			}
		}
		ApplicationContext hcontext = BundleSpringBeanUtils
				.getApplicationContext(context, "com.norcp.example.module.h");

		if (hcontext != null) {
			IOrganService os = (IOrganService) hcontext.getBean("organService");
			List<Organ> organs = os.queryOrgans();
			for (Organ o : organs) {
				System.out.println(o.getName());
			}
			Organ o = new Organ();
			o.setName("lisi");
			os.createOrgan(o);
		}
		IViewReference[] views = Activator.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getViewReferences();
		for (IViewReference view : views) {
			IViewPart viewpart = view.getView(true);
			if (viewpart instanceof SampleView) {
				SampleView sv = (SampleView) viewpart;
				sv.getViewer().getTable().clearAll();
				sv.getViewer().refresh();
				sv.getViewer().setInput(userlist);
			}
		}
	}
}