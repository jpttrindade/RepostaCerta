package menu;

import java.util.ArrayList;
import java.util.List;
import br.org.saber.repostacertaapp.R;
import classes_basicas.Nivel;
import classes_basicas.OnItemClickedCallBack;
import database.RepositorioNiveis;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Menu_Principal_Fragment extends Fragment implements OnItemClickListener{

	OnItemClickedCallBack callBack;

	private ArrayList<ItemListView> itens;
	private ListView listView;
	private AdapterListView adapterListView;
	List<Nivel> listaNiveis;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menu_principal, container, false);
		createListView(view);
		/** Retorna a view para ser exibida */
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			callBack = (OnItemClickedCallBack) activity;
		} catch (ClassCastException e) {
			Log.e("MenuFragment", activity.toString() + " must implement OnItemClickedCallBack");
		}
	}

	private void createListView(View view) {
		itens = new ArrayList<ItemListView>();
		RepositorioNiveis rep_niveis = new RepositorioNiveis(this.getActivity());
		listaNiveis = rep_niveis.listarNiveis();

		//Pega a referencia do ListView
		listView = (ListView) view.findViewById(R.id.listView1);

		if(listaNiveis.size() > 0){
			ItemListView item;
			for(int i=0; i<listaNiveis.size(); i++){
				item = new ItemListView(listaNiveis.get(i).getIntNivel());
				itens.add(item);
			}
		}
		rep_niveis.fechar();

		adapterListView = new AdapterListView(this, itens);
		listView.setAdapter(adapterListView);
		listView.setOnItemClickListener(this);
	}

    public ListView getList(){
        return listView;
    }

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		callBack.onItemClicked(listaNiveis.get(position));
	}
}