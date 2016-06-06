//Alex Spenassato 78237 e Guilherme Malfatti 104484
package br.com.so.util;

public class ValoresVO implements Comparable<ValoresVO> {

	private Integer pId;
	private Integer entrSist;
	private Integer tempCpu;
	private Float prior;
	private String fila;
	private boolean escalonado;
	//Usado só no Loteria
	private int bilhete;
	private Integer tempoEntrada;
	private Integer usoCpu;
	
	@Override
	public int compareTo(ValoresVO o) {
		return prior.compareTo(o.getPrior());
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public Integer getEntrSist() {
		return entrSist;
	}

	public void setEntrSist(Integer entrSist) {
		this.entrSist = entrSist;
	}

	public Integer getTempCpu() {
		return tempCpu;
	}

	public void setTempCpu(Integer tempCpu) {
		this.tempCpu = tempCpu;
	}

	public Float getPrior() {
		return prior;
	}

	public void setPrior(Float prior) {
		this.prior = prior;
	}

	public String getFila() {
		return fila;
	}

	public void setFila(String fila) {
		this.fila = fila;
	}

	public boolean getEscalonado() {
		return escalonado;
	}

	public void setEscalonado(boolean escalonado) {
		this.escalonado = escalonado;
	}

	public int getBilhete() {
		return bilhete;
	}

	public void setBilhete(int bilhete) {
		this.bilhete = bilhete;
	}

	public Integer getTempoEntrada() {
		return tempoEntrada;
	}

	public void setTempoEntrada(Integer tempoEntrada) {
		this.tempoEntrada = tempoEntrada;
	}

	public Integer getUsoCpu() {
		return usoCpu;
	}

	public void setUsoCpu(Integer usoCpu) {
		this.usoCpu = usoCpu;
	}
}
