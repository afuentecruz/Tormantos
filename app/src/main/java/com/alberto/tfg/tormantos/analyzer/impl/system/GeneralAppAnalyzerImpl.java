package com.alberto.tfg.tormantos.analyzer.impl.system;

import android.content.Context;
import android.widget.Toast;

import com.alberto.tfg.tormantos.analyzer.Analyzer;
import com.alberto.tfg.tormantos.dto.system.GeneralAppDto;
import com.alberto.tfg.tormantos.manager.DBManager;
import com.alberto.tfg.tormantos.sto.EventSto;

public class GeneralAppAnalyzerImpl implements Analyzer{
    private Context context;
    private GeneralAppDto generalAppDto;

    public GeneralAppAnalyzerImpl(Context context) {
        this.context = context;
    }

    @Override
    public void compute(EventSto eventSto) {
        if(generalAppDto == null){
            this.generalAppDto = new GeneralAppDto(eventSto.getCaptureInstant());
            this.generalAppDto.setPackageName(eventSto.getPackageName());
        }
    }

    public void confirmEndOfUsage(EventSto eventSto) {
        if(this.generalAppDto != null &&
                !this.generalAppDto.getPackageName().equals(eventSto.getPackageName()) &&
                this.generalAppDto.getStartTimestamp() != null){
            this.generalAppDto.setEndTimestamp(eventSto.getCaptureInstant());
            storeObjectInRealm();
        }
    }

    @Override
    public void storeObjectInRealm() {
        DBManager.saveOrUpdate(this.generalAppDto);
        Toast.makeText(context, "Stored generalItem:\n" + this.generalAppDto.toString(), Toast.LENGTH_LONG).show();

        this.generalAppDto = null;
    }
}
