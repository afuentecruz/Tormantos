package com.alberto.tfg.tormantos.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alberto.tfg.tormantos.R;
import com.alberto.tfg.tormantos.dto.browsing.FirefoxDto;
import com.alberto.tfg.tormantos.dto.comunication.GmailDto;
import com.alberto.tfg.tormantos.dto.comunication.SmsDto;
import com.alberto.tfg.tormantos.dto.messaging.WhatsappDto;
import com.alberto.tfg.tormantos.manager.browsing.FirefoxManager;
import com.alberto.tfg.tormantos.manager.communication.GmailManager;
import com.alberto.tfg.tormantos.manager.communication.SmsManager;
import com.alberto.tfg.tormantos.manager.messaging.WhatsappManager;
import com.alberto.tfg.tormantos.utils.Strings;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment to display the RealmDB content of the selected collection
 */
public class DBContentFragment extends Fragment {

    public static final String TAG = "DBContentFragment";

    private String contentDescription;

    @BindView(R.id.DBListView)
    ListView dbListView;

    public DBContentFragment() {
        // Required empty constructor
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_db_content, container, false);
        ButterKnife.bind(this, view);


        loadDBContent(view);

        return view;
    }

    private void loadDBContent(View view) {

        if (this.contentDescription != null) {
            switch (this.contentDescription) {
                case Strings.PACKAGE_WHATSAPP:
                    dbListView.setAdapter(new ArrayAdapter<WhatsappDto>(this.getActivity(), android.R.layout.simple_list_item_1, WhatsappManager.getAllWhatsappModels()));
                    break;
                case Strings.PACKAGE_GMAIL:
                    dbListView.setAdapter(new ArrayAdapter<GmailDto>(this.getActivity(), android.R.layout.simple_list_item_1, GmailManager.getAllGmailModels()));
                    break;
                case Strings.PACKAGE_SMS:
                    dbListView.setAdapter(new ArrayAdapter<SmsDto>(this.getActivity(), android.R.layout.simple_list_item_1, SmsManager.getAllSmsModels()));
                case Strings.PACKAGE_FIREFOX:
                    dbListView.setAdapter(new ArrayAdapter<FirefoxDto>(this.getActivity(), android.R.layout.simple_list_item_1, FirefoxManager.getAllFirefoxModels()));
                default:
                    break;
            }
        }

    }


}
