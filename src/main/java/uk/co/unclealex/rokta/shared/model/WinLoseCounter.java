/**
 * 
 */
package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;

/**
 * @author alex
 *
 */
public class WinLoseCounter implements Serializable {

	private int i_winCount;
	private int i_lossCount;
	private String i_winner;
	private String i_loser;
	
	/**
	 * 
	 */
	protected WinLoseCounter() {
		super();
	}
	
	/**
	 * @param winCount
	 * @param lossCount
	 */
	public WinLoseCounter(String winner, int winCount, String loser, int lossCount) {
		super();
		i_winner = winner;
		i_winCount = winCount;
		i_loser = loser;
		i_lossCount = lossCount;
	}
	
	public void addWin() {
		i_winCount++;
	}
	
	public void addLoss() {
		i_lossCount++;
	}
	
	public int getTotalCount() {
		return getWinCount() + getLossCount();
	}
	
	public double getWinRatio() {
		return getWinCount() / (double) getTotalCount();
	}

	public double getLossRatio() {
		return getLossCount() / (double) getTotalCount();
	}
	
	@Override
	public String toString() {
		return "[W:" + getWinner() + ":" + getWinCount() + ",L:" + getLoser() + ":" + getLossCount() + "]";
	}
/**
	 * @return the lossCount
	 */
	public int getLossCount() {
		return i_lossCount;
	}
	/**
	 * @param lossCount the lossCount to set
	 */
	public void setLossCount(int lossCount) {
		i_lossCount = lossCount;
	}

	/**
	 * @return the winCount
	 */
	public int getWinCount() {
		return i_winCount;
	}
	/**
	 * @param winCount the winCount to set
	 */
	public void setWinCount(int winCount) {
		i_winCount = winCount;
	}

	public String getWinner() {
		return i_winner;
	}

	public void setWinner(String winner) {
		i_winner = winner;
	}

	public String getLoser() {
		return i_loser;
	}
}
