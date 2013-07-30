package database;

import java.util.ArrayList;
import java.util.List;

import classes_basicas.Afirmacao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class RepositorioAfirmacao extends RepositorioGenerico {

	public static final String TABELA = "afirmacao";


	public RepositorioAfirmacao(Context ctx) {
		super(ctx);
	}

	public Cursor getCursor(){
		try{
			super.abrir();
			return super.db.query(TABELA, Afirmacao.colunas, null, null, null, null, null);

		}catch(SQLException e){
			return null;
		}
	}

	// Busca a afirmacao pelo nivel "select * from afirmacao where nivel=?"
	public List<Afirmacao> buscarAfirmacaoPorNivel(int nivel) {
		List<Afirmacao> afs = new ArrayList<Afirmacao>();

		try {
			super.abrir();
			// Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
			Cursor c = super.db.query(TABELA, Afirmacao.colunas, Afirmacao.NIVEL + "=" + nivel, null, null, null, null);
			// Se encontrou...
			if (c.moveToFirst()) {
				// Loop até o final
				Afirmacao af = null;
				do {
					af = new Afirmacao(c.getLong(0),c.getString(1), c.getString(2),c.getInt(3));
					afs.add(af);
				} while (c.moveToNext());
			}

			c.close();
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar o Afirmacao pelo nivel: " + e.toString());

			return null;
		}
		super.fechar();
		return afs;
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
	private long inserir(ContentValues valores) {
		super.abrir();
		long id = super.db.insert(TABELA, "", valores);
		super.fechar();
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
				super.abrir();
				int count = super.db.update(TABELA, valores, where, whereArgs);
				Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
				super.fechar();
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
				super.abrir();
				int count = super.db.delete(TABELA, where, whereArgs);
				Log.i(CATEGORIA, "Deletou [" + count + "] registros");
				super.fechar();
				return count;
			}

}
