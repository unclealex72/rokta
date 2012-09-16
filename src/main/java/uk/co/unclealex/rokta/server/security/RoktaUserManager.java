package uk.co.unclealex.rokta.server.security;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.oauth2.model.Userinfo;
import com.google.common.collect.Lists;

import uk.co.unclealex.googleauth.UserManager;
import uk.co.unclealex.rokta.server.dao.PersonDao;

@Transactional
public class RoktaUserManager implements UserManager {

  private PersonDao personDao;
  
  @Override
  public List<String> getValidGmailAddresses() {
    return Lists.newArrayList(getPersonDao().getAllEmailAddresses());
  }

  @Override
  public boolean isUserInRole(String gmailAddress, String roleName) {
    return true;
  }

  @Override
  public void createNewUserIfRequired(String gmailAddress, Userinfo userinfo, Credential credential) {
    // Do nothing.
  }

  public PersonDao getPersonDao() {
    return personDao;
  }

  public void setPersonDao(PersonDao personDao) {
    this.personDao = personDao;
  }

}
