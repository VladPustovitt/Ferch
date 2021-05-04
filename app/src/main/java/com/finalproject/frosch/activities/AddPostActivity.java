package com.finalproject.frosch.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finalproject.frosch.R;
import com.finalproject.frosch.ui.LoaderIconPack;
import com.finalproject.frosch.utils.convertor.DateConvector;
import com.finalproject.frosch.utils.exeptions.TimeException;
import com.finalproject.frosch.utils.tasks.AddNoteToDatabaseTask;
import com.finalproject.frosch.database.AppDatabase;
import com.finalproject.frosch.database.Note;
import com.finalproject.frosch.database.TypeNote;
import com.finalproject.frosch.databinding.AddPostActivityBinding;
import com.finalproject.frosch.utils.exeptions.StringException;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorShape;
import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.IconDialogSettings;
import com.maltaisn.icondialog.data.Icon;
import com.maltaisn.icondialog.pack.IconPack;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class AddPostActivity
        extends AppCompatActivity
        implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, ColorPickerDialogListener,
        IconDialog.Callback{
    private AddPostActivityBinding binding;
    private AppDatabase database;
    private final AtomicReference<String> typeAtomic = new AtomicReference<>();
    private String color;
    private Integer icon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        database = AppDatabase.getInstance(this);
        binding.success.setOnClickListener(v -> {
            String type = typeAtomic.get();
            if (type == null){
                type = TypeNote.INCOME.getName();
            }

            if (this.color == null){
                this.color = "#"+Integer.toHexString(Color.BLACK);
            }

            if(this.icon == null){
                this.icon = getResourceIdByIconId(-1);
            }
            try {
                String name = Objects.requireNonNull(binding.name.getText()).toString();
                String comment = Objects.requireNonNull(binding.comment.getText()).toString();
                String date = Objects.requireNonNull(binding.date.getText()).toString();
                String time = Objects.requireNonNull(binding.time.getText()).toString();
                int sum = Integer.parseInt(Objects.requireNonNull(binding.sum.getText()).toString());

                StringException.equalNull(name);
                StringException.equalNull(comment);
                StringException.equalNull(date);
                StringException.equalNull(time);

                if(DateConvector.dateStringToMs(date+" "+time) > Calendar.getInstance().getTimeInMillis())
                    throw new TimeException("Такое время ещё не наступило. Измените дату или время");

                Note note = new Note(type, name, comment, sum, DateConvector.dateStringToMs(date + " " + time), color, icon);
                new AddNoteToDatabaseTask(database).execute(note);

                mainActivity();
            } catch (NullPointerException | NumberFormatException e){
                Toast.makeText(this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
            } catch (TimeException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        setContentView(binding.getRoot());
    }

    private void initBinding(){
        binding = AddPostActivityBinding.inflate(getLayoutInflater());
        binding.date.setOnClickListener(v -> {
            DatePickerDialog datePicker = new DatePickerDialog(
                    AddPostActivity.this, this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });

        binding.time.setOnClickListener(v ->{
            TimePickerDialog timePicker = new TimePickerDialog(
                    AddPostActivity.this, this,
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE),
                    true);
            timePicker.show();
        });

        binding.lastButton.setOnClickListener(this);

        binding.type.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                typeAtomic.set(TypeNote.CONSUMPTION.getName());
            } else {
                typeAtomic.set(TypeNote.INCOME.getName());
            }
        });

        binding.color.setOnClickListener(this);

        binding.icon.setImageResource(R.drawable.ic_dish_post);
        binding.icon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.last_button:
                mainActivity();
                break;
            case R.id.color:
                createColorPickerDialog();
                break;
            case R.id.icon:
                createIconPickerDialog();
                break;
        }
    }

    private void createIconPickerDialog() {
        IconDialog dialog = (IconDialog)getSupportFragmentManager().findFragmentByTag("icon-dialog");
        IconDialog iconDialog = dialog != null? dialog:
                IconDialog.newInstance(new IconDialogSettings.Builder().build());
        Log.d("Icon Picker", "Click icon");
        iconDialog.show(getSupportFragmentManager(), "icon-dialog");
    }

    private void createColorPickerDialog(){
        ColorPickerDialog.newBuilder()
                .setColor(Color.BLACK)
                .setDialogTitle(ColorPickerDialog.TYPE_CUSTOM)
                .setPresets(getResources().getIntArray(R.array.post_color))
                .setColorShape(ColorShape.CIRCLE)
                .setDialogId(0)
                .setDialogTitle(R.string.color_picker_dialog_title)
                .setAllowPresets(false)
                .setAllowCustom(false)
                .setShowColorShades(false)
                .show(this);
        Log.e("ColorPicker", "Color");
    }


    private void mainActivity(){
        Intent intent = new Intent(AddPostActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar.getInstance().set(Calendar.YEAR, year);
        Calendar.getInstance().set(Calendar.MONTH, month);
        Calendar.getInstance().set(Calendar.DAY_OF_MONTH, dayOfMonth);
        binding.date.setText(
                new StringBuilder()
                        .append(DateConvector.addZeros(Integer.toString(dayOfMonth)))
                        .append(".")
                        .append(DateConvector.addZeros(Integer.toString(month+1)))
                        .append(".").append(year));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar.getInstance().set(Calendar.HOUR_OF_DAY, hourOfDay);
        Calendar.getInstance().set(Calendar.MINUTE, minute);
        binding.time.setText(new StringBuilder()
                .append(DateConvector.addZeros(Integer.toString(hourOfDay)))
                .append(":")
                .append(DateConvector.addZeros(Integer.toString(minute))));
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        switch (dialogId){
            case 0:
                this.color = "#"+Integer.toHexString(color);
                ((GradientDrawable)this.binding.color.getBackground()).setColor(color);
                break;
        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {
        Log.d("AddPostActivity", "onDialogDismissed() called with id ["+dialogId+"]");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public IconPack getIconDialogIconPack() {
        return (new LoaderIconPack(this)).getIconPack();
    }

    @Override
    public void onIconDialogCancelled() {}

    @Override
    public void onIconDialogIconsSelected(@NotNull IconDialog iconDialog, @NotNull List<Icon> list) {
        if(list.size() == 1){
            Icon icon = list.get(0);
            this.icon = getResourceIdByIconId(icon.getId());
            binding.icon.setImageResource(this.icon);
        } else if (list.size() == 0){
            Toast.makeText(this, "Выберите иконку", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Надо выбрать ОДНУ иконку", Toast.LENGTH_SHORT).show();
        }
    }

    private int getResourceIdByIconId(int id){
        switch (id){
            case 0: return R.drawable.ic_apartment_post;
            case 1: return R.drawable.ic_book_post;
            case 2: return R.drawable.ic_chef_suit_post;
            case 3: return R.drawable.ic_coding_post;
            case 4: return R.drawable.ic_delivery_man_post;
            case 5: return R.drawable.ic_devices_post;
            case 7: return R.drawable.ic_grocery_post;
            case 8: return R.drawable.ic_popcorn_post;
            case 9: return R.drawable.ic_sport_post;
            case 10: return R.drawable.ic_subscribe_post;
            case 11: return R.drawable.ic_subway_post;
            case 12: return R.drawable.ic_utilities_post;
            case 13: return R.drawable.ic_bingo_post;
            case 14: return R.drawable.ic_business_and_finance_post;
            case 15: return R.drawable.ic_business_post;
            case 16: return R.drawable.ic_medal_post;
            case 17: return R.drawable.ic_money_post;
            case 18: return R.drawable.ic_money_transfer_post;
            case 19: return R.drawable.ic_scholarship_post;
            case 20: return R.drawable.ic_stock_post;
            case 21: return R.drawable.ic_working_post;
            default: return R.drawable.ic_dish_post;
        }
    }
}
