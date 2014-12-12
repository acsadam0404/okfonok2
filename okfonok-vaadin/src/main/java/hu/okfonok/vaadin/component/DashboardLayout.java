package hu.okfonok.vaadin.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;


public class DashboardLayout extends CustomComponent {
	private ComponentContainer root;
	private ComponentContainer full;
	private boolean maximized;


	public DashboardLayout(ComponentContainer root) {
		this(root, null);
	}


	public DashboardLayout(ComponentContainer root, ComponentContainer full) {
		this.root = root;
		this.full = full;
		setCompositionRoot(root);
	}


	public void addComponent(Component c) {
		root.addComponent(createContentWrapper(c));
	}


	private Component createContentWrapper(final Component content) {
		final CssLayout slot = new CssLayout();
		slot.setWidth("100%");
		slot.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");

		Label caption = new Label(content.getCaption());
		caption.addStyleName(ValoTheme.LABEL_H4);
		caption.addStyleName(ValoTheme.LABEL_COLORED);
		caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		content.setCaption(null);

		MenuBar tools = new MenuBar();
		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {


			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (maximized) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(slot);
				}
				else {
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(slot);
				}
			}
		});
		max.setStyleName("icon-only");
		MenuItem root = tools.addItem("", FontAwesome.COG, null);
		root.addItem("Configure", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				Notification.show("Not implemented in this demo");
			}
		});
		root.addSeparator();
		root.addItem("Close", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				Notification.show("Not implemented in this demo");
			}
		});

		toolbar.addComponents(caption, tools);
		toolbar.setExpandRatio(caption, 1);
		toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, content);
		slot.addComponent(card);
		return slot;
	}


	private void toggleMaximized(final Component panel) {
		maximized = !maximized;
		if (full != null) {
			for (Component c : full) {
				c.setVisible(!maximized);
			}
		}
		for (Component c : root) {
			c.setVisible(!maximized);
		}

		setVisible(true);
		full.setVisible(true);
		root.setVisible(true);
		panel.setVisible(maximized);
	}

}
