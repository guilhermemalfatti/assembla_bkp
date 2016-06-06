package br.com.upf.main;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFileChooser;

public class Compiladores {

	private static JFileChooser filechooser = new JFileChooser();

	public static void main(String[] args) throws Exception {

		int returnValue = filechooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			List<String> lidos = leArquivo(filechooser.getSelectedFile()
					.getPath());
			int i = 0;
			System.out.println("Tabela de simbolos:");

			for (String s : lidos) {

				for (int ii = 0; ii < s.length(); ii++) {
					if (s.charAt(ii) + "" == "а" || s.charAt(ii) + "" == "в"
							|| s.charAt(ii) + "" == "к"
							|| s.charAt(ii) + "" == "ф"
							|| s.charAt(ii) + "" == "г"
							|| s.charAt(ii) + "" == "х"
							|| s.charAt(ii) + "" == "б"
							|| s.charAt(ii) + "" == "й"
							|| s.charAt(ii) + "" == "н"
							|| s.charAt(ii) + "" == "у"
							|| s.charAt(ii) + "" == "ъ"
							|| s.charAt(ii) + "" == "з"
							|| s.charAt(ii) + "" == "ь"
							|| s.charAt(ii) + "" == "В"
							|| s.charAt(ii) + "" == "К"
							|| s.charAt(ii) + "" == "Ф"
							|| s.charAt(ii) + "" == "Ы"
							|| s.charAt(ii) + "" == "Г"
							|| s.charAt(ii) + "" == "Х"
							|| s.charAt(ii) + "" == "Б"
							|| s.charAt(ii) + "" == "Й"
							|| s.charAt(ii) + "" == "Н"
							|| s.charAt(ii) + "" == "У"
							|| s.charAt(ii) + "" == "Ъ"
							|| s.charAt(ii) + "" == "З"
							|| s.charAt(ii) + "" == "Ь") {

						System.out.println(" - Foi encontrado um erro nao й possivel ter palavras acentuadas ou caracteres especiais- ");
						System.exit(1);
					}
					ii++;
				}

			}
			for (String item : lidos) {
				if (!item.equals("")) {
					System.out.println("<" + i + "," + item + ">");
					i++;

				}

			}

		}

	}

	public static List<String> leArquivo(String caminho) throws Exception {
		String[] reserv = { "int", "include", "auto", "break", "case", "cout",
				"endl", "namespace", "include ", "char", "using", "main",
				"iostream", "printf", "const", "continue", "default", "do",
				"double", "else", "enum", "extern", "float", "for", "goto",
				"if", "int", "long", "register", "return", "short", "signed",
				"sizeof", "static", "struct", "switch", "typedef", "union",
				"unsigned", "void", "volatile", "while" };
		String[] operadores = { "=", "+", "-", "(", ")", "*", "/", ";" };
		String[] valores = null;

		java.util.List<String> tabelaSimbolos = new ArrayList();
		try {
			// Passa o caminho do arquivo por parametro
			FileReader file = new FileReader(caminho);
			Scanner in = new Scanner(file);

			String linha;
			boolean erro = false;
			boolean erro2 = false;
			while (in.hasNextLine()) {
				linha = in.nextLine();

				// String[] carac = {";","{","}",">"};

				Boolean erroLinha = false;
				if (!linha.trim().endsWith(";")) {
					erroLinha = true;
					if (linha.trim().endsWith(")")) {
						erroLinha = false;
					} else if (linha.trim().endsWith("{")) {
						erroLinha = false;
					} else if (linha.trim().endsWith("}")) {
						erroLinha = false;
					} else if (linha.trim().endsWith(">")) {
						erroLinha = false;
					}

				}
				
				if(erroLinha){
					System.out.println("Erro esta faltando no final da linha ; ou > ou ) ou { ou } ");
					System.exit(1);
				}
				valores = linha.split("[^a-zA-Z]");

				for (String item : valores) {
					if (item.equals("main")) {
						erro = true;
					}
					if (item.equals("iostream")) {
						erro2 = true;
					}

					tabelaSimbolos.add(item);
				}

			}
			if (!erro) {
				System.out.println("Erro encontrado - falta a funзгo principal (main).");
				System.exit(1);
			}
			if (!erro2) {
				System.out.println("Erro encontrado - falta incluir a biblioteca iostream.");
				System.exit(1);
			}

			for (String item : reserv) {// fico bom
				tabelaSimbolos.remove(item);

			}

			in.close();
		} catch (IOException e) {

			throw e;
		}
		return tabelaSimbolos;
	}
}
