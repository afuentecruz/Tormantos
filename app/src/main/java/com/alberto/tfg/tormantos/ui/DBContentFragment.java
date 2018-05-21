package com.alberto.tfg.tormantos.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alberto.tfg.tormantos.R;
import com.alberto.tfg.tormantos.dto.messaging.WhatsappDto;
import com.alberto.tfg.tormantos.manager.messaging.WhatsappManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment to display the RealmDB content of the selected collection
 */
public class DBContentFragment extends Fragment {

    public static final String TAG ="DBContentFragment";

    @BindView(R.id.DBListView)
    ListView dbListView;

    public DBContentFragment(){
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_db_content, container, false);
        ButterKnife.bind(this, view);


        loadDBContent(view);

        return view;
    }

    private void loadDBContent(View view){
        List<WhatsappDto> whatsappDBList = WhatsappManager.getAllWhatsappModels();

        dbListView.setAdapter(new ArrayAdapter<WhatsappDto>(this.getActivity(), android.R.layout.simple_list_item_1, whatsappDBList));
    }





    }
