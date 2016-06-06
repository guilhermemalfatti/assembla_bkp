package br.com.upf.qualidadetestesoft;

/**
 * Classe para validação de CPF e a obtenção dos digítos verificadores.
 *
 * @author $efcjunior$
 * @version $Revision: 1.1 $
 */
public class CPFUtil
{
   private static final int TAM_CPF = 11;
   private Integer primeiroDigito;
   private Integer segundoDigito;
   private String algarismoCpf;
   private String cpf;
   private String digitosVerificadores;
   private boolean valido;

   /**
    * Cria uma nova instância do tipo CPF.
    *
    * @param cpf argumento recebido.
    */
   public CPFUtil(String cpf)
   {
      this.cpf = cpf;
      this.algarismoCpf = isCPFComDigito() ? obtemAlgarismoComDigito() : obtemAlgarismoSemDigito();
      this.primeiroDigito = calculaPrimeiroDigitoVerificador();
      this.segundoDigito = calculaSegundoDigitoVerificador();
      this.digitosVerificadores = concatenaDigitos();
      this.valido = isCPFComDigito() ? validaCPF() : false;
   }

   /**
    * Recupera somente os 9 primeiros algarismo.
    *
    * @return somente os 9 primeiros algarismo.
    */
   public String getAlgarismoCpf()
   {
      return algarismoCpf;
   }

   /**
    * Recupera CPF conforme digitado.
    *
    * @return CPF conforme digitado.
    */
   public String getCpf()
   {
      return cpf;
   }

   /**
    * Recupera os dígitos verificadores.
    *
    * @return dígitos verificadores.
    */
   public String getDigitosVerificadores()
   {
      return digitosVerificadores;
   }

   /**
    * Recupera primeiro dígito verificador.
    *
    * @return primeiro dígito verificador.
    */
   public Integer getPrimeiroDigito()
   {
      return primeiroDigito;
   }

   /**
    * Recupera segundo dígito verificador.
    *
    * @return segundo dígito verificador.
    */
   public Integer getSegundoDigito()
   {
      return segundoDigito;
   }

   /**
    * Verifica se o CPF digitado é valido.
    *
    * @return CPF digitado é valido.
    */
   public boolean isValido()
   {
      return valido;
   }

   /**
    * Calcula o primeiro dígito.
    *
    * @return primeiro dígito.
    */
   private Integer calculaPrimeiroDigitoVerificador()
   {
      String aux1 = algarismoCpf;
      String aux2 = "";
      Integer soma = new Integer(0);

      for (int i = 10; i >= 2; i--)
      {
         aux2 = aux1;
         soma += ((Integer.parseInt(aux2.substring(0, 1))) * i);
         aux1 = aux2.substring(1);
      }

      return resultaDigitoVerificador(soma);
   }

   /**
    * Calcula o segundo dígito.
    *
    * @return o segundo dígito.
    */
   private Integer calculaSegundoDigitoVerificador()
   {
      String aux1 = algarismoCpf + primeiroDigito;
      String aux2 = "";
      Integer soma = new Integer(0);

      for (int i = 11; i >= 2; i--)
      {
         aux2 = aux1;
         soma += ((Integer.parseInt(aux2.substring(0, 1))) * i);
         aux1 = aux2.substring(1);
      }

      return resultaDigitoVerificador(soma);
   }

   /**
    * Concatena o primeiro e o segundo dígito encontrado.
    *
    * @return dígitos concatenados.
    */
   private String concatenaDigitos()
   {
      return primeiroDigito + "" + segundoDigito;
   }

   /**
    * Verifica se o CPF é com dígito.
    *
    * @return verdadeiro ou falso.
    */
   private boolean isCPFComDigito()
   {
      if (cpf.contains("."))
      {
         if (cpf.contains("-"))
         {
            return true;
         }
         else
         {
            return false;
         }
      }
      else
      {
         if (cpf.length() < TAM_CPF)
         {
            return false;
         }
         else
         {
            return true;
         }
      }
   }

   /**
    * Obtem os algarismo com dígito.
    *
    * @return algarismo com dígito.
    */
   private String obtemAlgarismoComDigito()
   {
      if (cpf.length() > TAM_CPF)
      {
         return cpf.substring(0, 3) + cpf.substring(4, 7) + cpf.substring(8, 11);
      }
      else
      {
         return cpf.substring(0, 9);
      }
   }

   /**
    * Obtém os algarismo sem dígito.
    *
    * @return algarismo sem dígito.
    */
   private String obtemAlgarismoSemDigito()
   {
      if (cpf.length() < TAM_CPF)
      {
         return cpf;
      }
      else
      {
         return cpf.substring(0, 3) + cpf.substring(4, 7) + cpf.substring(8, 11);
      }
   }

   /**
    * Obtem o dígito verificador calculado.
    *
    * @param soma
    *
    * @return verificador calculado.
    */
   private Integer resultaDigitoVerificador(Integer soma)
   {
      Integer resto = soma % 11;

      if (resto < 2)
      {
         return 0;
      }
      else
      {
         return 11 - resto;
      }
   }

   /**
    * Valida CPF.
    *
    * @return CPF validado.
    */
   private boolean validaCPF()
   {
      if (cpf.contains("."))
      {
         return cpf.substring(12).equals(digitosVerificadores);
      }
      else
      {
         return cpf.substring(9).equals(digitosVerificadores);
      }
   }
}

