package com.rcp.example.app.intro;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.addView("com.rcp.example.module.a.views.SampleView",
				IPageLayout.LEFT, .5f, layout.getEditorArea());
	}
}
