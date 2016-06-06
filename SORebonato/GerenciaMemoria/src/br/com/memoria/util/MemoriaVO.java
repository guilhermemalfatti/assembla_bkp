//Alex Spenassato 78237 e Guilherme Malfatti 104484
package br.com.memoria.util;

public class MemoriaVO {
	private Integer pId;
	private Integer entradaSistema;
	private Integer tempoCpu;
	private Integer espaco;
	private Boolean alocado;
	private Integer tempoSaidaCPU;
	
	public Integer getTempoSaidaCPU() {
		return tempoSaidaCPU;
	}
	public void setTempoSaidaCPU(Integer tempoSaidaCPU) {
		this.tempoSaidaCPU = tempoSaidaCPU;
	}
	public MemoriaVO() {
		this.alocado = false;
		this.tempoCpu = 0;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public Integer getEntradaSistema() {
		return entradaSistema;
	}
	public void setEntradaSistema(Integer entradaSistema) {
		this.entradaSistema = entradaSistema;
	}
	public Integer getTempoCpu() {
		return tempoCpu;
	}
	public void setTempoCpu(Integer tempoCpu) {
		this.tempoCpu = tempoCpu;
	}
	public Integer getEspaco() {
		return espaco;
	}
	public void setEspaco(Integer espaco) {
		this.espaco = espaco;
	}
	public void setAlocado(Boolean alocado) {
		this.alocado = alocado;
	}
	public Boolean getAlocado() {
		return alocado;
	}	
}
