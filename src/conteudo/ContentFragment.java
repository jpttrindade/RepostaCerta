package conteudo;

import java.util.List;

import br.org.saber.repostacertaapp.Activity_Principal;
import br.org.saber.repostacertaapp.R;


import classes_basicas.Afirmacao;
import classes_basicas.Nivel;
import database.RepositorioAfirmacao;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import menu.Menu_Principal_Fragment;
import database.RepositorioNiveis;
import classes_basicas.OnItemClickedCallBack;
import android.app.Activity;

public class ContentFragment extends Fragment {

    OnItemClickedCallBack callBack;
	int nivelClicado;
	Nivel nivel;
	RepositorioAfirmacao rp_af;
	List<Afirmacao> lista_afirmacoes;
	int quest_atual;
	int total_erros;
    int total_niveis;
	ImageView[] icons;
	LayoutInflater inflater;
	private String textoAfirmacao = "Pergunta";
	private boolean respostaCorreta;
	View view;
	FragmentManager fm;
    RepositorioNiveis niveis;


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (OnItemClickedCallBack) activity;
        } catch (ClassCastException e) {
            Log.e("MenuFragment", activity.toString() + " must implement OnItemClickedCallBack");
        }
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(getNivel()){

			iniciaVariaveis(inflater, container, savedInstanceState);
			atualizar(lista_afirmacoes.get(quest_atual));

			ImageButton bt_verdade = (ImageButton) view.findViewById(R.id.verdade);
			bt_verdade.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					respostaSim();
				};
			});

			ImageButton bt_falso = (ImageButton) view.findViewById(R.id.falso);
			bt_falso.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					respostaNao();
				}
			});
		}
		return view;
	}

	private boolean getNivel() {
		if (getArguments() != null) {
			Bundle args = getArguments();
			nivel = (Nivel) args.getSerializable("nivel");
			return true;
		}	else {
			return false;
		}
	}


	private void iniciaVariaveis(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		this.view = this.inflater.inflate(R.layout.content, container, false);
		nivelClicado = 0;
		quest_atual = 0;
		total_erros = 0;

        niveis = new RepositorioNiveis(getActivity());
        total_niveis = niveis.listarNiveis().size();

        rp_af = new RepositorioAfirmacao(getActivity());
		lista_afirmacoes = rp_af.buscarAfirmacaoPorNivel(nivel.getIntNivel());

		fm = getFragmentManager();
		Fragment frag = fm.findFragmentByTag("menu_principal");
        ((TextView) view.findViewById(R.id.titulo_nivel)).setText("Nivel " + nivel.getIntNivel());
		icons = new ImageView[]{
                (ImageView)((Menu_Principal_Fragment)frag).getList().getChildAt(nivel.getIntNivel()-1).findViewById(R.id.bt_1),
                (ImageView)((Menu_Principal_Fragment)frag).getList().getChildAt(nivel.getIntNivel()-1).findViewById(R.id.bt_2),
                (ImageView)((Menu_Principal_Fragment)frag).getList().getChildAt(nivel.getIntNivel()-1).findViewById(R.id.bt_3),
                (ImageView)((Menu_Principal_Fragment)frag).getList().getChildAt(nivel.getIntNivel()-1).findViewById(R.id.bt_4),
                (ImageView)((Menu_Principal_Fragment)frag).getList().getChildAt(nivel.getIntNivel()-1).findViewById(R.id.bt_5),

		};


	}

	public void atualizar(Afirmacao af){
		TextView tv = (TextView) view.findViewById(R.id.afirmacao);
		textoAfirmacao = af.getSrc();
		if(af.getResposta().equals("T")){
			respostaCorreta = true;
		}else respostaCorreta = false;
		tv.setText(textoAfirmacao);
	}


	public void respostaSim(){

		if(respostaCorreta){
			feedBackImediato(true);

		} else {
			feedBackImediato(false);			

		}

	}

	public void respostaNao(){
		if(!respostaCorreta){
			feedBackImediato(true);
		} else {
			feedBackImediato(false);			

		}	

	}


	private void feedBackImediato(boolean acertou) {
		if (acertou) {
			callImage("Voce Acertou!!!", R.drawable.like);
			icons[quest_atual].setImageResource(R.drawable.v);
		} else {
			callImage("Voce Errou!!!", R.drawable.unlike);
			icons[quest_atual].setImageResource(R.drawable.x_red);
		total_erros++;
		}
		quest_atual++;
		if(quest_atual<lista_afirmacoes.size()){
			atualizar(lista_afirmacoes.get(quest_atual));
		} else {
            if (total_erros < 3 ) {

                Log.i("OI", "" + total_niveis);

                if(nivel.getIntNivel() < total_niveis){
                    Nivel proxNivel = niveis.buscarNivel(nivel.getIntNivel()+1);
                    callBack.onItemClicked(proxNivel);
                } else {
                    //fim do progama
                    ((Activity_Principal)getActivity()).finalizar_programa();
                }

            } else {
                callBack.onItemClicked(nivel);
            }
		}
	}

	private void callImage(CharSequence mensagem, int id){
		LayoutInflater inflater = this.getActivity().getLayoutInflater();
		View layout = inflater.inflate(R.layout.activity_curtir,
				(ViewGroup)  this.getActivity().findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(mensagem);

		//Setar imagem
		ImageView image = (ImageView) layout.findViewById(R.id.image);
		image.setImageResource(id);


		Toast toast = new Toast(getActivity().getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}


}
