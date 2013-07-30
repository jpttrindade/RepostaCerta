package classes_basicas;

public class Afirmacao {

	private long _id;
	private int nivel;
	private String src, resposta;
	
	public static String[] colunas = new String[] { Afirmacao._ID, Afirmacao.SRC, Afirmacao.RESPOSTA, Afirmacao.NIVEL };

	public static final String _ID = "_id";
	public static final String SRC = "src";
	public static final String RESPOSTA = "resposta";
	public static final String NIVEL = "nivel";
	
	public Afirmacao(String src, String resposta, int nivel){
		this.src = src;
		this.resposta = resposta;
		this.nivel = nivel;
	}
	public Afirmacao(long _id, String src, String resposta, int nivel){
		this._id = _id;
		this.src = src;
		this.resposta = resposta;
		this.nivel = nivel;
	}


	public long get_id() {
		return _id;
	}

	public int getNivel() {
		return nivel;
	}

	public String getSrc() {
		return src;
	}

	public String getResposta() {
		return resposta;
	}
	
	

	
	
}
