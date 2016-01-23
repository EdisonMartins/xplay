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
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;

import br.net.mirante.xplay.dao.JogoDAO;
import br.net.mirante.xplay.model.Jogo;

public class IncluirJogoPage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Jogo jogo;
	
	@Inject
	private JogoDAO jogoDAO;

	private TextField<String> emprestadoPara;

	public IncluirJogoPage() {
		add(new FeedbackPanel("feedback"));
		this.jogo = new Jogo();
		List<String> generos = Arrays.asList("Ação", "RPG", "Estratégia");

		Form<Jogo> form = new Form<Jogo>("form");

		form.add(new TextField<>("nome", new PropertyModel<>(jogo, "nome")).setRequired(true));
		form.add(new DropDownChoice<>("genero", new PropertyModel<>(jogo, "genero"), generos).setRequired(true));
		form.add(new TextArea<>("descricao", new PropertyModel<>(jogo, "descricao")));
		form.add(new TextField<>("nota", new PropertyModel<>(jogo, "nota")).add(RangeValidator.range(0, 10)));
/*		form.add(new CheckBox("emprestado", new PropertyModel<Boolean>(jogo, "emprestado")) {
			*//**
			 * 
			 *//*
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				return true;
			}
			
			
			@Override
			public void onSelectionChanged() {
				super.onSelectionChanged();
				System.out.println("checkBox - onSelectionChanged()");
				//emprestadoPara.setRequired(true);
				
			}
			
			
		});*/
		
		form.add(new CheckBox("emprestado", new PropertyModel<Boolean>(jogo, "emprestado")).add(new AjaxFormComponentUpdatingBehavior("change") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(emprestadoPara);
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
}
