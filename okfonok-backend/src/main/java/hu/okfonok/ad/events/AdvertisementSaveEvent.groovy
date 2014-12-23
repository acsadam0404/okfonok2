package hu.okfonok.ad.events

/**
 * Mentéskor és törléskor ez az event broadcastolódik
 */
class AdvertisementSaveEvent {
	private boolean saved

	AdvertisementSaveEvent(boolean saved) {
		this.saved = saved
	}

	/**
	 * @return true ha mentett
	 * false ha törölt
	 */
	boolean isSaved() {
		saved
	}
}
