package com.alberto.tfg.tormantos.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alberto.tfg.tormantos.R;
import com.alberto.tfg.tormantos.dto.NotificationDto;
import com.alberto.tfg.tormantos.dto.browsing.ChromeDto;
import com.alberto.tfg.tormantos.dto.browsing.FirefoxDto;
import com.alberto.tfg.tormantos.dto.comunication.GmailDto;
import com.alberto.tfg.tormantos.dto.comunication.SmsDto;
import com.alberto.tfg.tormantos.dto.messaging.WhatsappDto;
import com.alberto.tfg.tormantos.manager.DBManager;
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

        ArrayAdapter adapter = null;
        if (this.contentDescription != null) {
            switch (this.contentDescription) {
                case Strings.PACKAGE_WHATSAPP:
                    adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, DBManager.getAllObjects(WhatsappDto.class));
                    //dbListView.setAdapter(new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, DBManager.getAllObjects(WhatsappDto.class)));
                    break;
                case Strings.PACKAGE_GMAIL:
                    adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, DBManager.getAllObjects(GmailDto.class));
                    break;
                case Strings.PACKAGE_SMS:
                    adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, DBManager.getAllObjects(SmsDto.class));
                    break;
                case Strings.PACKAGE_FIREFOX:
                    adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, DBManager.getAllObjects(FirefoxDto.class));
                    break;
                case Strings.PACKAGE_CHROME:
                    adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, DBManager.getAllObjects(ChromeDto.class));
                    break;
                case Strings.CLASS_NOTIFICATION:
                    adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, DBManager.getAllObjects(NotificationDto.class));
                    break;
                default:
                    break;
            }
            if(adapter != null){
                dbListView.setAdapter(adapter);
            }

        }
    }


}
