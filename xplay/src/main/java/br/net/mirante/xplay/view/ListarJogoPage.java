package br.net.mirante.xplay.view;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.convert.IConverter;

import br.net.mirante.xplay.converter.SimNaoConverter;
import br.net.mirante.xplay.dao.JogoDAO;
import br.net.mirante.xplay.model.Jogo;

public class ListarJogoPage extends Template {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Jogo> jogos;

	@Inject
	private JogoDAO jogoDAO;
	private Fragment fragment;
	private boolean mostrarLista;

	public ListarJogoPage() {

		jogos = jogoDAO.listar();

		add(new Link<String>("novo", Model.of("Novo")) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(IncluirJogoPage.class);
			}
		});

		add(new Link<String>("RepeatingView", Model.of("RepeatingView")) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(RepeatingViewJogoPage.class);
			}
		});

		add(new FeedbackPanel("feedback"));
		buildFragment();
		add(criarLinkMostrarAmigos());

	}

	public List<Jogo> getJogos() {
		return jogos;
	}

	public void setJogos(List<Jogo> jogos) {
		this.jogos = jogos;
	}

	private Button criarBtnAlterar(final Jogo jogo) {
		return new Button("alterar", new Model<String>("alterar")) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				setResponsePage(new IncluirJogoPage(jogo));
			}
		};
	}

	private Link<String> criarLinkEditar(final Jogo jogo) {
		return new Link<String>("editar") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new IncluirJogoPage(jogo));

			}
		};
	}

	private Link<String> criarLinkEditarParameter(final Jogo jogo, final ListItem<Jogo> item) {
		return new Link<String>("editarParameter") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				PageParameters parameters = new PageParameters();
				parameters.add("id", item.getIndex());
				setResponsePage(IncluirJogoPage.class, parameters);

			}
		};
	}

	private Link<String> criarLinkEditarIndexed(final Jogo jogo) {
		return new Link<String>("editarIndexed") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				PageParameters parameters = new PageParameters();
				parameters.set(0, jogo.getNome());
				setResponsePage(IncluirJogoPage.class, parameters);

			}
		};
	}

	private void buildFragment() {
		fragment = new Fragment("fragmento", "list", this);
		fragment.setOutputMarkupId(true);

		fragment.add(new ListView<Jogo>("tabelaJogos", new PropertyModel<List<Jogo>>(this, "jogos")) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Jogo> item) {
				final Jogo jogo = item.getModelObject();

				item.add(new Label("nome", jogo.getNome()));
				item.add(new Label("nota", jogo.getNota()));
				item.add(new Label("genero", jogo.getGenero()));
				item.add(new Label("emprestado", jogo.isEmprestado()) {
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unchecked")
					@Override
					public <C> IConverter<C> getConverter(Class<C> type) {
						if (type == Boolean.class) {
							return (IConverter<C>) new SimNaoConverter();
						} else {
							return super.getConverter(type);
						}
					}
				});

				item.add(criarBtnAlterar(jogo));
				item.add(criarLinkEditar(jogo));
				item.add(criarLinkEditarParameter(jogo, item));
				item.add(criarLinkEditarIndexed(jogo));

				PageParameters parameters = new PageParameters();
				parameters.set(0, jogo.getNome());
				item.add(new BookmarkablePageLink<>("editarBookmarkable", IncluirJogoPage.class, parameters));

			}
		});

		add(fragment);
	}

	private AjaxLink<String> criarLinkMostrarAmigos() {
		return new AjaxLink<String>("mostrarAmigos") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				Fragment f;
				mostrarLista = !mostrarLista;

				if (mostrarLista) {
					f = new Fragment("fragmento", "amigos", getPage());
					f.add(new Label("label", Model.of(jogoDAO.getAmigosComJogo())));

				} else {

					f = fragment;
				}

				getPage().replace(f);
				target.add(f);

			}
		};

	}

}
