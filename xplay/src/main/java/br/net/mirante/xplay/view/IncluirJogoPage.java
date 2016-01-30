package br.net.mirante.xplay.view;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;

import br.net.mirante.xplay.dao.JogoDAO;
import br.net.mirante.xplay.model.Jogo;

public class IncluirJogoPage extends WebPage {
	private static final long serialVersionUID = 1L;

	private Jogo jogo;

	@Inject
	private JogoDAO jogoDAO;

	private TextField<String> emprestadoPara;

	private Form<Jogo> form;



	public IncluirJogoPage(Jogo jogo) {
		this.jogo = jogo;

	}

	public IncluirJogoPage() {
		
		this.jogo = new Jogo();

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		recebaParametros();
		
		buildForm();
		buildLinkListView();
		add(new Menu("menu"));
	
		

	}



	private void recebaParametros() {
		if (!getPageParameters().get(0).isNull()) {
			this.jogo = jogoDAO.getJogoPelo(getPageParameters().get(0).toString());
		} else if (!getPageParameters().get("id").isNull()) {
			this.jogo = jogoDAO.listar().get(getPageParameters().get("id").toInt());
		} else {
			// this.jogo = new Jogo();
		}
	}

	private void buildForm() {
		List<String> generos = Arrays.asList("Ação", "RPG", "Estratégia");
		form = new Form<Jogo>("form");
		form.add(new FeedbackPanel("feedback"));

		form.add(new TextField<>("nome", new PropertyModel<>(jogo, "nome")).setRequired(true));
		form.add(new DropDownChoice<>("genero", new PropertyModel<>(jogo, "genero"), generos).setRequired(true));
		form.add(new TextArea<>("descricao", new PropertyModel<>(jogo, "descricao")));
		form.add(new TextField<>("nota", new PropertyModel<>(jogo, "nota")).add(RangeValidator.range(0, 10)));

		form.add(new CheckBox("emprestado", new PropertyModel<Boolean>(jogo, "emprestado"))
				.add(new AjaxFormComponentUpdatingBehavior("change") {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						target.add(form);
					}
				}));

		emprestadoPara = new TextField<String>("emprestadoPara", new PropertyModel<String>(jogo, "emprestadoPara")) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return jogo.isEmprestado();
			}

		};
		emprestadoPara.setOutputMarkupId(true);
		setOutputMarkupPlaceholderTag(true);

		form.add(emprestadoPara.setRequired(true));

		form.add(new Button("salvar") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				jogoDAO.salvar(jogo);
				setResponsePage(ListarJogoPage.class);
			}
		});

		add(form);
	}

	private void buildLinkListView() {
		add(new Link<String>("listView", Model.of("listView")) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ListarJogoPage.class);
			}
		});
	}



}
