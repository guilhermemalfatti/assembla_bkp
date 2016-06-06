package br.com.upf.sd;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Guilherme Malfatti e Alex Spenassato
 */
public class UDPClient {

    private static Scanner ler;

	public static void main(String args[]) {
        String host = "localhost";
        System.out.println("Cliente");
        // obtem endereço IP do servidor com o DNS
        InetAddress IPAddress = null;
		try {
			IPAddress = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Uma exceção foi gerada ao obter o endereco IP. O cliente sera finalizado!");
			System.exit(1);
		}

        while (true) {
        	byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
        	// declara socket cliente
            DatagramSocket clientSocket = null;
			try {
				clientSocket = new DatagramSocket();
			} catch (SocketException e) {
				e.printStackTrace();
				System.out.println("Erro no socket, o programa será finalizado!");
				System.exit(1);
			}
			//LE DO TECLADO
			System.out.println("Digite a opera��o desejada :");
			ler = new Scanner(System.in);
			String mensagem = ler.nextLine();
			StringBuffer strBuff =  new StringBuffer(mensagem);
			
			if(strBuff.toString().equalsIgnoreCase("dia")){
				//le do teclado a data
                System.out.println("Digite a data no formato dd/mm/aaaa: ");
                ler = new Scanner(System.in);
                String msg = ler.nextLine();
                strBuff.append(";");
                strBuff.append(msg);
			} else if(mensagem.equalsIgnoreCase("fuso")){
				System.out.println("Digite o timezone Ex: US/Hawaii ");
                ler = new Scanner(System.in);
                String msg = ler.nextLine();
                strBuff.append(";");
                strBuff.append(msg);
			}
			
			sendData = strBuff.toString().getBytes();
			// cria pacote com o dado, o endereço do server e porta do servidor
			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, IPAddress, 1972);
			//envia o pacote
			try {
				clientSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Erro ao enviar os dados para o servidor. O cliente será finalizado!");
				System.exit(1);
			}
			//Encerra o programa caso o usuario digite kill
			if (strBuff.toString().equalsIgnoreCase("Kill")) {
				System.out.println("Cliente acabou de ser desativado.");
				System.exit(0);
			}
			// declara o pacote a ser recebido
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			// recebe pacote do servidor
			try {
				clientSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Erro ao receber os dados do servidor. O cliente será finalizado!");
				System.exit(1);
			}
			// separa somente o dado recebido
			String recebida = new String(receivePacket.getData());
			// mostra no vídeo
			System.out.println("FROM SERVER: " + recebida.trim());
			
			
		}
    }
}
