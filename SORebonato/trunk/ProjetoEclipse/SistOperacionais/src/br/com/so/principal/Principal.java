//Alex Spenassato 78237 e Guilherme Malfatti 104484
package br.com.so.principal;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import br.com.so.util.Util;
import br.com.so.util.ValoresVO;

public class Principal {

	private JFrame frame;
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextArea textArea;
	private JRadioButton radioHrrn;
	private JRadioButton radioLoteria;
	private static final int QUANTUM = 3; //Constante que define o tempo
	private JFileChooser filechooser = new JFileChooser();

	/**
	 * Roda a aplicação.
	 */
	public static void main(String[] args) {
		//Seta o look n feel
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
	}

	/**
	 * Cria a aplicação.
	 */
	public Principal() {
		initialize();
	}

	/**
	 * Inicializa o conteúdo do frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 539, 338);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//Botão que seleciona o arquivo para leitura
		JButton btnSelecioneOArquivo = new JButton("Selecione o arquivo");
		btnSelecioneOArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        int returnValue = filechooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		            //Seta no campo texto o caminho do arquivo escolhido
		        	textField.setText(filechooser.getSelectedFile().getPath());
					try {
						//Opção selecionada no radio
						if(radioHrrn.isSelected()){
							calculaEscalHrrn();
						} else {
							calculaEscalLoteria();
						}
					}catch (Exception e) {
						JOptionPane.showMessageDialog(null, e);
					}
		        }
			}
		});
		
		btnSelecioneOArquivo.setBounds(11, 11, 125, 28);
		frame.getContentPane().add(btnSelecioneOArquivo);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(143, 11, 380, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		this.radioHrrn = new JRadioButton("HRRN");
		radioHrrn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(filechooser.getSelectedFile() == null){
						JOptionPane.showMessageDialog(null, "Selecione o arquivo!");
					}else{
						calculaEscalHrrn();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		buttonGroup.add(radioHrrn);
		radioHrrn.setSelected(true);
		radioHrrn.setBounds(10, 46, 61, 23);
		frame.getContentPane().add(radioHrrn);
		
		this.radioLoteria = new JRadioButton("Loteria");
		radioLoteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(filechooser.getSelectedFile() == null){
						JOptionPane.showMessageDialog(null, "Selecione o arquivo!");
					}else{
						calculaEscalLoteria();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		buttonGroup.add(radioLoteria);
		radioLoteria.setBounds(83, 46, 66, 23);
		frame.getContentPane().add(radioLoteria);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(11, 76, 512, 223);
		frame.getContentPane().add(scrollPane);
		
		this.textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
	}
	
	// Método aonde será calculado o escalonamento (recebe por parâmetro a lista de objetos lidos)
	private void calculaEscalHrrn() throws Exception {
		Util util = new Util();
		List<ValoresVO> listaLidos = util.leArquivo(this.filechooser.getSelectedFile().getPath());
		
		Integer pid;
		int teste =0;
		Integer tempoCpu = 0;
		Integer tempoEntrada = 0;
		int usoCPU = 0; 
		Integer primeiro = 1;
		float prioridade = 0; 
        List<ValoresVO> listaPrioridade = new ArrayList<ValoresVO>();      
        
        boolean ok = false;  
        do {
        	ok = false;
        	for (ValoresVO item : listaLidos) {
        		if (primeiro == 1 ) {//somente p/ o primeiro processo
        			tempoEntrada += item.getTempCpu();
        			usoCPU += item.getTempCpu();     
        			primeiro = 0;
        			item.setEscalonado(true);
        			tempoCpu = item.getTempCpu();
        			//Mostra no textArea
        			textArea.setText("");
        			textArea.append("Escalonamento HRRN"+"\n");
        			textArea.append("Tempo - Pid - Uso de CPU"+"\n");
        			textArea.append(item.getEntrSist()+" - "+item.getpId()+" - "+item.getTempCpu()+"\n");
        			continue;
        		}

        		if (item.getEntrSist() <= usoCPU && !item.getEscalonado()) {
        			prioridade = ((usoCPU - item.getEntrSist()) + item.getTempCpu()) / item.getTempCpu();		
        			item.setPrior(prioridade);
        			listaPrioridade.add(item);
        			Collections.sort(listaPrioridade);
        		}     
        	}        	
        	pid = listaPrioridade.get(listaPrioridade.size() -1).getpId();
        	tempoCpu = listaPrioridade.get(listaPrioridade.size() -1).getTempCpu();
        	listaPrioridade.get(listaPrioridade.size() -1).setEscalonado(true);//marcar como escalonado
        	tempoEntrada += listaPrioridade.get(listaPrioridade.size()-1).getTempCpu();
        	teste = listaPrioridade.get(listaPrioridade.size()-1).getpId();
        	textArea.append(usoCPU+" - "+pid+" - "+tempoCpu+"\n");
        	usoCPU += listaPrioridade.get(listaPrioridade.size() -1).getTempCpu();
        	
        	listaPrioridade.removeAll(listaPrioridade);//remove toda lista        
        	
        	//Mostra no textArea
        	for (ValoresVO itemEscalonado : listaLidos) {
        		if(!itemEscalonado.getEscalonado()){
        			ok = true;
        			break;
        		}
        	}
		} while (ok);   
	}
	
	private void calculaEscalLoteria() throws Exception {
		List<ValoresVO> listaLidos = new Util().leArquivo(this.filechooser.getSelectedFile().getPath());
		List<ValoresVO> listaEscalonados = new ArrayList<ValoresVO>();

		Integer tempoDecorrido = 0;
		boolean ok = false;
		Random ran = new Random(313);
		do {
			ok = false;

			//Distribui 1 bilhete para cada processo
			for (ValoresVO procBilhete : listaLidos) {
				procBilhete.setBilhete(ran.nextInt());
			}
			
			//Sorteia um número que esteja setado na lista de processos
			int numeroSorteado = listaLidos.get( ran.nextInt(listaLidos.size()) ).getBilhete();
			
			for (ValoresVO processo : listaLidos) {
				//Testa se foi o sorteado e se o processo já não foi escalonado
				if(numeroSorteado == processo.getBilhete() && !processo.getEscalonado()){
					processo.setTempoEntrada(tempoDecorrido);

					//Incrementa o tempoDecorrido de acordo com os seguintes critérios
					if( (processo.getTempCpu().intValue() == QUANTUM) ){
						tempoDecorrido += QUANTUM;
						processo.setUsoCpu( QUANTUM );
					} else {
						if ( processo.getTempCpu().intValue() > QUANTUM ) {
							tempoDecorrido += QUANTUM;
							processo.setUsoCpu( QUANTUM );
						} else {
							tempoDecorrido += processo.getTempCpu().intValue();
							processo.setUsoCpu( processo.getTempCpu() );
						}
					}

					//Calcula o tempo restante com base no QUANTUM
					processo.setTempCpu( processo.getTempCpu() - QUANTUM );
					
					//Se o tempo for menor ou igual a 0, setar processo como já escalonado
					if(processo.getTempCpu() <= 0){
						processo.setEscalonado(true);
					}
					
					ValoresVO processoAux = new ValoresVO();
					processoAux.setpId(processo.getpId());
					processoAux.setTempoEntrada(processo.getTempoEntrada());
					processoAux.setUsoCpu(processo.getUsoCpu());
					
					//Adiciona o processo na lista de escalonamento
					listaEscalonados.add(processoAux);
				}	
			}
			
			//Para ser usado como critério de parada do laço while...Percorre toda lista procurando pelos não-escalonados
			for (ValoresVO processo : listaLidos) {
				if(!processo.getEscalonado()){
					ok = true;
					break;
				}	
			}
		} while (ok);
			
		//mostra lista dos escalonados no textArea
		textArea.setText("");
		textArea.append("Escalonamento Loteria"+"\n");
		textArea.append("Tempo - Pid - Uso de CPU "+"\n");
		for (ValoresVO processo : listaEscalonados) {
			textArea.append(processo.getTempoEntrada()+" - "+processo.getpId()+" - "+processo.getUsoCpu()+"\n");
		}
	}	
}
