//Alex Spenassato 78237 e Guilherme Malfatti 104484
package br.com.memoria.util;

public class Memoria {
	private Integer particao;
	private Boolean ocupado = false;
	private Integer Pid = 0;
	
	public Integer getPid() {
		return Pid;
	}
	public void setPid(Integer pid) {
		Pid = pid;
	}
	public Integer getParticao() {
		return particao;
	}
	public void setParticao(Integer particao) {
		this.particao = particao;
	}
	public Boolean getOcupado() {
		return ocupado;
	}
	public void setOcupado(Boolean ocupado) {
		this.ocupado = ocupado;
	}
}
