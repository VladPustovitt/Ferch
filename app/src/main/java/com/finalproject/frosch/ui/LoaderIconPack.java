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
        iconPack = loader.load(R.xml.icons_post, 0, Collections.singletonList(Locale.ENGLISH), null);
        iconPack.loadDrawables(loader.getDrawableLoader());
        return iconPack;
    }

    public static Pair<Integer, String> getResourceIdAndHashTagByIconId(int id){
        switch (id){
            case 0:
                return new Pair<>(R.drawable.ic_apartment_post, HashTag.Consumption.HOME.getName());
            case 1:
                return new Pair<>(R.drawable.ic_book_post, HashTag.Consumption.BOOK.getName());
            case 2:
                return new Pair<>(R.drawable.ic_chef_suit_post, HashTag.Consumption.CLOTHE.getName());
            case 3:
                return new Pair<>(R.drawable.ic_coding_post, HashTag.Consumption.PROGRAM.getName());
            case 4:
                return new Pair<>(R.drawable.ic_delivery_man_post, HashTag.Consumption.DELIVERY.getName());
            case 5:
                return new Pair<>(R.drawable.ic_devices_post, HashTag.Consumption.GADGET.getName());
            case 7:
                return new Pair<>(R.drawable.ic_grocery_post, HashTag.Consumption.GROCERY.getName());
            case 8:
                return new Pair<>(R.drawable.ic_popcorn_post, HashTag.Consumption.FUN.getName());
            case 9:
                return new Pair<>(R.drawable.ic_sport_post, HashTag.Consumption.SPORT.getName());
            case 10:
                return new Pair<>(R.drawable.ic_subscribe_post, HashTag.Consumption.SUBSCRIBE.getName());
            case 11:
                return new Pair<>(R.drawable.ic_subway_post, HashTag.Consumption.TRANSPORT.getName());
            case 12:
                return new Pair<>(R.drawable.ic_utilities_post, HashTag.Consumption.COMMUNAL.getName());
            case 13:
                return new Pair<>(R.drawable.ic_bingo_post, HashTag.Income.LOTTERY.getName());
            case 14:
                return new Pair<>(R.drawable.ic_business_and_finance_post, HashTag.Income.DEPOSIT.getName());
            case 15:
                return new Pair<>(R.drawable.ic_business_post, HashTag.Income.SAVING.getName());
            case 16:
                return new Pair<>(R.drawable.ic_medal_post, HashTag.Income.WIN.getName());
            case 17:
                return new Pair<>(R.drawable.ic_money_post, HashTag.Income.SALARY.getName());
            case 18:
                return new Pair<>(R.drawable.ic_money_transfer_post, HashTag.Income.TRANSFER.getName());
            case 19:
                return new Pair<>(R.drawable.ic_scholarship_post, HashTag.Income.GRANT.getName());
            case 20:
                return new Pair<>(R.drawable.ic_stock_post, HashTag.Income.TRADING.getName());
            case 21:
                return new Pair<>(R.drawable.ic_working_post, HashTag.Income.EXTRA_WORK.getName());
            default:
                return new Pair<>(R.drawable.ic_dish_post, HashTag.Consumption.FOOD.getName());
        }
    }
}
