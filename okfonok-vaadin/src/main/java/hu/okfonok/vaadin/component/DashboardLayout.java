package hu.okfonok.vaadin.component;

import java.util.Iterator;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

public class DashboardLayout extends CssLayout {
	public DashboardLayout() {
		addStyleName("dashboard-panels");
		Responsive.makeResponsive(this);
	}
	
	@Override
	public void addComponent(Component c) {
		super.addComponent(createContentWrapper(c));
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
				if (!slot.getStyleName().contains("max")) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(slot, true);
				} else {
					slot.removeStyleName("max");
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(slot, false);
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

	private void toggleMaximized(final Component panel, final boolean maximized) {
		/* ha akarjuk hogy a többi is eltűnjön akkor itt azon a legfőbb komponensen kell végigiterálni aminek a tartalmát el akarjuk tüntetni,
		 * ehhez meg kell adnunk konstruktorban egy ilyet vagy valami
		 */
//		for (Iterator<Component> it = iterator(); it.hasNext();) {
//			it.next().setVisible(!maximized);
//		}
		setVisible(true);

		for (Component c  : components) {
			c.setVisible(!maximized);
		}

		if (maximized) {
			panel.setVisible(true);
			panel.addStyleName("max");
		} else {
			panel.removeStyleName("max");
		}
	}

}
