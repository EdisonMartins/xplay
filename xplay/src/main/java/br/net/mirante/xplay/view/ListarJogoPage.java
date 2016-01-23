package br.net.mirante.xplay.view;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.convert.IConverter;

import br.net.mirante.xplay.converter.SimNaoConverter;
import br.net.mirante.xplay.dao.JogoDAO;
import br.net.mirante.xplay.model.Jogo;

public class ListarJogoPage extends WebPage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Jogo> jogos;
	
	
	public ListarJogoPage() {
		// TODO Auto-generated constructor stub
		
		jogos = new JogoDAO().listar();
		add(new ListView<Jogo>("tabelaJogos", new PropertyModel<List<Jogo>>(this, "jogos")) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<Jogo> item) {
                Jogo jogo = item.getModelObject();
                
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
            }
        });
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
        
        
	}


	public List<Jogo> getJogos() {
		return jogos;
	}


	public void setJogos(List<Jogo> jogos) {
		this.jogos = jogos;
	}
	
	
	
	

}
