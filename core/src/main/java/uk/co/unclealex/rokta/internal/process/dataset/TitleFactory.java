package uk.co.unclealex.rokta.internal.process.dataset;

import uk.co.unclealex.rokta.pub.filter.GameFilter;

public interface TitleFactory {

	public String createTitle(GameFilter gameFilter, String prefix);

	public String createCopyright();

}
