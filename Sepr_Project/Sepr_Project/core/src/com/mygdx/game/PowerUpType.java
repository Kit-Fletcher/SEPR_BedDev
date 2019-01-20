package com.mygdx.game;


/*Possible PowerUp types */

public enum PowerUpType {
    REDVK("heal"),
    BLUEVK("decreseDamage"),
    YLWVK("increaseSpeed");

    private final String type;
    /*
     * Create a new PowerUp type 
     * @param newtype: String 
     * */    
    PowerUpType(String newtype) {
        type = newtype;
    }

    public String getEffect() {
        return type;
    }
}
