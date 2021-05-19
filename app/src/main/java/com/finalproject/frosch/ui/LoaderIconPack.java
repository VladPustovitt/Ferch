package com.finalproject.frosch.ui;

import android.content.Context;

import androidx.core.util.Pair;

import com.finalproject.frosch.R;
import com.finalproject.frosch.database.HashTag;
import com.maltaisn.icondialog.pack.IconPack;
import com.maltaisn.icondialog.pack.IconPackLoader;

import java.util.Collections;
import java.util.Locale;

public class LoaderIconPack{
    private IconPack iconPack;
    private final Context context;

    public LoaderIconPack(Context context){
        this.context = context;
    }

    public IconPack getIconPack(){
        return iconPack != null? iconPack: loadIconPack();
    }

    private IconPack loadIconPack() {
        IconPackLoader loader = new IconPackLoader(this.context);
        iconPack = loader.load(R.xml.icons_post, 0,
                Collections.singletonList(Locale.ENGLISH), null);
        iconPack.loadDrawables(loader.getDrawableLoader());
        return iconPack;
    }

    public static Pair<Pair<Integer, Integer>, String> getResourceIdAndHashTagByIconId(int id){
        switch (id){
            case 0:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_apartment_post, R.color.orange),
                        HashTag.Consumption.HOME.getName());
            case 1:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_book_post, R.color.day),
                        HashTag.Consumption.BOOK.getName());
            case 2:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_chef_suit_post, R.color.light_purple),
                        HashTag.Consumption.CLOTHE.getName());
            case 3:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_coding_post, R.color.night),
                        HashTag.Consumption.PROGRAM.getName());
            case 4:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_delivery_man_post, R.color.light_blue),
                        HashTag.Consumption.DELIVERY.getName());
            case 5:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_devices_post, R.color.consumption),
                        HashTag.Consumption.GADGET.getName());
            case 7:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_grocery_post, R.color.salat),
                        HashTag.Consumption.GROCERY.getName());
            case 8:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_popcorn_post, R.color.current_color),
                        HashTag.Consumption.FUN.getName());
            case 9:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_sport_post, R.color.blue),
                        HashTag.Consumption.SPORT.getName());
            case 10:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_subscribe_post, R.color.red),
                        HashTag.Consumption.SUBSCRIBE.getName());
            case 11:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_subway_post, R.color.dark_gray),
                        HashTag.Consumption.TRANSPORT.getName());
            case 12:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_utilities_post, R.color.raspberry),
                        HashTag.Consumption.COMMUNAL.getName());
            case 14:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_business_and_finance_post, R.color.green),
                        HashTag.Income.DEPOSIT.getName());
            case 16:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_medal_post, R.color.pink),
                        HashTag.Income.WIN.getName());
            case 17:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_money_post, R.color.income),
                        HashTag.Income.SALARY.getName());
            case 18:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_money_transfer_post, R.color.light_blue_2),
                        HashTag.Income.TRANSFER.getName());
            default:
                return new Pair<>(
                        new Pair<>(R.drawable.ic_dish_post, R.color.food),
                        HashTag.Consumption.FOOD.getName());
        }
    }
}
