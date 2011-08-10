/**
 * 
 */
package uk.co.unclealex.rokta.shared.model;

/**
 * @author alex
 *
 */
public class WinLoseCounter {

	private int i_winCount;
	private int i_lossCount;

	/**
	 * 
	 */
	public WinLoseCounter() {
		super();
	}
	
	/**
	 * @param winCount
	 * @param lossCount
	 */
	public WinLoseCounter(int winCount, int lossCount) {
		super();
		i_winCount = winCount;
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
		return "[W:" + getWinCount() + ",L:" + getLossCount() + "]";
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
}
