package com.finalproject.frosch.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.finalproject.frosch.R;
import com.finalproject.frosch.database.AppDatabase;
import com.finalproject.frosch.database.HashTag;
import com.finalproject.frosch.database.Note;
import com.finalproject.frosch.database.TypeNote;
import com.finalproject.frosch.databinding.AddPostActivityBinding;
import com.finalproject.frosch.databinding.UpdatePostActivityBinding;
import com.finalproject.frosch.ui.LoaderIconPack;
import com.finalproject.frosch.utils.convertor.DateConvector;
import com.finalproject.frosch.utils.exeptions.StringException;
import com.finalproject.frosch.utils.exeptions.TimeException;
import com.finalproject.frosch.utils.tasks.AddNoteToDatabaseTask;
import com.finalproject.frosch.utils.tasks.UpdateNoteTask;
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

public class UpdatePostActivity extends AppCompatActivity
        implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener,
        IconDialog.Callback{
    private Note note;
    private UpdatePostActivityBinding uBinding;
    protected AppDatabase database;
    protected AtomicReference<String> typeAtomic = new AtomicReference<>();
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
        uBinding.success.setOnClickListener(v -> {
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
                String name = Objects.requireNonNull(uBinding.name.getText()).toString();
                String comment = Objects.requireNonNull(uBinding.comment.getText()).toString();
                String date = Objects.requireNonNull(uBinding.date.getText()).toString();
                String time = Objects.requireNonNull(uBinding.time.getText()).toString();
                int sum = Integer.parseInt(Objects.requireNonNull(uBinding.sum.getText()).toString());

                StringException.equalNull(name);
                StringException.equalNull(comment);
                StringException.equalNull(date);
                StringException.equalNull(time);

                if(DateConvector.dateStringToMs(date+" "+time) > Calendar.getInstance().getTimeInMillis())
                    throw new TimeException("Такое время ещё не наступило. Измените дату или время");

                note.setName(name);
                note.setComment(comment);
                note.setDate(DateConvector.dateStringToMs(date+" "+time));
                note.setSum(sum);
                note.setType(type);
                note.setColor(color);
                note.setIcon(icon);
                note.setHashTag(hashTag);
                
                new UpdateNoteTask(database).execute(note);

                mainActivity();
            } catch (NullPointerException | NumberFormatException e){
                Toast.makeText(this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
            } catch (TimeException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        setContentView(uBinding.getRoot());
    }

    protected void initBinding() {
        uBinding = UpdatePostActivityBinding.inflate(getLayoutInflater());
        note = Note.fromString(getIntent().getExtras().getString("CURRENT_NOTE"));
        String dateAndTime = DateConvector.msToDateString(note.getDate());
        uBinding.name.setText(note.getName());
        uBinding.comment.setText(note.getComment());
        String date = dateAndTime.split(" ")[0];
        this.onDateSet(null,
                Integer.parseInt(date.split("\\.")[2]),
                Integer.parseInt(date.split("\\.")[1])-1,
                Integer.parseInt(date.split("\\.")[0]));
        uBinding.date.setOnClickListener(v -> {
            DatePickerDialog datePicker = new DatePickerDialog(
                    UpdatePostActivity.this, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });
        String time = dateAndTime.split(" ")[1];
        this.onTimeSet(null,
                Integer.parseInt(time.split(":")[0]),
                Integer.parseInt(time.split(":")[1]));
        uBinding.time.setOnClickListener(v -> {
            TimePickerDialog timePicker = new TimePickerDialog(
                    UpdatePostActivity.this, this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true);
            timePicker.show();
        });
        uBinding.sum.setText(Integer.toString(note.getSum()));
        uBinding.lastButton.setOnClickListener(this);
        uBinding.type.setChecked(note.getType().equals(TypeNote.CONSUMPTION.getName()));
        uBinding.type.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                typeAtomic.set(TypeNote.CONSUMPTION.getName());
            } else {
                typeAtomic.set(TypeNote.INCOME.getName());
            }
        });
        this.color = note.getColor();
        this.icon = note.getIcon();
        this.typeAtomic = new AtomicReference<>(note.getType());
        ((GradientDrawable)uBinding.icon.getBackground()).setColor(Color.parseColor(this.color));
        uBinding.icon.setImageResource(this.icon);
        this.hashTag = note.getHashTag();
        uBinding.icon.setOnClickListener(this);
        String category = "Категория: " + this.hashTag;
        uBinding.category.setText(category);
    }

    protected void createIconPickerDialog() {
        IconDialog dialog = (IconDialog)getSupportFragmentManager().findFragmentByTag("icon-dialog");
        IconDialog iconDialog = dialog != null? dialog:
                IconDialog.newInstance(new IconDialogSettings.Builder().build());
        iconDialog.show(getSupportFragmentManager(), "icon-dialog");
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
        uBinding.date.setText(
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
        uBinding.time.setText(new StringBuilder()
                .append(DateConvector.addZeros(Integer.toString(hourOfDay)))
                .append(":")
                .append(DateConvector.addZeros(Integer.toString(minute))));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.last_button:
                mainActivity();
                break;
            case R.id.icon:
                createIconPickerDialog();
                break;
        }
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
            uBinding.icon.setImageResource(this.icon);
            ((GradientDrawable)uBinding.icon.getBackground()).setColor(Color.parseColor(color));
            String category = "Категория: "+hashTag;
            uBinding.category.setText(category);
        } else if (list.size() == 0){
            Toast.makeText(this, "Выберите иконку", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Надо выбрать ОДНУ иконку", Toast.LENGTH_SHORT).show();
        }
    }

    protected void mainActivity(){
        Intent intent = new Intent(UpdatePostActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
