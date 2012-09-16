package uk.co.unclealex.rokta.server.service;

import javax.servlet.http.HttpServletRequest;

import uk.co.unclealex.rokta.shared.service.AnonymousRoktaService;
import uk.co.unclealex.rokta.shared.service.UserRoktaService;

import com.google.common.base.Supplier;

public interface RoktaService extends AnonymousRoktaService, UserRoktaService {

	public void setHttpServletRequestSupplier(Supplier<HttpServletRequest> supplier);

}
