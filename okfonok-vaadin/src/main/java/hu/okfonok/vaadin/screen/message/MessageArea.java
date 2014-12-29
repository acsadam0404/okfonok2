package hu.okfonok.vaadin.screen.message;

import hu.okfonok.message.Message;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextArea;


public class MessageArea extends CustomComponent {
	public MessageArea(Message msg, Direction dir) {
		TextArea textArea = new TextArea(null, msg.getText());
		textArea.setReadOnly(true);
		textArea.addStyleName(dir.getStyleName());
		setCaption(msg.getSender().getProfile().getShortenedName());
		setSizeUndefined();
		setCompositionRoot(textArea);
	}


	public static enum Direction {
		LEFT("left"), RIGHT("right");

		private String styleName;


		private Direction(String styleName) {
			this.styleName = styleName;

		}


		public String getStyleName() {
			return styleName;
		}

	}
}
