package br.com.unifacear.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.unifacear.model.Veiculo;

public class ArqTxt {
	private String nome;
	private File file;
	ControleVeiculo cv = new ControleVeiculo();
	
	public ArqTxt(String nome) {
		this.nome = nome;
		criar();
	}
	
	private void criar() {
		this.file = new File(this.nome);
		try {
			if(!file.exists()) {
				file.createNewFile();
			} else {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void excluir(String nome) {
		this.file.delete();
	}
	
	public void inserir(String texto) {
		FileWriter fw;
		try {
			fw = new FileWriter(this.file, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(texto);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ControleVeiculo ler(){
		FileReader fr;
		try {
			fr = new FileReader(this.file);
			BufferedReader br = new BufferedReader(fr);
			try {
				String linha = br.readLine();
				try {
					while (linha != null) {
						Veiculo veiculo = new Veiculo();
						veiculo.setPlaca(linha);
						linha = br.readLine();
						veiculo.setMarca(linha);
						linha = br.readLine();
						veiculo.setModelo(linha);
						linha = br.readLine();
						veiculo.setAno(Short.parseShort(linha));
						linha = br.readLine();
						veiculo.setAtivo(true);
						cv.salvar(veiculo);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				br.close();
				return cv;
			}  catch (Exception e) {
				System.out.println("Teste");
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
