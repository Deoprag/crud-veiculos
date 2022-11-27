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
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
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
import javax.swing.text.MaskFormatter;

import br.com.unifacear.model.Veiculo;
import br.com.unifacear.util.ArqTxt;
import br.com.unifacear.util.ControleVeiculo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class App extends JFrame{
	
	ArqTxt arq = new ArqTxt("Carros.txt");
	ControleVeiculo cv = arq.ler();
	List<Veiculo> veiculos = cv.retorna();

	private JPanel PanelOut;
	private DefaultTableModel model;
	private JTextField txtMarca;
	private JTextField txtModelo;
	private JTextField txtPlaca;
	private JTextField txtAno;
	private JButton btnLimpar;
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
	
	public void limpar() {
		
		txtPlaca.setText("AAA1234");
		txtPlaca.setForeground(new Color(192, 192, 192));
		
		txtMarca.setText("Insira a marca");
		txtMarca.setForeground(new Color(192, 192, 192));
		
		txtModelo.setText("Insira o modelo");
		txtModelo.setForeground(new Color(192, 192, 192));
		
		txtAno.setText("2000");
		txtAno.setForeground(new Color(192, 192, 192));
		
	}
	
	public boolean ano() {
		if(Short.parseShort(txtAno.getText()) > 2022){
			JOptionPane.showMessageDialog(null, "Atenção! A data de fabricação deve ser menor que a atual");
			return false;
		}
		if(Short.parseShort(txtAno.getText()) < 1950) {
			JOptionPane.showMessageDialog(null, "Atenção! Data de fabricação muito antiga");
			return false;
		}
		return true;
	}
	
	public boolean vazio() {
		if(txtPlaca.getText().replace("-", "").isBlank() == true || txtMarca.getText().isBlank() == true || txtModelo.getText().isBlank() == true || txtAno.getText().isBlank() == true) {
			JOptionPane.showMessageDialog(null, "Atenção! Preencha todos os campos.");
			return false;
		}
		if(txtPlaca.getForeground().equals(new Color(192, 192, 192)) || txtMarca.getForeground().equals(new Color(192, 192, 192)) || txtModelo.getForeground().equals(new Color(192, 192, 192)) || txtAno.getForeground().equals(new Color(192, 192, 192))) {
			JOptionPane.showMessageDialog(null, "Atenção! Preencha todos os campos.");
			return false;
		}
		return true;
	}
	
	public void fechar() {
		arq.excluir("Carros.txt");
		ArqTxt arq = new ArqTxt("Carros.txt");
		for (Veiculo veiculo : veiculos) {
			arq.inserir(veiculo.getPlaca());
			arq.inserir(veiculo.getMarca());
			arq.inserir(veiculo.getModelo());
			arq.inserir(Short.toString(veiculo.getAno()));
		}
		if (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Sair?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	public boolean verPlaca(String placa) {
			for (Veiculo veiculo : veiculos) {
				if (veiculo.getPlaca().equals(placa)){
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
		try {
			model.getDataVector().removeAllElements();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i < veiculos.size() && veiculos.get(i) != null; i++) {
			model.addRow(new Object[]{veiculos.get(i).getPlaca(), veiculos.get(i).getMarca(), veiculos.get(i).getModelo(), veiculos.get(i).getAno()});
		}
	}
	
	public App() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(App.class.getResource("/br/com/unifacear/images/icon.png")));
		setResizable(false);
		setTitle("PEDRO MOTORS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 530);
		PanelOut = new JPanel();
		PanelOut.setBackground(new Color(0, 0, 0));
		PanelOut.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(PanelOut);
		PanelOut.setLayout(null);
		
		JPanel PanelLeftUp = new JPanel();
		PanelLeftUp.setBackground(Color.WHITE);
		PanelLeftUp.setBounds(8, 8, 270, 475);
		PanelOut.add(PanelLeftUp);
		PanelLeftUp.setLayout(null);
		
		MaskFormatter mascaraPlaca = null;
		MaskFormatter mascaraAno = null;
		try {
			mascaraPlaca = new MaskFormatter("UUU-#A##");
			mascaraAno = new MaskFormatter("####");
		} catch (ParseException e3) {
			e3.printStackTrace();
		}
		
		txtPlaca = new JFormattedTextField(mascaraPlaca);
		txtPlaca.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtPlaca.getForeground().equals(new Color(192,192,192))) {
					txtPlaca.setText("");
					txtPlaca.setForeground(new Color(0,0,0));
				}
			}
		});
		txtPlaca.setForeground(new Color(192, 192, 192));
		txtPlaca.setBackground(new Color(255, 255, 255));
		txtPlaca.setText("AAA1234");
		txtPlaca.setBorder(null);
		txtPlaca.setBounds(100, 270, 151, 30);
		PanelLeftUp.add(txtPlaca);
		txtPlaca.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtPlaca.setColumns(10);
		
		txtAno = new JFormattedTextField(mascaraAno);
		txtAno.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtAno.getForeground().equals(new Color(192,192,192))) {
					txtAno.setText("");
					txtAno.setForeground(new Color(0,0,0));
				}
			}
		});
		txtAno.setText("2000");
		txtAno.setForeground(new Color(192, 192, 192));
		txtAno.setBackground(new Color(255, 255, 255));
		txtAno.setBorder(null);
		txtAno.setBounds(100, 420, 70, 30);
		PanelLeftUp.add(txtAno);
		txtAno.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtAno.setColumns(10);
		
		txtModelo = new JTextField();
		txtModelo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtModelo.getForeground().equals(new Color(192,192,192))) {
					txtModelo.setText("");
					txtModelo.setForeground(new Color(0,0,0));
				}
			}
		});
		txtModelo.setForeground(new Color(192, 192, 192));
		txtModelo.setBackground(new Color(255, 255, 255));
		txtModelo.setText("Insira o modelo");
		txtModelo.setBorder(null);
		txtModelo.setBounds(100, 370, 151, 30);
		PanelLeftUp.add(txtModelo);
		txtModelo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtModelo.setColumns(10);
		
		txtMarca = new JTextField();
		txtMarca.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtMarca.getForeground().equals(new Color(192,192,192))) {
					txtMarca.setText("");
					txtMarca.setForeground(new Color(0,0,0));
				}
			}
		});
		txtMarca.setForeground(new Color(192, 192, 192));
		txtMarca.setBackground(new Color(255, 255, 255));
		txtMarca.setText("Insira a marca");
		txtMarca.setBorder(null);
		txtMarca.setBounds(100, 320, 151, 30);
		PanelLeftUp.add(txtMarca);
		txtMarca.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtMarca.setColumns(10);
		
		JLabel lblMarca = new JLabel("Marca");
		lblMarca.setBounds(19, 320, 60, 30);
		PanelLeftUp.add(lblMarca);
		lblMarca.setFont(new Font("Rubik", Font.PLAIN, 18));
		
		JLabel lblModelo = new JLabel("Modelo");
		lblModelo.setBounds(16, 370, 70, 30);
		PanelLeftUp.add(lblModelo);
		lblModelo.setFont(new Font("Rubik", Font.PLAIN, 18));
		
		JLabel lblPlaca = new JLabel("Placa");
		lblPlaca.setBounds(19, 270, 60, 30);
		PanelLeftUp.add(lblPlaca);
		lblPlaca.setFont(new Font("Rubik", Font.PLAIN, 18));
		
		JLabel lblAno = new JLabel("Ano");
		lblAno.setBounds(23, 420, 49, 30);
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
		
		JSeparator separator = new JSeparator();
		separator.setBounds(90, 300, 160, 30);
		PanelLeftUp.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(90, 350, 160, 30);
		PanelLeftUp.add(separator_1);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(90, 400, 160, 30);
		PanelLeftUp.add(separator_1_1);
		
		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setBounds(90, 450, 80, 30);
		PanelLeftUp.add(separator_1_1_1);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setFocusable(false);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLimpar.setBackground(new Color(170,170,170));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnLimpar.setBackground(new Color(192, 192, 192));
			}
		});
		btnLimpar.setForeground(new Color(0, 0, 0));
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setFont(new Font("Rubik", Font.PLAIN, 11));
		btnLimpar.setBackground(new Color(220,220,220));
		btnLimpar.setBorder(new LineBorder(new Color(60, 60, 60), 2, true));
		btnLimpar.setBounds(181, 418, 70, 38);
		PanelLeftUp.add(btnLimpar);
		
		JPanel panelRight = new JPanel();
		panelRight.setBackground(new Color(255, 255, 255));
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
		btnExcluir.setBounds(189, 422, 107, 42);
		panelRight.add(btnExcluir);
		btnExcluir.setForeground(Color.BLACK);
		btnExcluir.setFont(new Font("Rubik", Font.PLAIN, 18));
		btnExcluir.setFocusPainted(false);
		btnExcluir.setBorder(new LineBorder(new Color(60, 0, 0), 2, true));
		btnExcluir.setBackground(new Color(250, 128, 114));
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(337, 422, 107, 42);
		panelRight.add(btnAtualizar);
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pos = tableDados.getSelectedRow();
					if (!txtPlaca.getText().equals(veiculos.get(pos).getPlaca())) {
						JOptionPane.showMessageDialog(null, "Você não pode alterar uma placa!");
					} else if (vazio() == true) {
						if(ano() == true) {
							veiculos.get(pos).setMarca(txtMarca.getText().toUpperCase());
							veiculos.get(pos).setModelo(txtModelo.getText().toUpperCase());
							veiculos.get(pos).setPlaca(txtPlaca.getText().toUpperCase());
							veiculos.get(pos).setAno(Short.parseShort(txtAno.getText()));
							exibe(cv);
							JOptionPane.showMessageDialog(null, "Dados do veículo '" + txtPlaca.getText().toUpperCase() + "' foram atualizados com sucesso!");
						}
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
		btnAtualizar.setBorder(new LineBorder(new Color(0, 60, 0), 2, true));
		btnAtualizar.setBackground(new Color(144, 238, 144));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		scrollPane.setBounds(10, 10, 471, 400);
		panelRight.add(scrollPane);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		model = new DefaultTableModel(); 
		model.addColumn("Placa");
		model.addColumn("Marca");
		model.addColumn("Modelo");
		model.addColumn("Ano");
		tableDados = new JTable(model);
		tableDados.setToolTipText("");
		tableDados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x, y;
				x = tableDados.getSelectedRow();
				y = tableDados.getSelectedColumn();
				if (tableDados.getValueAt(x,y) == null) {
					JOptionPane.showMessageDialog(null, "Não é possível recuperar dados de um campo vazio!");
				} else {
					txtPlaca.setForeground(new Color(0,0,0));
					txtMarca.setForeground(new Color(0,0,0));
					txtModelo.setForeground(new Color(0,0,0));
					txtAno.setForeground(new Color(0,0,0));
	
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
			}
		});
		tableDados.setGridColor(Color.DARK_GRAY);
		tableDados.setForeground(Color.BLACK);
		tableDados.setBackground(new Color(245, 245, 245));
		tableDados.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableDados.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tableDados.setRowHeight(30);
		tableDados.getColumnModel().getColumn(0).setResizable(false);
		tableDados.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableDados.getColumnModel().getColumn(0).setMinWidth(10);
		tableDados.getColumnModel().getColumn(1).setResizable(false);
		tableDados.getColumnModel().getColumn(2).setResizable(false);
		tableDados.getColumnModel().getColumn(2).setPreferredWidth(190);
		tableDados.getColumnModel().getColumn(3).setResizable(false);
		tableDados.getColumnModel().getColumn(3).setPreferredWidth(15);
		tableDados.getColumnModel().getColumn(3).setMinWidth(10);
		scrollPane.setViewportView(tableDados);
		
		JButton btnCadastro = new JButton("Cadastrar");
		btnCadastro.setBounds(41, 422, 107, 42);
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
		btnCadastro.setBorder(new LineBorder(new Color(0, 0, 60), 2, true));
		btnCadastro.setBackground(new Color(210, 250, 255));
		btnCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Veiculo veiculo = new Veiculo();
				try {
					if (vazio() == true) {
						if (ano() == true) {
							if (verPlaca(txtPlaca.getText().toUpperCase()) == true) {
								veiculo.setMarca(txtMarca.getText().toUpperCase());
								veiculo.setModelo(txtModelo.getText().toUpperCase());
								veiculo.setPlaca(txtPlaca.getText().toUpperCase());
								veiculo.setAno(Short.parseShort(txtAno.getText()));
								veiculo.setAtivo(true);
								if(cv.salvar(veiculo) == true) {
									JOptionPane.showMessageDialog(null, "Veículo '" + txtPlaca.getText() + "' cadastrado com sucesso!");
									limpar();
									exibe(cv);
								}
							} else {
								JOptionPane.showMessageDialog(null, "Placa '" + txtPlaca.getText() + "' já cadastrada no sistema!");
							}
						}
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
						tableDados.setValueAt(null, pos, 0);
						tableDados.setValueAt(null, pos, 1);
						tableDados.setValueAt(null, pos, 2);
						tableDados.setValueAt(null, pos, 3);
						limpar();
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
