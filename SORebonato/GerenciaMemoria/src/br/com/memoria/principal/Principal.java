//Alex Spenassato 78237 e Guilherme Malfatti 104484
package br.com.memoria.principal;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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

import br.com.memoria.util.Memoria;
import br.com.memoria.util.MemoriaVO;
import br.com.memoria.util.Util;

public class Principal {

	private JFrame frame;
	
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextArea textArea;
	private JRadioButton firstFit;
	private JRadioButton partVariavel;
	private JFileChooser filechooser = new JFileChooser();
	private static final int TAMANHO_SO = 25; 
	private static final int CAPACIDADE_MEMORIA = 210; 

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
		frame.setBounds(100, 100, 538, 334);
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
						if(firstFit.isSelected()){
							firstFit();
						} else {
							tamVariavel();
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
		
		this.firstFit = new JRadioButton("First-Fit");
		firstFit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(filechooser.getSelectedFile() == null){
						JOptionPane.showMessageDialog(null, "Selecione o arquivo!");
					}else{
						firstFit();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		buttonGroup.add(firstFit);
		firstFit.setSelected(true);
		firstFit.setBounds(10, 46, 79, 23);
		frame.getContentPane().add(firstFit);
		
		this.partVariavel = new JRadioButton("Tamanho Variável");
		partVariavel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(filechooser.getSelectedFile() == null){
						JOptionPane.showMessageDialog(null, "Selecione o arquivo!");
					}else{
						tamVariavel();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		buttonGroup.add(partVariavel);
		partVariavel.setBounds(91, 46, 119, 23);
		frame.getContentPane().add(partVariavel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(11, 76, 512, 223);
		frame.getContentPane().add(scrollPane);
		
		this.textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
	}
	
	private void firstFit() throws Exception {
		textArea.setText("");
		List<MemoriaVO> listaLidos = new Util().leArquivo(this.filechooser.getSelectedFile().getPath());
		
		int espacoLivre = CAPACIDADE_MEMORIA;
		int intervaloIn = 0;
		int intervaloOut = 0;
		
		boolean so = true;
		for (MemoriaVO item : listaLidos) {
			if(espacoLivre > 0 && espacoLivre >= item.getEspaco()){
				if(so){
        			textArea.append("Capacidade: "+CAPACIDADE_MEMORIA+"\n");
        			textArea.append("Alocação contígua particionada dinâmica"+"\n");
        			textArea.append("Mecanismo: First-fit"+"\n");
		
					intervaloOut += TAMANHO_SO -1;
					espacoLivre -= TAMANHO_SO;
					textArea.append(intervaloIn+" - "+intervaloOut+" -> "+"Sistema Operacional "+"\n");
					so = false;
				}
				intervaloIn = intervaloOut +1;
				intervaloOut += item.getEspaco() +1;
				espacoLivre -= item.getEspaco();
				item.setAlocado(true);
				textArea.append(intervaloIn+" - "+intervaloOut+" -> "+"Pid: "+item.getpId()+" Uso: "+item.getEspaco()+"\n");
			}
		}
		//calcula espaço livre
		if(espacoLivre > 0){
			textArea.append(intervaloOut +1 +" - "+CAPACIDADE_MEMORIA+" -> "+"Livre"+"\n");
		}
		//Mostra processos aguardando memoria
		int quantidade = 0;
		String mostraProcessos = "";
		for (MemoriaVO item : listaLidos) {	
			if(!item.getAlocado()){
				quantidade++;
				mostraProcessos += item.getpId()+" ";
			}
		}
		textArea.append("Aguardando: "+quantidade+" Processo(s): "+mostraProcessos+"\n");
	}

	private void tamVariavel() throws Exception {//begin func
		textArea.setText("");
		List<MemoriaVO> listaLidos = new Util().leArquivo(this.filechooser.getSelectedFile().getPath());
		int acum = 0 ;
		for(int i=0; i<10; i++){
			acum += listaLidos.get(i).getTempoCpu();
			listaLidos.get(i).setTempoSaidaCPU(acum);
		}
		
		Memoria particao01 = new Memoria();
		Memoria particao02 = new Memoria();
		Memoria particao03 = new Memoria();
		Memoria particao04 = new Memoria();
		Memoria particao05 = new Memoria();
		Memoria particao06 = new Memoria();

		particao01.setParticao(40);
		particao02.setParticao(45);
		particao03.setParticao(25);
		particao04.setParticao(15);
		particao05.setParticao(50);
		particao06.setParticao(35);
		
		int tempo = 0; 
		Boolean soAlocado = false;
	
		while(true){//begin while
			tempo ++;
			if(!soAlocado){
				particao01.setParticao(40-25);
				particao01.setOcupado(true);
				soAlocado = true;
				//System.out.print("capacidade: 210\nEstatica, Tamanho - variavel\n6 partições: 40/45/25/15/50/35\n0 - 24 -> Sistema operacional\n");
				textArea.append("Capacidade: 210\nAlocação contígua particionada estática\n6 partições de: 40/45/25/15/50/35\n0 - 24 -> Sistema operacional\n");
			}
			
			//for(MemoriaVO item : listaLidos){//processo//fazer um for nomral ->listaLidos.get(i).setalocado(true);
			for(int i=0 ; i<=9; i++){
				//System.out.print(i+"\n");
					if(!listaLidos.get(i).getAlocado()){//begin if
						if(listaLidos.get(i).getEspaco() <= particao02.getParticao() && particao02.getOcupado() == false && listaLidos.get(i).getAlocado() == false){
							particao02.setOcupado(true);
							particao02.setParticao(45 - listaLidos.get(i).getEspaco());
							particao02.setPid(listaLidos.get(i).getpId());
							listaLidos.get(i).setAlocado(true);
							//System.out.print("39 - 84 ->Pid: "+particao02.getPid()+ " Uso: "+listaLidos.get(particao02.getPid()-1).getEspaco()+" D: "+particao02.getParticao()+"\n");
							textArea.append("39 - 84 ->Pid: "+particao02.getPid()+ " Uso: "+listaLidos.get(particao02.getPid()-1).getEspaco()+" D: "+particao02.getParticao()+"\n");
						}
						
						
						if(listaLidos.get(i).getEspaco() <= particao03.getParticao() && particao03.getOcupado() == false && listaLidos.get(i).getAlocado() == false){
							particao03.setOcupado(true);
							particao03.setParticao(25 - listaLidos.get(i).getEspaco());
							particao03.setPid(listaLidos.get(i).getpId());
							listaLidos.get(i).setAlocado(true);
							//System.out.print("85 - 109 ->Pid: "+particao03.getPid()+ " Uso: "+listaLidos.get(particao03.getPid()-1).getEspaco()+" D: "+particao03.getParticao()+"\n");
							textArea.append("85 - 109 ->Pid: "+particao03.getPid()+ " Uso: "+listaLidos.get(particao03.getPid()-1).getEspaco()+" D: "+particao03.getParticao()+"\n");
						}
						
	
						if(listaLidos.get(i).getEspaco() <= particao04.getParticao() && particao04.getOcupado() == false && listaLidos.get(i).getAlocado() == false){
							particao04.setOcupado(true);
							particao04.setParticao(15 - listaLidos.get(i).getEspaco());
							particao04.setPid(listaLidos.get(i).getpId());
							listaLidos.get(i).setAlocado(true);
						//	System.out.print("110 - 124 ->Pid: "+particao04.getPid()+ " Uso: "+listaLidos.get(particao04.getPid()-1).getEspaco()+" D: "+particao04.getParticao()+"\n");
							textArea.append("110 - 124 ->Pid: "+particao04.getPid()+ " Uso: "+listaLidos.get(particao04.getPid()-1).getEspaco()+" D: "+particao04.getParticao()+"\n");
						}
						
						
						if(listaLidos.get(i).getEspaco() <= particao05.getParticao() && particao05.getOcupado() == false && listaLidos.get(i).getAlocado() == false){
							particao05.setOcupado(true);
							particao05.setParticao(50 - listaLidos.get(i).getEspaco());
							particao05.setPid(listaLidos.get(i).getpId());
							listaLidos.get(i).setAlocado(true);
							//System.out.print("125 - 174 ->Pid: "+particao05.getPid()+ " Uso: "+listaLidos.get(particao05.getPid()-1).getEspaco()+" D: "+particao05.getParticao()+"\n");
							textArea.append("125 - 174 ->Pid: "+particao05.getPid()+ " Uso: "+listaLidos.get(particao05.getPid()-1).getEspaco()+" D: "+particao05.getParticao()+"\n");
						}
						
						
						if(listaLidos.get(i).getEspaco() <= particao06.getParticao() && particao06.getOcupado() == false && listaLidos.get(i).getAlocado() == false){
							particao06.setOcupado(true);
							particao06.setParticao(35 - listaLidos.get(i).getEspaco());
							particao06.setPid(listaLidos.get(i).getpId());
							listaLidos.get(i).setAlocado(true);
							//System.out.print("175 - 209 ->Pid: "+particao06.getPid()+ " Uso: "+listaLidos.get(particao06.getPid()-1).getEspaco()+" D: "+particao06.getParticao()+"\n");
							textArea.append("175 - 209 ->Pid: "+particao06.getPid()+ " Uso: "+listaLidos.get(particao06.getPid()-1).getEspaco()+" D: "+particao06.getParticao()+"\n");
						}
						
						if(particao02.getOcupado()){
							if(listaLidos.get(particao02.getPid() -1).getTempoSaidaCPU() == tempo){
								particao02.setOcupado(false);
								particao02.setPid(0);
								particao02.setParticao(45);
							}
						}
						
				
						if(particao03.getOcupado()){
							if(listaLidos.get(particao03.getPid() -1).getTempoSaidaCPU() == tempo){
								particao03.setOcupado(false);
								particao03.setPid(0);
								particao03.setParticao(25);
							}
						}
						
						if(particao04.getOcupado()){
							if(listaLidos.get(particao04.getPid() -1).getTempoSaidaCPU() == tempo){
								particao04.setOcupado(false);
								particao04.setPid(0);
								particao04.setParticao(15);
							}
						}
						
						if(particao05.getOcupado()){
							if(listaLidos.get(particao05.getPid() -1).getTempoSaidaCPU() == tempo){
								particao05.setOcupado(false);
								particao05.setPid(0);
								particao05.setParticao(50);
							}
						}
						
						if(particao06.getOcupado()){
							if(listaLidos.get(particao06.getPid() -1).getTempoSaidaCPU() == tempo){
								particao06.setOcupado(false);
								particao06.setPid(0);
								particao06.setParticao(35);
							}
						}
				}//end if
				if(!listaLidos.get(i).getAlocado()){
					i --;
					tempo ++;
					//System.out.print("Aguradando Liberaçao memória...\n");
					textArea.append("Aguardando liberaçao de memória...\n");
				}
			}
			
			if(listaLidos.get(9).getAlocado()){
				break; 
				
			}
			
		}//end while
		
		
	}//end func
}
