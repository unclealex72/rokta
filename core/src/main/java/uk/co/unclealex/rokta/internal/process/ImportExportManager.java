package uk.co.unclealex.rokta.internal.process;

import uk.co.unclealex.rokta.pub.views.Rokta;

public interface ImportExportManager {

	public Rokta exportAll();
	public void importAll(Rokta rokta);
}
