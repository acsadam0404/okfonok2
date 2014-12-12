package hu.okfonok.vaadin.screen;

import hu.okfonok.common.ValueSet;
import hu.okfonok.help.Help;
import hu.okfonok.vaadin.OFFieldGroup;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.security.Authentication;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class HelpFrame extends CustomComponent {
	private OFFieldGroup<Help> fg;
	
	@PropertyId("subject")
	private TextField subjectField;
	@PropertyId("message")
	private TextArea messageField;
	@PropertyId("category")
	private ComboBox categoryField;
	private Button sendButton = new Button("Elküldés", new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			try {
				fg.commit();
				if (fg.isValid()) {
					fg.getBean().save();
					UIEventBus.post(new HelpCreatedEvent());
				}
			} catch (CommitException e) {
				e.printStackTrace();
			}
			
		}
	});

	public HelpFrame() {
		Help help = new Help();
		help.setUser(Authentication.getUser());
		fg = new OFFieldGroup<>(help);
		setCompositionRoot(build());
	}

	private Component build() {
		VerticalLayout root = new VerticalLayout();
		root.setMargin(true);
		root.setSpacing(true);
		root.addComponent(new Label("Bármilyen kérdést, észrevételt örömmel fogadunk!"));
		subjectField = new TextField();
		subjectField.setNullRepresentation("");
		subjectField.setInputPrompt("Tárgy");
		messageField = new TextArea();
		messageField.setInputPrompt("Üzenet");
		messageField.setNullRepresentation("");
		categoryField = new ComboBox(null, ValueSet.helpCategory().getEntryValues());
		categoryField.setInputPrompt("Kategória");
		root.addComponent(subjectField);
		root.addComponent(messageField);
		root.addComponent(categoryField);
		root.addComponent(sendButton);
		fg.bindMemberFields(this);
		return root;
	}
}
