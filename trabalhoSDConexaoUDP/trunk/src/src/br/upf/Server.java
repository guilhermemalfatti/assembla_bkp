
package br.upf;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author  Guilherme Malfatti; Alex Spenasato
 */

public class Server {

	public static void main(String args[]) throws Exception {
		// cria socket do servidor com a porta 1972
		int serverPort = 1972;
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		System.out.println("Servidor");
		Integer i = 0;
		String mensagem, mensagemErr, str;
		DatagramSocket serverSocket = new DatagramSocket(serverPort);
		DatagramPacket receivePacket;
		DatagramPacket sendPacket;
		receiveData = new byte[1024];
		sendData = new byte[1024];
		
		receivePacket = new DatagramPacket(receiveData,receiveData.length);
		// recebe o pacote do cliente
		serverSocket.receive(receivePacket);
		int port = receivePacket.getPort();
		InetAddress IPAddress = receivePacket.getAddress();
		System.out.println("Ouvindo na porta: " + port);
        System.out.println("Conexao estabelecida com: " + receivePacket.getAddress().getHostAddress());
		do {	
			receivePacket = new DatagramPacket(receiveData,receiveData.length);
			serverSocket.receive(receivePacket);
			str = new String(receivePacket.getData());
			System.out.println(str);
			 if(str.trim().equalsIgnoreCase("date")){
				 DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                 Date data = new Date();
				 mensagem = dateFormat.format(data);				 
				 sendData = mensagem.getBytes();
				 sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, port);
				 serverSocket.send(sendPacket);
			 }else if (str.trim().equalsIgnoreCase("time")) {
				 DateFormat dateFormat = new SimpleDateFormat("HH:mm:s");
                 Date data = new Date();
				 mensagem = dateFormat.format(data);				 
				 sendData = mensagem.getBytes();
				 sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, port);
				 serverSocket.send(sendPacket);	
				
			}else if (str.trim().equalsIgnoreCase("day")) {
				 DateFormat dateFormat = new SimpleDateFormat("D");
                 Date data = new Date();
				 mensagem = dateFormat.format(data);				 
				 sendData = mensagem.getBytes();
				 sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, port);
				 serverSocket.send(sendPacket);	
				 
			}else if (str.trim().equalsIgnoreCase("case")) {				
				 mensagem = str.toUpperCase();			 
				 sendData = mensagem.getBytes();
				 sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, port);
				 serverSocket.send(sendPacket);		
				 
			}else if (str.trim().equalsIgnoreCase("alive")) {
				mensagem = new String(receivePacket.getData());
				sendData = mensagem.getBytes();
				sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
				
			} else{
				mensagemErr = "Nenhuma fução correspondente.";
				sendData = mensagemErr.getBytes();
				sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
//				System.out.println("Log: porta:"+receivePacket.getPort()+" IP:"+receivePacket.getAddress());				
			 }		 
			
		} while (!str.equals("FIM"));

		System.out.println("Conexao encerrada pelo cliente");				
		serverSocket.close();
	}
}