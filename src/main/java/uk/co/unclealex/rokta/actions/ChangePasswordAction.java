package uk.co.unclealex.rokta.actions;

import uk.co.unclealex.rokta.process.PersonManager;

public class ChangePasswordAction extends DetailsAction {

	private PersonService i_personManager;
	
	private String i_currentPassword;
	private String i_password;
	private String i_retypePassword;
	
	@Override
	protected String executeInternal() {
		super.executeInternal();
		String currentPassword = getCurrentPassword();
		String password = getPassword();
		String retypePassword = getRetypePassword();
		
		if (!password.equals(retypePassword)) {
			addFieldError("retypePassword", "The two passwords are not the same.");
			return INPUT;
		}
		boolean successful =
			getPersonManager().changePassword(
					getPrincipalProxy().getUserPrincipal().getName(), currentPassword, password);
		if (successful) {
			return SUCCESS;
		}
		else {
			addFieldError("currentPassword", "Your current password is invalid.");
			return INPUT;
		}
	}
	
	public String getRetypePassword() {
		return i_retypePassword;
	}
	public void setRetypePassword(String retypePassword) {
		i_retypePassword = retypePassword;
	}

	public String getCurrentPassword() {
		return i_currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		i_currentPassword = currentPassword;
	}

	public String getPassword() {
		return i_password;
	}

	public void setPassword(String password) {
		i_password = password;
	}

	public PersonService getPersonManager() {
		return i_personManager;
	}

	public void setPersonManager(PersonService personManager) {
		i_personManager = personManager;
	}
}
