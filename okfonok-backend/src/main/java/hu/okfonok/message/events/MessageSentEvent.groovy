package hu.okfonok.message.events

import hu.okfonok.message.Conversation

class MessageSentEvent {
	final Conversation conversation

	MessageSentEvent(Conversation conversation) {
		this.conversation = conversation
	}

	public String getUsername1() {
		return conversation.getUser1().getUsername();
	}
	public String getUsername2() {
		return conversation.getUser2().getUsername();
	}
}
