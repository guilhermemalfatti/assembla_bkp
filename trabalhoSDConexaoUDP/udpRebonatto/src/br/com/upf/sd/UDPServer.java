/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.upf.sd;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author Guilherme Malfatti e Alex Spenassato
 */

public class UDPServer {
    public static void main(String args[]) {
        // cria socket do servidor com a porta 1972
        int serverPort = 1972;
        
        
        System.out.println("Servidor");

        DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket(serverPort);
		} catch (SocketException e1) {
			e1.printStackTrace();
			System.out.println("Uma excecao foi gerada e o servidor sera finalizado");
			System.exit(1);
		}

        while (true){
        	byte[] receiveData = new byte[1024];
            // declara o pacote a ser recebido
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            // recebe o pacote do cliente
            try {
				serverSocket.receive(receivePacket);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("Uma excecao foi gerada e o servidor sera finalizado");
				System.exit(1);
			}
            // pega os dados, o endereço IP e a porta do cliente
            // para poder mandar a msg de volta
            String vetOper = new String(receivePacket.getData());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            
            String[] operacao = vetOper.split(";");
            
            if(operacao[0].trim().equalsIgnoreCase("Data")){
            	DateFormat dfmt = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy'.'"); 
            	Date hoje = Calendar.getInstance(Locale.getDefault()).getTime(); 
            	
				enviaPacote(dfmt.format(hoje), IPAddress, port, serverSocket, operacao[0]);
	            
            } else if(operacao[0].trim().equalsIgnoreCase("Hora")){
            	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            	Date data = new Date();
            	String dataFormatada = dateFormat.format(data);	

 				enviaPacote(dataFormatada, IPAddress, port, serverSocket, operacao[0]);
 				
            } else if(operacao[0].trim().equalsIgnoreCase("Dia")){
            	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            	formato.setLenient(false); // nao tolera datas inv�lidas
				Date dataFormatada = null;
				boolean erro = false;
				try {
					dataFormatada = formato.parse(operacao[1].trim());
				} catch (ParseException e) {
					String msgErro = new String("A data digitada eh invalida!");
	 				enviaPacote(msgErro, IPAddress, port, serverSocket, operacao[0]);
	 	            erro = true;
				}	

				if(!erro){
					Calendar c = Calendar.getInstance();
	 				c.setTime(dataFormatada);
	 				String dia = String.valueOf(c.get(Calendar.DAY_OF_YEAR));
	 					
	 				enviaPacote(dia, IPAddress, port, serverSocket, operacao[0]);
				} 
            } else if(operacao[0].trim().equalsIgnoreCase("fuso")) {
                String[] sm = TimeZone.getAvailableIDs();
                //verifica se o timezone digitado � valido
                boolean ok = false;
                for (String tzone : sm) {
					if(tzone.equals(operacao[1].trim())){
						ok = true;
					}
				}
                if(ok){
                	Calendar cal = new GregorianCalendar();
					cal.setTimeZone(TimeZone.getTimeZone(operacao[1].trim()));
					int h = cal.get(Calendar.HOUR_OF_DAY);  
					int m = cal.get(Calendar.MINUTE);  
					int s = cal.get(Calendar.SECOND);  
					
					String horario = operacao[1].trim()+" " + h + "H:" + m + "M:" + s + "S";
					enviaPacote(horario, IPAddress, port, serverSocket, operacao[0]);
                } else {
                	enviaPacote("TimeZone invalido!", IPAddress, port, serverSocket, operacao[0]);
                }
            } else if(operacao[0].trim().equalsIgnoreCase("Kill")){
            	enviaPacote("Server acabou de ser desativado.", IPAddress, port, serverSocket, operacao[0]);
            	System.exit(0);
            } else {
            	enviaPacote("Digite uma operacao valida!", IPAddress, port, serverSocket, operacao[0]);
            }
    }
}
    private static void enviaPacote(String mensagem, InetAddress IPAddress, int port, DatagramSocket serverSocket, String operacaoSolicitada){
    	byte[] sendData = new byte[1024];
		sendData = mensagem.getBytes();
    	// monta o pacote com enderço IP e porta
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        //exibe logs
        mostraLogs(mensagem, IPAddress, port, operacaoSolicitada);
        // envia ao cliente
        try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			System.out.println("Erro ao enviar o pacote!");
		}	
    }
    
    private static void mostraLogs(String mensagem, InetAddress IPAddress, int port, String operacaoSolicitada){
	    System.out.println("**** Logs Servidor - Inicio ****");
	    System.out.println("IP cliente: "+IPAddress.getHostAddress());
	    System.out.println("Porta do cliente: "+port);
	    System.out.println("Operacao Solicitada: "+operacaoSolicitada);
	    System.out.println("Retorno ao cliente: "+mensagem);
	    System.out.println("**** Logs Servidor - Fim ****");
    }
}