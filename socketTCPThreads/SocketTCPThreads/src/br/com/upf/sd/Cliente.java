package br.com.upf.sd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	private static Scanner ler;

	public static void main(String[] args) {
		
		//Declaro o socket cliente
		Socket s = null;
		
		//Declaro a Stream de saida de dados
		PrintStream ps = null;
		
		//inicia o socket
		Socket sSrv= null;
		ServerSocket serv = null;
		BufferedReader entrada=null;
		try {
			serv = new ServerSocket(9001);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		
		while(true){
			try{
				s = new Socket("127.0.0.1", 9000);
				ps = new PrintStream(s.getOutputStream());
				
				//LE DO TECLADO
				System.out.println("Digite a operação desejada :");
				ler = new Scanner(System.in);
				String mensagem = ler.nextLine();
				StringBuffer strBuff =  new StringBuffer(mensagem);
				
				if(mensagem.trim().equalsIgnoreCase("reset") ){
					//envia ao servidor
	                ps.println(mensagem);
				} else if (mensagem.trim().equalsIgnoreCase("para")){
					//envia ao servidor
	                ps.println(mensagem);
					break;
				} else {
					System.out.println("Digite o valor: ");
	                ler = new Scanner(System.in);
	                String msg = ler.nextLine();
	                strBuff.append(";");
	                strBuff.append(msg);
	                
	                //envia ao servidor
	                ps.println(strBuff);
				}
				
				sSrv = serv.accept();
				entrada = new BufferedReader(new InputStreamReader(sSrv.getInputStream()));
				
				System.out.println(entrada.readLine());
				
			}catch(IOException e){
				System.out.println("Algum problema ocorreu ao criar ou enviar dados pelo socket.");
			}catch(Exception e){
				System.out.println("Erro geral");
				System.exit(1);
			}
		}
		
		try{
			if(sSrv != null){
				sSrv.close();
			}
			s.close();
			System.exit(0);
		}catch(IOException e){
			e.printStackTrace();
		}
	
	}
}