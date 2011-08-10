/**
 * 
 */
package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author alex
 *
 */
public class Streak implements Comparable<Streak>, Serializable {

	private String i_personName;
	private Date i_startDate;
	private Date i_endDate;
	private int i_length;
	private int i_rank;
	private int i_positionInTable;
	private boolean i_rankSameAsPreviousRank;
	private boolean i_current;
	
	protected Streak() {
		// No-arg constructor for serialisation
	}
	
	/**
	 * @param person
	 * @param games
	 */
	public Streak(int positionInTable, int rank, String personName, Date startDate, Date endDate, int length, boolean rankSameAsPreviousRank, boolean current) {
		super();
		i_positionInTable = positionInTable;
		i_rank = rank;
		i_personName = personName;
		i_startDate = startDate;
		i_endDate = endDate;
		i_length = length;
		i_rankSameAsPreviousRank = rankSameAsPreviousRank;
		i_current = current;
	}
	
	public int compareTo(Streak o) {
		return getPositionInTable() - o.getPositionInTable();
	}
	
	/**
	 * @return the current
	 */
	public boolean isCurrent() {
		return i_current;
	}

	public String getPersonName() {
		return i_personName;
	}

	public Date getStartDate() {
		return i_startDate;
	}

	public Date getEndDate() {
		return i_endDate;
	}

	public int getRank() {
		return i_rank;
	}

	public int getPositionInTable() {
		return i_positionInTable;
	}

	public int getLength() {
		return i_length;
	}
	
	public boolean isRankSameAsPreviousRank() {
		return i_rankSameAsPreviousRank;
	}
}
