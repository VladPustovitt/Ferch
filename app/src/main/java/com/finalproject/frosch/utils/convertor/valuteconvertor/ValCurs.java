package com.finalproject.frosch.utils.convertor.valuteconvertor;

import androidx.annotation.NonNull;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Arrays;

@Root(name = "ValCurs")
public class ValCurs {

    @Root(name = "Valute")
    public class Valute {

        @Element(name = "CharCode")
        private String CharCode;

        @Element(name = "Value")
        private String Value;

        @Attribute(name = "ID")
        private String ID;

        @Element(name = "Nominal")
        private String Nominal;

        @Element(name = "NumCode")
        private String NumCode;

        @Element(name = "Name")
        private String Name;

        public Valute() {}

        public String getID ()
        {
            return ID;
        }

        public String getValue() {
            return Value;
        }
    }

    @Attribute(name = "name")
    private String name;

    @Attribute(name = "Date")
    private String date;

    @ElementList(name="Valute")
    private ArrayList<Valute> valutes;


    @NonNull
    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", Date = "+date+", Valute = "+ valutes +"]";
    }

    public ValCurs(){}

    public Valute getValuteById(String ID){
        Valute valute = new Valute();
        for(Valute iValute: valutes){
            if(iValute.getID().equals(ID)) {
                valute = iValute;
                break;
            }
        }
        return valute;
    }
}
