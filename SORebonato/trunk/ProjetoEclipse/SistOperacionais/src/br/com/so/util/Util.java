//Alex Spenassato 78237 e Guilherme Malfatti 104484
package br.com.so.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {
    //Método para leitura do arquivo
    public List<ValoresVO> leArquivo(String caminho) throws Exception{
        List<ValoresVO> listaValores = new ArrayList<ValoresVO>();
        try {
            //Passa o caminho do arquivo por parametro
            BufferedReader in = new BufferedReader(new FileReader(caminho));
            String linha;
            while((linha = in.readLine()) != null){
            	//Quando chegar na ultima linha interrompe o laço
            	if(linha.equals("0")){
            		break;
            	}
                ValoresVO valoresVO = new ValoresVO();
                String[] valores = linha.split("[\\t]");

                //Seta cada atributo no objeto
                valoresVO.setpId(Integer.parseInt(valores[0]));
                valoresVO.setEntrSist(Integer.parseInt(valores[1]));
                valoresVO.setTempCpu(Integer.parseInt(valores[2]));
                valoresVO.setPrior(Float.parseFloat(valores[3]));
                valoresVO.setFila(valores[4]);

                //Adiciona o objeto na lista (cada objeto é uma linha do arquivo lido)
                listaValores.add(valoresVO);
            }
            in.close();
        } 
        catch (IOException e){
            throw e;
        }
        return listaValores;
    }
}
