package br.net.mirante.xplay.view;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

import br.net.mirante.xplay.dao.JogoDAO;
import br.net.mirante.xplay.model.Jogo;

public class RepeatingViewJogoPage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Jogo> jogos;
	
	
	
	public RepeatingViewJogoPage() {		
		jogos = new JogoDAO().listar();
		
		 RepeatingView view = new RepeatingView("repeater");
		 
		 for (Jogo jogo : jogos) {
			 view.add(new Label(view.newChildId(), jogo.getNome()));
		}
		 add(view);
		 
		 add(new Link<String>("novo", Model.of("Novo")) {
	            private static final long serialVersionUID = 1L;

	            @Override
	            public void onClick() {
	                setResponsePage(IncluirJogoPage.class);
	            }
	        });
		 
		 
		 add(new Link<String>("listView", Model.of("listView")) {
	            private static final long serialVersionUID = 1L;

	            @Override
	            public void onClick() {
	                setResponsePage(ListarJogoPage.class);
	            }
	        });
		 
	}

}
