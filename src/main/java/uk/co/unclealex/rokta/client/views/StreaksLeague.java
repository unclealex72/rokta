package uk.co.unclealex.rokta.client.views;

import java.io.Serializable;
import java.util.List;

public class StreaksLeague implements Serializable {

	private int i_size;
	private boolean i_current;
	private List<Streak> i_streaks;
	
	protected StreaksLeague() {
		// No arg constructor for serialisation.
	}
	
	public StreaksLeague(List<Streak> streaks, boolean current) {
		super();
		i_streaks = streaks;
		i_current = current;
		i_size = streaks.size();
	}
	
	public int getSize() {
		return i_size;
	}
	
	public boolean isCurrent() {
		return i_current;
	}
	
	public List<Streak> getStreaks() {
		return i_streaks;
	}
	
}
