package br.com.unifacear.util;

import java.util.ArrayList;
import java.util.List;

import br.com.unifacear.model.Veiculo;

public class ControleVeiculo {
	private List<Veiculo> veiculos = new ArrayList<>();
	
	public boolean salvar(Veiculo v) {
		if (v != null) {
			veiculos.add(v);
			return true;
		} else {
			return false;
		}
	}
	public List<Veiculo> retorna(){
		return veiculos;
	}
}
