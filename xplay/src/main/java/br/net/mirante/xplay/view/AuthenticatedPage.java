package br.net.mirante.xplay.view;

import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;

public class AuthenticatedPage extends WebPage {
	private static final long serialVersionUID = 1L;

	@Override
	protected void onConfigure() {
		super.onConfigure();
		 AuthenticatedWebApplication app = (AuthenticatedWebApplication) Application.get();
		if (!AuthenticatedWebSession.get().isSignedIn()) {
			 app.restartResponseAtSignInPage();
		}

	}

}
