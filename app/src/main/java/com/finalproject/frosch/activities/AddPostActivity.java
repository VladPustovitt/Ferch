package com.finalproject.frosch.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.finalproject.frosch.R;
import com.finalproject.frosch.database.HashTag;
import com.finalproject.frosch.ui.LoaderIconPack;
import com.finalproject.frosch.utils.convertor.DateConvector;
import com.finalproject.frosch.utils.exeptions.TimeException;
import com.finalproject.frosch.utils.tasks.AddNoteToDatabaseTask;
import com.finalproject.frosch.database.AppDatabase;
import com.finalproject.frosch.database.Note;
import com.finalproject.frosch.database.TypeNote;
import com.finalproject.frosch.databinding.AddPostActivityBinding;
import com.finalproject.frosch.utils.exeptions.StringException;
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
        TimePickerDialog.OnTimeSetListener,
        IconDialog.Callback{
    protected AddPostActivityBinding binding;
    protected AppDatabase database;
    protected final AtomicReference<String> typeAtomic = new AtomicReference<>();
    protected String color;
    protected Integer icon;
    protected String hashTag;
    protected Calendar calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
        initBinding();
        database = AppDatabase.getInstance(this);
        binding.success.setOnClickListener(v -> {
            String type = typeAtomic.get();
            if (type == null){
                type = TypeNote.INCOME.getName();
            }

            if(this.icon == null){
                Pair<Integer, Integer> icon_color = LoaderIconPack.getResourceIdAndHashTagByIconId(-1).first;
                this.icon = icon_color.first;
                this.color = "#" + Integer.toHexString(getResources().getColor(icon_color.second));
            }

            if(this.hashTag == null){
                this.hashTag = HashTag.Consumption.FOOD.getName();
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

                Note note = new Note(type, name, comment, sum, DateConvector.dateStringToMs(date + " " + time), color, icon, hashTag);
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

    protected void initBinding(){
        binding = AddPostActivityBinding.inflate(getLayoutInflater());
        binding.date.setOnClickListener(v -> {
            DatePickerDialog datePicker = new DatePickerDialog(
                    AddPostActivity.this, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });

        binding.time.setOnClickListener(v ->{
            TimePickerDialog timePicker = new TimePickerDialog(
                    AddPostActivity.this, this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
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
        ((GradientDrawable)this.binding.icon.getBackground()).setColor(
                getResources().getColor(LoaderIconPack.getResourceIdAndHashTagByIconId(-1).first.second));
        binding.icon.setImageResource(R.drawable.ic_dish_post);
        binding.icon.setOnClickListener(this);
        String category = "Категория: " + LoaderIconPack.getResourceIdAndHashTagByIconId(-1).second;
        binding.category.setText(category);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.last_button:
                mainActivity();
                break;
            case R.id.icon:
                createIconPickerDialog();
                break;
        }
    }

    protected void createIconPickerDialog() {
        IconDialog dialog = (IconDialog)getSupportFragmentManager().findFragmentByTag("icon-dialog");
        IconDialog iconDialog = dialog != null? dialog:
                IconDialog.newInstance(new IconDialogSettings.Builder().build());
        iconDialog.show(getSupportFragmentManager(), "icon-dialog");
    }

    protected void mainActivity(){
        Intent intent = new Intent(AddPostActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(this.getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        binding.date.setText(
                new StringBuilder()
                        .append(DateConvector.addZeros(Integer.toString(dayOfMonth)))
                        .append(".")
                        .append(DateConvector.addZeros(Integer.toString(month+1)))
                        .append(".").append(year));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(this.getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        Calendar.getInstance().set(Calendar.HOUR_OF_DAY, hourOfDay);
        Calendar.getInstance().set(Calendar.MINUTE, minute);
        binding.time.setText(new StringBuilder()
                .append(DateConvector.addZeros(Integer.toString(hourOfDay)))
                .append(":")
                .append(DateConvector.addZeros(Integer.toString(minute))));
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
        if(this.getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        if(list.size() == 1){
            Icon icon = list.get(0);
            Pair<Pair<Integer, Integer>, String> iconAndHashTag =
                    LoaderIconPack.getResourceIdAndHashTagByIconId(icon.getId());
            this.icon = iconAndHashTag.first.first;
            this.color = "#"+Integer.toHexString(getResources().getColor(iconAndHashTag.first.second));
            this.hashTag = iconAndHashTag.second;
            binding.icon.setImageResource(this.icon);
            ((GradientDrawable)binding.icon.getBackground()).setColor(Color.parseColor(color));
            String category = "Категория: "+hashTag;
            binding.category.setText(category);
        } else if (list.size() == 0){
            Toast.makeText(this, "Выберите иконку", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Надо выбрать ОДНУ иконку", Toast.LENGTH_SHORT).show();
        }
    }
}
