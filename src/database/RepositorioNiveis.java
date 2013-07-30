package database;

import java.util.ArrayList;
import java.util.List;

import classes_basicas.Afirmacao;
import classes_basicas.Nivel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class RepositorioNiveis extends RepositorioGenerico {

	public static final String TABELA = "niveis";



	public RepositorioNiveis(Context ctx) {
		super(ctx);
	}


	public Cursor getCursor(){
		try{
			super.abrir();
			return super.db.query(TABELA, Nivel.colunas, null, null, null, null, null);

		}catch(SQLException e){
			Log.e("BANCO", e.getMessage());
			return null;
		}
	}


	public List<Nivel> listarNiveis() {
		Cursor c = getCursor();

		List<Nivel> niveis = new ArrayList<Nivel>();
		
		if (c.moveToFirst()) {

			// Recupera os índices das colunas
			int idxNivel = c.getColumnIndex(Nivel.NIVEL);
			int idxPtNec = c.getColumnIndex(Nivel.PT_NECESSARIA);
			int idxPtAti = c.getColumnIndex(Nivel.PT_ATINGIDA);

			// Loop até o final
			do {
				Nivel ni = new Nivel(c.getInt(idxNivel), c.getInt(idxPtNec), c.getInt(idxPtAti));

				niveis.add(ni);


			} while (c.moveToNext());
		}

		c.close();
		return niveis;



	}


	public Nivel buscarNivel(int nivel) {
		Nivel n = null;
		try {
			super.abrir();
			// Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
			Cursor c = super.db.query(TABELA, Nivel.colunas, Nivel.NIVEL + "=" + nivel, null, null, null, null);
			// Se encontrou...
			if (c.moveToFirst()) {
				// Loop até o final
				n =  new Nivel(c.getInt(0),c.getInt(1), c.getInt(2));

			}

			c.close();
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar o nivel: " + e.toString());
		}
		super.fechar();
		return n;
	}

	// Insere um novo nivel
	public long inserir(Nivel n) {
		ContentValues values = new ContentValues();
		values.put(Nivel.NIVEL, n.getIntNivel());
		values.put(Nivel.PT_ATINGIDA, n.getPt_atingida());
		values.put(Nivel.PT_NECESSARIA, n.getPt_necessaria());

		long id = inserir(values);
		return id;
	}

	// Insere uma novo nivel
	private long inserir(ContentValues valores) {
		super.abrir();
		long id = super.db.insert(TABELA, "", valores);
		super.fechar();
		return id;
	}

	// Atualiza um nivel no banco.
	public int atualizar(Nivel n) {
		ContentValues values = new ContentValues();
		values.put(Nivel.NIVEL, n.getIntNivel());
		values.put(Nivel.PT_ATINGIDA, n.getPt_atingida());
		values.put(Nivel.PT_NECESSARIA, n.getPt_necessaria());

		String nivel = String.valueOf(n.getIntNivel());

		String where = Nivel.NIVEL + "=?";
		String[] whereArgs = new String[] {nivel};

		int count = atualizar(values, where, whereArgs);

		return count;
	}

	// Atualiza o nivel com os valores abaixo
	// A cláusula where é utilizada para identificar o nivel a ser atualizado
	private int atualizar(ContentValues valores, String where, String[] whereArgs) {
		super.abrir();
		int count = super.db.update(TABELA, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		super.fechar();
		return count;
	}

	// Deleta  nivel com o nivel fornecido
	public int deletar(int nivel) {
		String where = Nivel.NIVEL + "=?";

		String nivel1 = String.valueOf(nivel);
		String[] whereArgs = new String[] { nivel1 };

		int count = deletar(where, whereArgs);

		return count;
	}

	// Deleta o nivel com os argumentos fornecidos
	private int deletar(String where, String[] whereArgs) {
		super.abrir();
		int count = super.db.delete(TABELA, where, whereArgs);
		Log.i(CATEGORIA, "Deletou [" + count + "] registros");
		super.fechar();
		return count;
	}




}
