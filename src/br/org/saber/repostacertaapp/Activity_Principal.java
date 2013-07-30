package br.org.saber.repostacertaapp;



import conteudo.ContentFragment;
import menu.Menu_Principal_Fragment;
import classes_basicas.Nivel;
import classes_basicas.OnItemClickedCallBack;
import database.RepositorioScripts;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;


public class Activity_Principal extends FragmentActivity implements OnItemClickedCallBack{


	private RepositorioScripts rep_script;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_principal);
		
		rep_script = new RepositorioScripts(this);
		
		Menu_Principal_Fragment menuFrag = new Menu_Principal_Fragment();
		
		if(findViewById(R.id.main_smart) != null ){
			getSupportFragmentManager().beginTransaction().add(R.id.main_smart, menuFrag,"menu_principal").commit();
		} else if(findViewById(R.id.content) != null){
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.main_tab, menuFrag, "menu_principal");
			transaction.add(R.id.content, new ContentFragment(), "conteudo");	
			transaction.commit();
		}
	}
	
	@Override
	public void onItemClicked(Nivel nivel) {
		
		ContentFragment newFragment = new ContentFragment();
		
		Bundle args = new Bundle();
		args.putSerializable("nivel", nivel);
		newFragment.setArguments(args);
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		if(findViewById(R.id.main_smart) != null){
			transaction.replace(R.id.main_smart, newFragment);
			transaction.addToBackStack(null);
		} else if(findViewById(R.id.content) != null){
			transaction.replace(R.id.content, newFragment); 	
		}
		transaction.commit();	
	}


    public void finalizar_programa(){
        setContentView(R.layout.act_principal_fim);

    }
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.menu__principal, menu);
//		return true;
//	}
//

}
