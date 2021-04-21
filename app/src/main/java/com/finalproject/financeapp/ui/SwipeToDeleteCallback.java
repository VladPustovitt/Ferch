package com.finalproject.financeapp.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.financeapp.R;
import com.finalproject.financeapp.activities.MainActivity;

public abstract class SwipeToDeleteCallback
extends ItemTouchHelper.SimpleCallback {

    private final Context context;
    private final ColorDrawable background;
    private final int color;
    private final Drawable deleteIcon;
    private final int inHeight;
    private final int inWeight;
    private final Paint clearPaint = new Paint();


    protected SwipeToDeleteCallback(Context context){
        super(0, ItemTouchHelper.LEFT);
        this.context = context;
        this.background = new ColorDrawable();
        this.color = Color.parseColor("#C90C0C");
        this.deleteIcon = ContextCompat.getDrawable(this.context, R.drawable.ic_delete);
        if (this.deleteIcon != null) {
            this.inHeight = this.deleteIcon.getIntrinsicHeight();
            this.inWeight = this.deleteIcon.getIntrinsicWidth();
        } else {
            this.inHeight = 0;
            this.inWeight = 0;
        }
        this.clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        int itemHeight = viewHolder.itemView.getBottom() - viewHolder.itemView.getTop();
        boolean isCanceled = dX == 0 && !isCurrentlyActive;

        if (isCanceled){
            this.clearCanvas(c, viewHolder.itemView.getRight()+dX,
                    (float)viewHolder.itemView.getTop(),
                    (float)viewHolder.itemView.getRight(),
                    (float)viewHolder.itemView.getBottom());
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }

        background.setColor(color);
        background.setBounds(
                viewHolder.itemView.getRight() + (int)dX,
                viewHolder.itemView.getTop(),
                viewHolder.itemView.getRight(),
                viewHolder.itemView.getBottom()
        );
        background.draw(c);
        int iconTop = viewHolder.itemView.getTop() + (itemHeight - this.inHeight)/2;
        int iconMargin = (itemHeight - inHeight)/2;
        int iconLeft = viewHolder.itemView.getRight() - iconMargin - this.inWeight;
        int iconRight = viewHolder.itemView.getRight() - iconMargin;
        int iconBottom = iconTop + this.inHeight;

        this.deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
        this.deleteIcon.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void clearCanvas(Canvas c, float left, float top, float right, float bottom){
        c.drawRect(left, top, right, bottom, this.clearPaint);
    }
}
