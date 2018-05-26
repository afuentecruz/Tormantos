package com.alberto.tfg.tormantos.analizer;


import com.alberto.tfg.tormantos.sto.EventSto;

/**
 * Analizer general interface which each app analizer implements and modifies
 */
public interface Analizer {

    /**
     * Main method of the analizer, implements
     * the required logic to extract the
     * app context and user information
     *
     * @param eventSto the accessibility event, encapsulated
     */
    void compute(EventSto eventSto);

    /**
     * Stores the analized object in realmDB
     */
    void storeObjectInRealm();

}
