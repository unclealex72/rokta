package uk.co.unclealex.googleauth;

import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class GoogleOauthListener implements HttpSessionListener {

  /**
   * {@inheritDoc}
   */
  @Override
  public void sessionCreated(HttpSessionEvent evt) {
    HttpSession session = evt.getSession();
    session.setAttribute(GoogleOauthFilter.LOCK, new ReentrantLock());
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
    // TODO Auto-generated method stub

  }

}
