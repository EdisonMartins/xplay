package br.net.mirante.xplay;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public class XplayWebSession extends AuthenticatedWebSession {
	private static final long serialVersionUID = 1L;

	public XplayWebSession(Request request) {
		super(request);

	}

	@Override
	protected boolean authenticate(String username, String password) {

		if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("1234")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Roles getRoles() {
		return null;
	}

}
