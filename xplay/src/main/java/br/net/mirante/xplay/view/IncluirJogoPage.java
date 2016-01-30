package br.net.mirante.xplay.view;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;

import br.net.mirante.xplay.dao.JogoDAO;
import br.net.mirante.xplay.model.Jogo;

public class IncluirJogoPage extends Template {
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

		CampoBorder nomeCB = new CampoBorder("nomeCB", "Nome");
		nomeCB.add(new TextField<>("nomeTF", new PropertyModel<>(jogo, "nome")).setRequired(true));
		
		form.queue(nomeCB);

		CampoBorder generoCB = new CampoBorder("generoCB", "Gênero");
		generoCB.add(new DropDownChoice<>("generoSelect", new PropertyModel<>(jogo, "genero"), generos)
				.setRequired(true));
		form.queue(generoCB);

		CampoBorder descricaoCB = new CampoBorder("descricaoCB", "Descrição");
		descricaoCB.add(new TextArea<>("descricaoTA", new PropertyModel<>(jogo, "descricao")));
		form.queue(descricaoCB);

		CampoBorder notaCB = new CampoBorder("notaCB", "Nota");
		notaCB.add(new TextField<>("notaTF", new PropertyModel<>(jogo, "nota")).add(RangeValidator.range(0, 10)));
		form.queue(notaCB);

		emprestadoPara = new TextField<String>("emprestadoPara", new PropertyModel<String>(jogo, "emprestadoPara")) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				boolean teste = jogo.isEmprestado();
				return teste;
			}

		};
		emprestadoPara.setOutputMarkupId(true);
		
		form.queue(emprestadoPara.setRequired(true));

		form.queue(new CheckBox("emprestado", new PropertyModel<Boolean>(jogo, "emprestado"))
				.add(new AjaxFormComponentUpdatingBehavior("change") {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						target.add(emprestadoPara);
					}
				}));

		

		form.queue(new Button("salvar") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				jogoDAO.salvar(jogo);
				setResponsePage(ListarJogoPage.class);
			}
		});

		form.queue(new Label("feedback"));
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
