package hu.okfonok.vaadin.screen.message;

import hu.okfonok.message.Conversation;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;

public class ConversationTable extends CustomComponent {
	private Table table;

	public ConversationTable() {
		this.table = buildTable();
		setCompositionRoot(table);
		refresh();
	}

	private Table buildTable() {
		Table table = new Table();
		table.setSizeFull();
		table.setContainerDataSource(new BeanItemContainer<>(Conversation.class));
		
	
		return table;
	}
	
	public void refresh() {
		BeanItemContainer container = (BeanItemContainer)table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(Conversation.findAll());
	}
}
