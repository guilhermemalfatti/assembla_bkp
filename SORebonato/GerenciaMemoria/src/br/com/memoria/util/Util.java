//Alex Spenassato 78237 e Guilherme Malfatti 104484
package br.com.memoria.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {
    //Método para leitura do arquivo
    public List<MemoriaVO> leArquivo(String caminho) throws Exception{
        List<MemoriaVO> listaValores = new ArrayList<MemoriaVO>();
        try {
            //Passa o caminho do arquivo por parametro
            BufferedReader in = new BufferedReader(new FileReader(caminho));
            String linha;
            while((linha = in.readLine()) != null){
            	//Quando chegar na ultima linha interrompe o laço
            	if(linha.equals("0")){
            		break;
            	}
            	MemoriaVO memoriaVO = new MemoriaVO();
                String[] valores = linha.split("[\\t]");

                //Seta cada atributo no objeto
                memoriaVO.setpId( Integer.parseInt(valores[0]) );
                memoriaVO.setEntradaSistema( Integer.parseInt(valores[1]) );
                memoriaVO.setTempoCpu( Integer.parseInt(valores[2]) );
                memoriaVO.setEspaco( Integer.parseInt(valores[3]) );

                //Adiciona o objeto na lista (cada objeto é uma linha do arquivo lido)
                listaValores.add(memoriaVO);
            }
            in.close();
        } 
        catch (IOException e){
            throw e;
        }
        return listaValores;
    }
}
