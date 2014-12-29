package hu.okfonok.message.events

import hu.okfonok.message.Conversation

class MessageSentEvent {
	private Conversation conversation

	MessageSentEvent(Conversation conversation) {
		this.conversation = conversation
	}

	Conversation getConversation() {
		conversation
	}
}
