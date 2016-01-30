package br.net.mirante.xplay.view;

import org.apache.wicket.markup.html.WebPage;

public class Template extends WebPage {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Menu("menu"));
		
		
		
	}

}
