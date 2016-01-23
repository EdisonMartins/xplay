package br.net.mirante.xplay.dao;

import java.util.ArrayList;
import java.util.List;

import br.net.mirante.xplay.model.Jogo;

public class JogoDAO {
	private static List<Jogo> jogos = new ArrayList<>();

	static {
		jogos.add(new Jogo("GTA IV", 9, "Ação"));
		jogos.add(new Jogo("The Witcher 3", 10, "RPG"));
		jogos.add(new Jogo("Need for Speed", 8, "Corrida"));
	}

	public void salvar(Jogo jogo) {
		System.out.println(String.format("Jogo %s incluído", jogo.getNome()));
		jogos.add(jogo);
	}

	public List<Jogo> listar() {
		return jogos;
	}

}
