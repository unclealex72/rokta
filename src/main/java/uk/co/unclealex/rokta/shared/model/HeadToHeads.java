package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;

public class HeadToHeads implements Serializable {

	private SortedSet<String> names;
	private Set<WinLoseCounter> i_winLoseCounters;
	
	protected HeadToHeads() {
		// Default construtor for serialisation
	}
	
	public HeadToHeads(SortedSet<String> names, Set<WinLoseCounter> winLoseCounters) {
		super();
		this.names = names;
		i_winLoseCounters = winLoseCounters;
	}

	public SortedSet<String> getNames() {
		return names;
	}

	public Set<WinLoseCounter> getWinLoseCounters() {
		return i_winLoseCounters;
	}

	
}
