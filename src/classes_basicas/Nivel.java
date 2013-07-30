package classes_basicas;

import java.io.Serializable;


public class Nivel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 773505133520336617L;
	public static final String NIVEL = "nivel";
	public static final String PT_NECESSARIA = "pt_necessaria";
	public static final String PT_ATINGIDA = "pt_atingida";
	
	public static final String[] colunas = new String[]{NIVEL, PT_NECESSARIA, PT_ATINGIDA};
	
	
	private int nivel;
	private int pt_necessaria;
	private int pt_atingida;
	
	
	public Nivel (int nivel, int pt_necessaria){
		this.nivel = nivel;
		this.pt_necessaria = pt_necessaria;
		this.pt_atingida = 0;
	}
	
	public Nivel (int nivel, int pt_necessaria, int pt_atingida){
		this.nivel = nivel;
		this.pt_necessaria = pt_necessaria;
		this.pt_atingida = pt_atingida;
	}

	public int getIntNivel() {
		return nivel;
	}

	public int getPt_necessaria() {
		return pt_necessaria;
	}

	public int getPt_atingida() {
		return pt_atingida;
	}
	
	
}
