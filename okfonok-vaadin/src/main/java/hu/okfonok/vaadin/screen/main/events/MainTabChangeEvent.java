package hu.okfonok.vaadin.screen.main.events;

/**
 * akkor generálódik amikor a hirdetés/feladatok között váltunk
 * 
 */
public class MainTabChangeEvent {
	private TabId tabId;


	public static enum TabId {
		ADS,
		OWNADS
	}


	public MainTabChangeEvent(TabId tabId) {
		this.tabId = tabId;
	}


	public TabId getTabId() {
		return tabId;
	}
}
