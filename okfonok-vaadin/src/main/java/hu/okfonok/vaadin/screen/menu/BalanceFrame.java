package hu.okfonok.vaadin.screen.menu;

import hu.okfonok.user.account.events.TransactionEvent;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.security.Authentication;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.PopupView.Content;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class BalanceFrame extends CustomComponent {
	private Label balanceLabel = new Label();

	public BalanceFrame() {
		UIEventBus.register(this);
		setCompositionRoot(build());
		refresh();
	}

	private Component build() {
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(new Image(null, new ThemeResource("img/landing/regisztralj.png")));
		VerticalLayout l = new VerticalLayout();
		l.addComponent(new Label("Egyenleg"));
		l.addComponent(balanceLabel);
		hl.addComponent(l);
		final PopupView popup = new PopupView(new BalancePopupContent());
		hl.addComponent(popup);
		hl.addLayoutClickListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				popup.setPopupVisible(true);
			}
		});
		return hl;
	}
	
	@Subscribe
	public void handleTransactionEvent(TransactionEvent event) {
		refresh();
	}

	private void refresh() {
		balanceLabel.setValue(Authentication.getUser().getAccount().getBalance().toString() + " Ft");
	}
	
	private static class BalancePopupContent implements Content{
		public BalancePopupContent() {
			
		}

		@Override
		public String getMinimizedValueAsHTML() {
			return "";
		}

		@Override
		public Component getPopupComponent() {
			VerticalLayout l = new VerticalLayout();
			l.addComponent(buildRows());
			l.addComponent(buildSummary());
			l.addComponent(buildActions());
			return l;
		}

		private Component buildSummary() {
			return new Label("TODO");
		}

		private Component buildRows() {
			return new Label("TODO");
		}

		private Component buildActions() {
			HorizontalLayout actions = new HorizontalLayout();
			Button allPaymentsButton = new Button("Korábbi befizetések", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					//TODO navigálás korábbi befizetések képernyőre, ha elkészül
				}
			});
			allPaymentsButton.setStyleName(ValoTheme.BUTTON_LINK);
			actions.addComponent(allPaymentsButton);
			actions.setComponentAlignment(allPaymentsButton, Alignment.MIDDLE_LEFT);
			
			Button payButton = new Button("Befizetés", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					//TODO befizetés
				}
			});
			actions.addComponent(payButton);
			actions.setComponentAlignment(payButton, Alignment.MIDDLE_RIGHT);
			
			return actions;
		}
	}
}
