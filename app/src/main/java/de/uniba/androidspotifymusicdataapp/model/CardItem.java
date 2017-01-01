package de.uniba.androidspotifymusicdataapp.model;

/**
 * Created by chandan on 01/01/2017.
 */

public class CardItem {
    private String personName;
    private String personStatement;

    private int imageResourceId = android.R.drawable.ic_menu_camera;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonStatement() {
        return personStatement;
    }

    public void setPersonStatement(String personStatement) {
        this.personStatement = personStatement;
    }

}
