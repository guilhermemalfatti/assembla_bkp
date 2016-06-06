/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.upf.sd;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Guilherme Malfatti e Alex Spenassato
 */
// Recebe um pacote de algum cliente
// Separa o dado, o endereço IP e a porta deste cliente
// Transforma em maiúscula
// Envia ao cliente, usando o endereço IP e a porta recebidos

public class UDPServer {
	
	private static Scanner ler;

    public static void main(String args[]) throws Exception {
        // cria socket do servidor com a porta 1972
        int serverPort = 1972;
        byte[] receiveData = new byte[1024];
        
        System.out.println("Servidor");

        DatagramSocket serverSocket = new DatagramSocket(serverPort);

        while (true){
            // declara o pacote a ser recebido
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            // recebe o pacote do cliente
            serverSocket.receive(receivePacket);
            // pega os dados, o endereço IP e a porta do cliente
            // para poder mandar a msg de volta
            String str = new String(receivePacket.getData());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            
            if(str.trim().equalsIgnoreCase("Data")){
            	DateFormat dfmt = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy'.'"); 
            	Date hoje = Calendar.getInstance(Locale.getDefault()).getTime(); 
            	
				byte[] sendData = new byte[1024];
				sendData = dfmt.format(hoje).getBytes();
				
	            // monta o pacote com enderço IP e porta
	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
	            // envia ao cliente
	            serverSocket.send(sendPacket);
	            
            } else if(str.trim().equalsIgnoreCase("Hora")){
            	DateFormat dateFormat = new SimpleDateFormat("HH:mm:s");
            	Date data = new Date();
            	String dataFormatada = dateFormat.format(data);	
            	byte[] sendData = new byte[1024];
 				sendData = dataFormatada.getBytes();
 				
 	            // monta o pacote com enderço IP e porta
 	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
 	            // envia ao cliente
 	            serverSocket.send(sendPacket);
            } else if(str.trim().equalsIgnoreCase("Dia")){
            	//le do teclado a data
                System.out.println("Digite a data no formato dd/mm/aaaa: ");
                ler = new Scanner(System.in);
                String mensagem = ler.nextLine();
                
            	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            	formato.setLenient(false); // nao tolera datas inv�lidas
				Date dataFormatada = null;
				boolean erro = false;
				try {
					dataFormatada = formato.parse(mensagem.trim());
				} catch (ParseException e) {
					String msgErro = new String("A data digitada � invalida!");
					byte[] sendData = new byte[1024];
	 				sendData = msgErro.getBytes();
	 				// monta o pacote com enderço IP e porta
	 	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
	 	            // envia ao cliente
	 	            serverSocket.send(sendPacket);
	 	            erro = true;
				}	

				if(!erro){
					Calendar c = Calendar.getInstance();
	 				c.setTime(dataFormatada);
	 				String dia = String.valueOf(c.get(Calendar.DAY_OF_YEAR));
	 				
	 				byte[] sendData = new byte[1024];
	 				sendData = dia.getBytes();
	 				
	 				// monta o pacote com enderço IP e porta
	 	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
	 	            // envia ao cliente
	 	            serverSocket.send(sendPacket);	
				} 
            } else if(str.trim().equalsIgnoreCase("Data")) {
            	

            	
            }
      
        
       // serverSocket.close();
    }
}
}