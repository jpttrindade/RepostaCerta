package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RepositorioScripts extends RepositorioGenerico {

	private static final String NOME_BANCO = "respostacerta";
	private static final String SCRIPT_DELETE_TABELA= "DROP TABLE IF EXISTS afirmacao";
	
	private static final String[] SCRIPT_CRIAR_TABELA = new String[] {
		
		"create table afirmacao" +
			"(_id integer primary key autoincrement," +
			"src text not null," +
			"resposta text not null," +
			"nivel integer not null);",
					
		"create table niveis" +
			"(nivel integer primary key," +
			"pt_necessaria integer not null," +
			"pt_atingida integer not null);",
	
		
		
//		"foreign key (nivel) references niveis (nivel));" +
            "insert into niveis(nivel, pt_necessaria, pt_atingida) values(1,0,0);",
            "insert into niveis(nivel, pt_necessaria, pt_atingida) values(2,30,0);",
//            "insert into niveis(nivel, pt_necessaria, pt_atingida) values(3,80,0);",
//            "insert into niveis(nivel, pt_necessaria, pt_atingida) values(4,130,0);",
	        "insert into afirmacao (src,resposta, nivel) values('Se uma força atuar sobre um corpo em movimento, ela altera a sua velocidade!', 'F', 1);",
        	"insert into afirmacao (src,resposta, nivel) values('Se a força for no mesmo sentido do movimento, sua velocidade diminuirá!', 'F', 1);",
        	"insert into afirmacao (src,resposta, nivel) values('Se a força for contrária ao movimento, a velocidade do corpo aumentará!', 'F', 1);",
        	"insert into afirmacao (src,resposta, nivel) values('Aquilo que provoca a mudança na velocidade de um corpo é chamado de força!', 'T', 1);",
        	"insert into afirmacao (src,resposta, nivel) values('As corpos são \"teimosos\" para modificarem seus estados de repouso ou movimento.', 'T', 1);",
        	"insert into afirmacao(src,resposta, nivel) values(' Liderança é a capacidade de justificar legalmente o exercício do poder','F', 2);",
           	"insert into afirmacao(src,resposta, nivel) values('Liderança é o processo de dirigir o comportamento das pessoas para o alcance de objetivos.', 'T', 2);",
	    	"insert into afirmacao(src,resposta, nivel) values('Liderança é uma influência interpessoal exercida em uma dada situação e dirigida através do processo de comunicação humana para a consecução de um ou mais objetivos específicos.', 'T', 2);",
		    "insert into afirmacao(src,resposta, nivel) values(' Liderança é uma função das necessidades existentes em uma determinada situação e consiste em uma relação entre um individuo e um grupo.', 'T', 2);",
		    "insert into afirmacao(src,resposta, nivel) values(' Liderança é uma característica exclusiva das funções de chefia e decorre da estrutura formal da organização.', 'F', 2);"



//		"insert into (src,resposta,nivel) values()" +
//		"insert into (src,resposta,nivel) values()" +
//		"insert into (src,resposta,nivel) values()" +
//		"insert into (src,resposta,nivel) values()" +
//		"insert into (src,resposta,nivel) values()"
//		"insert into () values()" +
//		"insert into () values()" +
//		"insert into () values()" +
//		"insert into () values()" +
//		"insert into () values()" +
//		"insert into () values()" +
//		"insert into () values()" +
//		"insert into () values()" +
//		"insert into () values()" +
//		"insert into () values()" +
		
		
		
		};
	
	private static final int VERSAO_BANCO = 1;

	private SQLiteHelper dbHelper;
	
	public RepositorioScripts(Context ctx) {
		super(ctx);
		dbHelper = new SQLiteHelper(ctx, NOME_BANCO, VERSAO_BANCO, SCRIPT_CRIAR_TABELA, SCRIPT_DELETE_TABELA);
		Log.i("BANCO", "dbHelper de boooa");
		db = dbHelper.getWritableDatabase();
		Log.i("BANCO","getwritable funfou");
		
	}
	@Override
	public void fechar(){
		if(dbHelper != null){
			dbHelper.close();
		}
	}

}

	
	
