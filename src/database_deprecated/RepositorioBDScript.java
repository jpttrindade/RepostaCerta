package database_deprecated;

import java.util.ArrayList;
import java.util.List;

import classes_basicas.Afirmacao;
import database.SQLiteHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RepositorioBDScript{ // extends RepositorioBD {



	public static final String NOME_TABELA = "afirmacao";

	private static final String CATEGORIA = "BANCO_DADOS";

	// Script para fazer drop na tabela
	private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS afirmacao";

	// Cria a tabela com o "_id" sequencial
	private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
		"create table afirmacao ( _id integer primary key autoincrement, src text not null, resposta text not null, nivel integer not null);",
		"insert into afirmacao(src,resposta,nivel) values('1+1=2','T',1);",
		"insert into afirmacao(src,resposta,nivel) values('1+2=3','T',1);",
		"insert into afirmacao(src,resposta,nivel) values('1+4=6','F',1);" };


	// Controle de versão
	private static final int VERSAO_BANCO = 1;

	
	private static final String NOME_BANCO = "askme.db";
	protected SQLiteDatabase db;

	// Classe utilitária para abrir, criar, e atualizar o banco de dados
	private SQLiteHelper dbHelper;

	public RepositorioBDScript(Context ctx) {
		// Criar utilizando um script SQL
		Log.i("BANCO", "antes de inicializar o helper");
		dbHelper = new SQLiteHelper(ctx, RepositorioBDScript.NOME_BANCO, RepositorioBDScript.VERSAO_BANCO,
				RepositorioBDScript.SCRIPT_DATABASE_CREATE, RepositorioBDScript.SCRIPT_DATABASE_DELETE);

		// abre o banco no modo escrita para poder alterar também
		db = dbHelper.getWritableDatabase();
	}
	
	
	// Salva a afirmacao, insere uma nova ou atualiza
		public long salvar(Afirmacao af) {
			long _id = af.get_id();

			if (_id != 0) {
				atualizar(af);
			} else {
				// Insere novo
				_id = inserir(af);
			}

			return _id;
		}

		// Insere uma nova afirmacao
		public long inserir(Afirmacao af) {
			ContentValues values = new ContentValues();
			values.put(Afirmacao.SRC, af.getSrc());
			values.put(Afirmacao.RESPOSTA, af.getResposta());
			values.put(Afirmacao.NIVEL, af.getNivel());

			long id = inserir(values);
			return id;
		}

		// Insere uma nova afirmacao
		public long inserir(ContentValues valores) {
			long id = db.insert(NOME_TABELA, "", valores);
			return id;
		}

		// Atualiza uma afirmacao no banco. O id da afirmacao é utilizado.
		public int atualizar(Afirmacao af) {
			ContentValues values = new ContentValues();
			values.put(Afirmacao._ID, af.get_id());
			values.put(Afirmacao.SRC, af.getSrc());
			values.put(Afirmacao.RESPOSTA, af.getResposta());
			values.put(Afirmacao.NIVEL, af.getNivel());

			String _id = String.valueOf(af.get_id());

			String where = Afirmacao._ID + "=?";
			String[] whereArgs = new String[] { _id };

			int count = atualizar(values, where, whereArgs);

			return count;
		}

		// Atualiza a afirmacao com os valores abaixo
		// A cláusula where é utilizada para identificar a afirmacao a ser atualizada
		public int atualizar(ContentValues valores, String where, String[] whereArgs) {
			int count = db.update(NOME_TABELA, valores, where, whereArgs);
			Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
			return count;
		}

		// Deleta a afirmacao com o id fornecido
		public int deletar(long _id) {
			String where = Afirmacao._ID + "=?";

			String _id1 = String.valueOf(_id);
			String[] whereArgs = new String[] { _id1 };

			int count = deletar(where, whereArgs);

			return count;
		}

		// Deleta a afirmacao com os argumentos fornecidos
		public int deletar(String where, String[] whereArgs) {
			int count = db.delete(NOME_TABELA, where, whereArgs);
			Log.i(CATEGORIA, "Deletou [" + count + "] registros");
			return count;
		}

		// Busca a afirmacao pelo id
		public Afirmacao buscarAfirmacao(long _id) {
			// select * from carro where _id=?
			Cursor c = db.query(true, NOME_TABELA, Afirmacao.colunas, Afirmacao._ID + "=" + _id, null, null, null, null, null);

			if (c.getCount() > 0) {

				// Posicinoa no primeiro elemento do cursor
				c.moveToFirst();

				Afirmacao af = new Afirmacao(c.getLong(0), c.getString(1), c.getString(2),c.getInt(3));

				//				// Lê os dados
				//				carro.id = c.getLong(0);
				//				carro.nome = c.getString(1);
				//				carro.placa = c.getString(2);
				//				carro.ano = c.getInt(3);

				return af;
			}

			return null;
		}

		// Retorna um cursor com todas as afirmacoes
		public Cursor getCursor() {
			try {
				// select * from carros
				return db.query(NOME_TABELA, Afirmacao.colunas, null, null, null, null, null, null);
			} catch (SQLException e) {
				Log.e(CATEGORIA, "Erro ao buscar os carros: " + e.toString());
				return null;
			}
		}

		// Retorna uma lista com todos as afirmacoes
		public List<Afirmacao> listarAfirmacoes() {
			Cursor c = getCursor();

			List<Afirmacao> afs = new ArrayList<Afirmacao>();

			if (c.moveToFirst()) {

				// Recupera os índices das colunas
				int idxId = c.getColumnIndex(Afirmacao._ID);
				int idxSrc = c.getColumnIndex(Afirmacao.SRC);
				int idxResposta = c.getColumnIndex(Afirmacao.RESPOSTA);
				int idxNivel = c.getColumnIndex(Afirmacao.NIVEL);

				// Loop até o final
				do {
					Afirmacao af = new Afirmacao(c.getLong(idxId), c.getString(idxSrc), c.getString(idxResposta), c.getInt(idxNivel));

					afs.add(af);


				} while (c.moveToNext());
			}

			return afs;
		}

		// Busca a afirmacao pelo nivel "select * from afirmacao where nivel=?"
		public List<Afirmacao> buscarAfirmacaoPorNivel(int nivel) {
			List<Afirmacao> afs = new ArrayList<Afirmacao>();

			try {
				// Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
				Cursor c = db.query(NOME_TABELA, Afirmacao.colunas, Afirmacao.NIVEL + "=" + nivel, null, null, null, null);
				// Se encontrou...
				if (c.moveToFirst()) {
					// Loop até o final
					Afirmacao af = null;
					do {
						af = new Afirmacao(c.getLong(0), c.getString(1), c.getString(2),c.getInt(3));
						afs.add(af);
					} while (c.moveToNext());
				}

			} catch (SQLException e) {
				Log.e(CATEGORIA, "Erro ao buscar o Afirmacao pelo nivel: " + e.toString());
				return null;
			}
			return afs;
		}



		public void fechar() {
			if(db!=null){
				db.close();
			}
			
			if (dbHelper != null) {
				dbHelper.close();
			}
		}
	
}
