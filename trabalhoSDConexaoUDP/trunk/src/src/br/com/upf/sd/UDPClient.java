/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.upf.sd;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 *
 * @author Guilherme Malfatti e Alex Spenassato
 */
// Envia o pacote (string) ao servidor
// Lê um pacote (linha) do servidor e mostra no vídeo

public class UDPClient {

    private static Scanner ler;

	public static void main(String args[]) throws Exception {
        String host = "localhost";

        System.out.println("Cliente");
        // declara socket cliente
        DatagramSocket clientSocket = new DatagramSocket();
        // obtem endereço IP do servidor com o DNS
        InetAddress IPAddress = InetAddress.getByName(host);
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        
        //LE DO TECLADO
        System.out.println("Digite a opera��o desejada :");
        ler = new Scanner(System.in);
        String mensagem = ler.nextLine();
        
        sendData = mensagem.getBytes();
        // cria pacote com o dado, o endereço do server e porta do servidor
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1972);

        //envia o pacote
        clientSocket.send(sendPacket);

        // declara o pacote a ser recebido
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        // recebe pacote do servidor
        clientSocket.receive(receivePacket);
        // separa somente o dado recebido
        String recebida = new String(receivePacket.getData());
        // mostra no vídeo
        System.out.println("FROM SERVER:" + recebida.trim());

        // fecha o cliente
        clientSocket.close();
    }
}
