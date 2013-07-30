package menu;

import java.util.ArrayList;

import br.org.saber.repostacertaapp.R;
import br.org.saber.repostacertaapp.R.id;
import br.org.saber.repostacertaapp.R.layout;

import android.content.Context;
import android.net.MailTo;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterListView extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<ItemListView> itens;

    public AdapterListView(Fragment ctx, ArrayList<ItemListView> itens) {
        this.itens = itens;
        
        Log.i("BANCO",	"Vai Inflar agoraaa");
        mInflater = (LayoutInflater) LayoutInflater.from(ctx.getActivity());
        
    }
  
    public int getCount() {
        return itens.size();
    }


    public ItemListView getItem(int position) {
        return itens.get(position);
    }

  
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
       //Pega o item de acordo com a posção.
       ItemListView item = getItem(position);
       //infla o layout para podermos preencher os dados
       view = mInflater.inflate(R.layout.nivel,null);
//
//        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
//        //ao item e definimos as informações.
//        
        TextView tv = ((TextView)(view.findViewById(R.id.nivel)));
//        Log.i("AdaterListView: getView()", "TextView: "+tv);
        if(tv != null){
        	tv.setText("Nivel: "+String.valueOf(item.getNivel()));
        }

        return view;
    }
}
