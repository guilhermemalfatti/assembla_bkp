package br.com.upf.sd;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Servidor extends Thread {

	private JFrame frame;
	private JTextField porta;
	private JTextArea textArea = null;
	private static double valor = 1;
	private static double acumulador = 0;
	private static String opAnterior = "";
	
	private static long tempoSleep = 0;
	
	private static Map<String, Boolean> map = new HashMap<String, Boolean>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		map.put("opSoma", true);
		map.put("opDiminui", false);
		map.put("opMultiplica", false);
		map.put("opDivide", false);
		map.put("opDorme", false);
		map.put("opPara", false);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servidor window = new Servidor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Servidor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Configura porta:");
		lblNewLabel.setBounds(10, 11, 98, 14);
		frame.getContentPane().add(lblNewLabel);
		
		porta = new JTextField();
		porta.setBounds(109, 8, 63, 22);
		frame.getContentPane().add(porta);
		porta.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 36, 414, 215);
		frame.getContentPane().add(textArea);
		
		JButton btnIniciarServidor = new JButton("Iniciar Servidor");
		btnIniciarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inicializa();
			}
		});
		btnIniciarServidor.setBounds(291, 7, 133, 23);
		frame.getContentPane().add(btnIniciarServidor);
	}
		
	private void inicializa(){
		//inicia a thread
		new Thread(new Servidor()).start();

		//inicia o socket
		Socket s= null;
		ServerSocket serv = null;
		BufferedReader entrada=null;
		try {
			serv = new ServerSocket(Integer.parseInt(porta.getText()));
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		
		//Escreve no textArea
		//textArea.append("Servidor iniciado na porta "+porta.getText());
		//this.textArea.append("Padrão: somando 1");
		
		while(true){
			try{
				s = serv.accept();
				entrada = new BufferedReader(new InputStreamReader(s.getInputStream()));
				
				String[] operacao = entrada.readLine().trim().split(";");

				//Escreve no textArea
				//this.textArea.append("Recebido de "+s.getInetAddress().getHostAddress()+":"+s.getPort()+" comando "+" "+operacao);
				System.out.println("Recebido de "+s.getInetAddress().getHostAddress()+":"+s.getPort()+" comando "+operacao[1]);

				if(operacao[0].equalsIgnoreCase("valor")){
					valor = Double.parseDouble(operacao[1]);
					enviaResposta("From server >>> OK");
					
				} else if(operacao[0].equalsIgnoreCase("reset")){
					acumulador = 0;
					enviaResposta("From server >>> OK");

				} else if(operacao[0].equalsIgnoreCase("soma")){
					valor = Double.parseDouble(operacao[1]);
					setaTrue("opSoma");
					enviaResposta("From server >>> OK");
					
				} else if(operacao[0].equalsIgnoreCase("diminui")){
					valor = Double.parseDouble(operacao[1]);
					setaTrue("opDiminui");
					enviaResposta("From server >>> OK");
					
				} else if(operacao[0].equalsIgnoreCase("multiplica")){
					valor = Double.parseDouble(operacao[1]);
					setaTrue("opMultiplica");
					enviaResposta("From server >>> OK");
					
				} else if(operacao[0].equalsIgnoreCase("divide")){
					valor = Double.parseDouble(operacao[1]);
					setaTrue("opDivide");
					enviaResposta("From server >>> OK");
					
				} else if(operacao[0].equalsIgnoreCase("dorme")){
					tempoSleep = Long.parseLong(operacao[1]);
					setaTrue("opDorme");
					enviaResposta("From server >>> OK");
					
				} else if(operacao[0].equalsIgnoreCase("para")){
					setaTrue("opPara");
					enviaResposta("From server >>> OK");
					break;
				} else {
					enviaResposta("From server >>> OPERACAO INVALIDA!!!");
				}
			}catch(IOException e){
				System.out.println("Algum problema ocorreu para criar ou receber o socket.");
				System.exit(1);
			}catch(Exception e){
				System.out.println("Erro geral.");
				System.exit(1);
			}
		}
		
		try {
			s.close();
			serv.close();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private void setaTrue(String variavel){
		for (Entry<String, Boolean> var : map.entrySet()) {
			if(variavel.equalsIgnoreCase(var.getKey())){
				var.setValue(true);
			} else {
				var.setValue(false);
			}
		}
	}
	
	private void enviaResposta(String msg) throws Exception {
		//resposta ao cliente
		Socket sCli = new Socket("127.0.0.1", 9001);
		PrintStream ps = new PrintStream(sCli.getOutputStream());
		ps.println(msg);
		sCli.close();
		
	}

	@Override
	public void run() {
		while(true){
			try {
				if (map.get("opSoma").booleanValue()) {
					acumulador += valor;
					opAnterior = "opSoma";
				} else if (map.get("opDiminui").booleanValue()) {
					acumulador -= valor;
					opAnterior = "opDiminui";
				} else if (map.get("opMultiplica").booleanValue()) {
					acumulador *= valor;
					opAnterior = "opMultiplica";
				} else if (map.get("opDivide").booleanValue()) {
					acumulador /= valor;
					opAnterior = "opDivide";
				} else if (map.get("opDorme").booleanValue()) {
					Thread.sleep(tempoSleep);
					//opAnterior = "opMultiplica";
					map.put("opDorme", false);
					map.put(opAnterior, true);
				} else if (map.get("opPara").booleanValue()) {
					Thread.currentThread().stop();
				}
				
				System.out.println("Acumulador "+acumulador);
				
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
