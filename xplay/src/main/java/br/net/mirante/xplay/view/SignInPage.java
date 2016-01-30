package br.net.mirante.xplay.view;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.string.Strings;

public class SignInPage extends WebPage {

	private static final long serialVersionUID = 1L;
	private String usuario;
	private String senha;

	public SignInPage() {
		add(new FeedbackPanel("feedback"));

		@SuppressWarnings("rawtypes")
		Form form = new Form("form") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				if (Strings.isEmpty(usuario))
					return;

				boolean authResult = AuthenticatedWebSession.get().signIn(usuario, senha);
				if (authResult) {
					continueToOriginalDestination();
				}
			}
		};
		form.setDefaultModel(new CompoundPropertyModel<>(this));
		form.add(new CampoBorder("campoUsuario", "Usuário").add(new TextField<>("usuario").setRequired(true)));
		form.add(new CampoBorder("campoSenha", "Senha").add(new PasswordTextField("senha")));
		form.add(new Button("login"));
		add(form);

	}

}
