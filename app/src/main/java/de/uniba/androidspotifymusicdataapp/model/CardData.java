package de.uniba.androidspotifymusicdataapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chandan on 01/01/2017.
 */
public class CardData {

    private static final String[] personNames = {"Eddard Stark","Tyrion Lanister","John Snow","Robert Baratheon"};
    private static final String[] personStatements = {"Winter is Coming and it will be a long winter","Look at me Bastard and tell me what you see !!!",
            "I have seen what is out there, north of the wall and no army can stop it","Help me Ned so that I could drink, whore and go to an early grave"};

    /**
     * Method for getting the data;
     * @return
     */
    public static List<CardItem> getDataForCards(){
        List<CardItem> cardItemList = new ArrayList<>();

        for(int i=0;i<4;i++){
            for(int j=0;j< personNames.length && j< personStatements.length;j++){
                CardItem cardItem = new CardItem();
                cardItem.setPersonName(personNames[j]);
                cardItem.setPersonStatement(personStatements[j]);
                cardItemList.add(cardItem);
            }
        }
        return cardItemList;
    }
}
