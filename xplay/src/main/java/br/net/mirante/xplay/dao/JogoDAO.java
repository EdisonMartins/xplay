package br.net.mirante.xplay.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.net.mirante.xplay.model.Jogo;

@Component
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

	public Jogo getJogoPelo(String nome) {
		for (Jogo jogo : jogos) {
			if (jogo.getNome().equals(nome)) {
				return jogo;
			}
		}

		return new Jogo();
	}

	public String getAmigosComJogo() {

		String amigosComJogo = "";

		for (Jogo jogo : jogos) {

			if (jogo.isEmprestado()) {

				amigosComJogo += jogo.getEmprestadoPara() + " ";

			}

		}

		return amigosComJogo;

	}

}
