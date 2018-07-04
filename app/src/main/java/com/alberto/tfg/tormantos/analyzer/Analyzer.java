package com.alberto.tfg.tormantos.analyzer;


import com.alberto.tfg.tormantos.sto.EventSto;

/**
 * Analyzer general interface which each app analyzer implements and modifies
 */
public interface Analyzer {

    /**
     * Main method of the analyzer, implements
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
