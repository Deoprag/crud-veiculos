package br.com.unifacear.app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import br.com.unifacear.model.Veiculo;
import br.com.unifacear.util.ArqTxt;
import br.com.unifacear.util.ControleVeiculo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class App extends JFrame{
	
	ArqTxt arq = new ArqTxt("Carros.txt");
	ControleVeiculo cv = arq.ler();
	List<Veiculo> veiculos = cv.retorna();

	private JPanel PanelOut;
	private JTextField txtMarca;
	private JTextField txtModelo;
	private JTextField txtPlaca;
	private JTextField txtAno;
	private JTable tableDados;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					frame.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							frame.fechar();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void fechar() {
		arq.excluir("Carros.txt");
		ArqTxt arq = new ArqTxt("Carros.txt");
		for (int i = 0; i < veiculos.size(); i++) {
			arq.inserir(veiculos.get(i).getPlaca());
			arq.inserir(veiculos.get(i).getMarca());
			arq.inserir(veiculos.get(i).getModelo());
			arq.inserir(Short.toString(veiculos.get(i).getAno()));
		}
		if (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Sair?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	public boolean verPlaca(String placa) {
		for (int i = 0; i < veiculos.size(); i++) {
			if (veiculos.get(i).getPlaca().equals(placa)){
				return false;
			}
		}
		return true;
	}
	
	public void exibe(ControleVeiculo cv) {
		this.cv = cv;
		exibeInfos();
	}
	
	private void exibeInfos() {
		for(int i = 0; i < veiculos.size(); i++) {
			tableDados.setValueAt(" ", i, 0);
			tableDados.setValueAt(" ", i, 1);
			tableDados.setValueAt(" ", i, 2);
			tableDados.setValueAt(" ", i, 3);
			tableDados.setValueAt(" ", i+1, 0);
			tableDados.setValueAt(" ", i+1, 1);
			tableDados.setValueAt(" ", i+1, 2);
			tableDados.setValueAt(" ", i+1, 3);
		}
		for(int i = 0; i < veiculos.size() && veiculos.get(i) != null; i++) {
			tableDados.setValueAt(veiculos.get(i).getPlaca(), i, 0);
			tableDados.setValueAt(veiculos.get(i).getMarca(), i, 1);
			tableDados.setValueAt(veiculos.get(i).getModelo(), i, 2);
			tableDados.setValueAt(veiculos.get(i).getAno(), i, 3);
		}
	}
	
	public App() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(App.class.getResource("/br/com/unifacear/images/icon.png")));
		setResizable(false);
		setTitle("PEDRO MOTORS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 530);
		PanelOut = new JPanel();
		PanelOut.setBackground(Color.DARK_GRAY);
		PanelOut.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(PanelOut);
		PanelOut.setLayout(null);
		
		JPanel PanelLeftUp = new JPanel();
		PanelLeftUp.setBackground(Color.WHITE);
		PanelLeftUp.setBounds(8, 8, 270, 475);
		PanelOut.add(PanelLeftUp);
		PanelLeftUp.setLayout(null);
		
		txtPlaca = new JTextField();
		txtPlaca.setBounds(99, 267, 151, 42);
		PanelLeftUp.add(txtPlaca);
		txtPlaca.setFont(new Font("Arial", Font.PLAIN, 15));
		txtPlaca.setColumns(10);
		
		txtAno = new JTextField();
		txtAno.setBounds(99, 423, 151, 42);
		PanelLeftUp.add(txtAno);
		txtAno.setFont(new Font("Arial", Font.PLAIN, 15));
		txtAno.setColumns(10);
		
		txtModelo = new JTextField();
		txtModelo.setBounds(99, 371, 151, 42);
		PanelLeftUp.add(txtModelo);
		txtModelo.setFont(new Font("Arial", Font.PLAIN, 15));
		txtModelo.setColumns(10);
		
		txtMarca = new JTextField();
		txtMarca.setBounds(99, 319, 151, 42);
		PanelLeftUp.add(txtMarca);
		txtMarca.setFont(new Font("Arial", Font.PLAIN, 15));
		txtMarca.setColumns(10);
		
		JLabel lblMarca = new JLabel("Marca");
		lblMarca.setBounds(29, 318, 60, 42);
		PanelLeftUp.add(lblMarca);
		lblMarca.setFont(new Font("Rubik", Font.PLAIN, 18));
		
		JLabel lblModelo = new JLabel("Modelo");
		lblModelo.setBounds(19, 370, 70, 42);
		PanelLeftUp.add(lblModelo);
		lblModelo.setFont(new Font("Rubik", Font.PLAIN, 18));
		
		JLabel lblPlaca = new JLabel("Placa");
		lblPlaca.setBounds(29, 266, 60, 42);
		PanelLeftUp.add(lblPlaca);
		lblPlaca.setFont(new Font("Rubik", Font.PLAIN, 18));
		
		JLabel lblAno = new JLabel("Ano");
		lblAno.setBounds(48, 422, 49, 42);
		PanelLeftUp.add(lblAno);
		lblAno.setFont(new Font("Rubik", Font.PLAIN, 18));
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.BLACK);
		panel.setBounds(15, 10, 240, 240);
		PanelLeftUp.add(panel);
		panel.setLayout(null);
		
		JLabel lblIcon = new JLabel("");
		lblIcon.setForeground(Color.WHITE);
		lblIcon.setIcon(new ImageIcon(App.class.getResource("/br/com/unifacear/images/car.png")));
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setBounds(0, 0, 240, 240);
		panel.add(lblIcon);
		
		JPanel panelRight = new JPanel();
		panelRight.setBackground(Color.WHITE);
		panelRight.setBounds(288, 8, 488, 475);
		PanelOut.add(panelRight);
		panelRight.setLayout(null);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnExcluir.setBackground(new Color(220, 98, 94));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnExcluir.setBackground(new Color(250, 128, 114));
			}
		});
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setBounds(189, 395, 107, 42);
		panelRight.add(btnExcluir);
		btnExcluir.setForeground(Color.BLACK);
		btnExcluir.setFont(new Font("Rubik", Font.PLAIN, 18));
		btnExcluir.setFocusPainted(false);
		btnExcluir.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnExcluir.setBackground(new Color(250, 128, 114));
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(337, 395, 107, 42);
		panelRight.add(btnAtualizar);
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pos = tableDados.getSelectedRow();
					if (txtPlaca.getText() == veiculos.get(pos).getPlaca() && txtMarca.getText().isBlank() == false && txtModelo.getText().isBlank() == false && txtMarca.getText().isBlank() == false && txtAno.getText().isBlank() == false) {
						veiculos.get(pos).setMarca(txtMarca.getText().toUpperCase());
						veiculos.get(pos).setModelo(txtModelo.getText().toUpperCase());
						veiculos.get(pos).setPlaca(txtPlaca.getText().toUpperCase());
						veiculos.get(pos).setAno(Short.parseShort(txtAno.getText()));
						exibe(cv);
						JOptionPane.showMessageDialog(null, "Dados do veículo '" + txtPlaca.getText().toUpperCase() + "' foram atualizados com sucesso!");
					} else if (txtPlaca.getText() != veiculos.get(pos).getPlaca()) {
						JOptionPane.showMessageDialog(null, "Você não pode alterar uma placa!");
					}else {
						JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
					}
					
				} catch(Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "Não foi possivel alterar os dados do veículo!");
				}
				
			}
		});
		btnAtualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtualizar.setBackground(new Color(124, 218, 144));
			}
			public void mouseExited(MouseEvent e) {
				btnAtualizar.setBackground(new Color(144, 238, 144));
			}
		});
		btnAtualizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAtualizar.setForeground(Color.BLACK);
		btnAtualizar.setFont(new Font("Rubik", Font.PLAIN, 18));
		btnAtualizar.setFocusPainted(false);
		btnAtualizar.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnAtualizar.setBackground(new Color(144, 238, 144));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 471, 350);
		panelRight.add(scrollPane);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		tableDados = new JTable();
		tableDados.setToolTipText("");
		tableDados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int pos = tableDados.getSelectedRow();
					txtPlaca.setText(tableDados.getValueAt(pos, 0).toString());
					txtMarca.setText(tableDados.getValueAt(pos, 1).toString());
					txtModelo.setText(tableDados.getValueAt(pos, 2).toString());
					txtAno.setText(tableDados.getValueAt(pos, 3).toString());
				} catch(Exception e1) {
					e1.printStackTrace();
					txtPlaca.setText("");
					txtMarca.setText("");
					txtModelo.setText("");
					txtAno.setText("");
				}
			}
		});
		tableDados.setGridColor(Color.DARK_GRAY);
		tableDados.setForeground(Color.BLACK);
		tableDados.setBackground(new Color(245, 245, 245));
		tableDados.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableDados.setFont(new Font("Arial", Font.PLAIN, 12));
		tableDados.setRowHeight(30);
		tableDados.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Placa", "Marca", "Modelo", "Ano"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Short.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableDados.getColumnModel().getColumn(0).setResizable(false);
		tableDados.getColumnModel().getColumn(0).setPreferredWidth(20);
		tableDados.getColumnModel().getColumn(0).setMinWidth(10);
		tableDados.getColumnModel().getColumn(1).setResizable(false);
		tableDados.getColumnModel().getColumn(2).setResizable(false);
		tableDados.getColumnModel().getColumn(2).setPreferredWidth(190);
		tableDados.getColumnModel().getColumn(3).setResizable(false);
		tableDados.getColumnModel().getColumn(3).setPreferredWidth(15);
		tableDados.getColumnModel().getColumn(3).setMinWidth(10);
		scrollPane.setViewportView(tableDados);
		
		JButton btnCadastro = new JButton("Cadastrar");
		btnCadastro.setBounds(41, 395, 107, 42);
		panelRight.add(btnCadastro);
		btnCadastro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCadastro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCadastro.setBackground(new Color(173, 216, 230));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnCadastro.setBackground(new Color(210, 250, 255));

			}
		});
		btnCadastro.setForeground(Color.BLACK);
		btnCadastro.setFont(new Font("Rubik", Font.PLAIN, 18));
		btnCadastro.setFocusPainted(false);
		btnCadastro.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnCadastro.setBackground(new Color(210, 250, 255));
		btnCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Veiculo veiculo = new Veiculo();
				try {
					if (txtMarca.getText().isBlank() == false && txtModelo.getText().isBlank() == false && txtMarca.getText().isBlank() == false && txtAno.getText().isBlank() == false) {
						if (verPlaca(txtPlaca.getText().toUpperCase()) == true) {
							veiculo.setMarca(txtMarca.getText().toUpperCase());
							veiculo.setModelo(txtModelo.getText().toUpperCase());
							veiculo.setPlaca(txtPlaca.getText().toUpperCase());
							veiculo.setAno(Short.parseShort(txtAno.getText()));
							veiculo.setAtivo(true);
							if(cv.salvar(veiculo) == true) {
								JOptionPane.showMessageDialog(null, "Veículo '" + txtPlaca.getText() + "' cadastrado com sucesso!");
								txtAno.setText("");
								txtMarca.setText("");
								txtModelo.setText("");
								txtPlaca.setText("");
								txtMarca.requestFocus();
								exibe(cv);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Placa '" + txtPlaca.getText() + "' já cadastrada no sistema!");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erro ao cadastrar veículo. Verifique os campos e tente novamente");
				}
			}
			
		});
		
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pos = tableDados.getSelectedRow();
					if (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o veículo '" + veiculos.get(pos).getPlaca() + "'?", "CONFIRME", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(null, "Veículo '" + veiculos.get(pos).getPlaca() + "' foi removido com sucesso!");
						veiculos.remove(pos);
						tableDados.setValueAt("", pos, 0);
						tableDados.setValueAt("", pos, 1);
						tableDados.setValueAt("", pos, 2);
						tableDados.setValueAt("", pos, 3);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Não foi possível remover o veículo!");
				}
				exibe(cv);
			}
		});
		txtAno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
	                btnCadastro.doClick();
               }
			}
		});
		exibe(cv);
	}
}
