package uk.co.unclealex.rokta.server.service;

import uk.co.unclealex.rokta.shared.service.AnonymousRoktaService;
import uk.co.unclealex.rokta.shared.service.SecurityInvalidator;
import uk.co.unclealex.rokta.shared.service.UserRoktaService;

public interface RoktaService extends AnonymousRoktaService, UserRoktaService {

	public void setSecurityInvalidator(SecurityInvalidator securityInvalidator);

}
