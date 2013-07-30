package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class RepositorioGenerico {
	
	
	
	protected SQLiteDatabase db;
	
	protected static final String CATEGORIA = "BANCO";

	protected static final String NOME_BANCO = "respostacerta";
	
	protected Context ctx;
	
	public RepositorioGenerico(Context ctx){
		this.ctx = ctx;
	}
	
	public void abrir(){
		if(db == null){
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
		} else if (!db.isOpen()){
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
		}
	}
	
	public void fechar(){
		if(db!=null){
			db.close();
		}
	}
	
	public boolean isOpen(){
		return db.isOpen();
	}

}
