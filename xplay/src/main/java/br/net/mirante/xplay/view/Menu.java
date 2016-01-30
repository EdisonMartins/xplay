package br.net.mirante.xplay.view;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class Menu extends Panel {
	private static final long serialVersionUID = 1L;

	public Menu(String id) {
		super(id);

		add(new Link<String>("listarJogos") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ListarJogoPage.class);

			}

		});

		add(new Link<String>("novoJogo") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(IncluirJogoPage.class);
			}

		});

	}

}
