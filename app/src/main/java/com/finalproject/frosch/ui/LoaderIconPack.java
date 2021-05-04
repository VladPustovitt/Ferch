package com.finalproject.frosch.ui;

import android.app.Application;
import android.content.Context;

import com.finalproject.frosch.R;
import com.maltaisn.icondialog.pack.IconPack;
import com.maltaisn.icondialog.pack.IconPackLoader;
import com.maltaisn.iconpack.defaultpack.IconPackDefault;

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
//        iconPack = IconPackDefault.createDefaultIconPack(loader);
        iconPack.loadDrawables(loader.getDrawableLoader());
        return iconPack;
    }
}
