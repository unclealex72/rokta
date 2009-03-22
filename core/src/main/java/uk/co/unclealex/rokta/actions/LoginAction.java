package uk.co.unclealex.rokta.actions;

public class LoginAction extends RoktaAction {

	private boolean i_failed;
	@Override
	
	protected String executeInternal() {
		return isFailed()?INPUT:SUCCESS; 
	}
	
	public boolean isFailed() {
		return i_failed;
	}
	public void setFailed(boolean failed) {
		i_failed = failed;
	}
}
