package com.finalproject.frosch.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finalproject.frosch.utils.tasks.AddNoteToDatabaseTask;
import com.finalproject.frosch.database.AppDatabase;
import com.finalproject.frosch.database.Note;
import com.finalproject.frosch.database.TypeNote;
import com.finalproject.frosch.databinding.AddPostActivityBinding;

import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class AddPostActivity
        extends AppCompatActivity
        implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {
    private AddPostActivityBinding binding;
    private AppDatabase database;
    private final AtomicReference<String> typeAtomic = new AtomicReference<>();

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
            try {
                String name = Objects.requireNonNull(binding.name.getText()).toString();
                String comment = Objects.requireNonNull(binding.comment.getText()).toString();
                String date = Objects.requireNonNull(binding.date.getText()).toString();
                String time = Objects.requireNonNull(binding.time.getText()).toString();
                int sum = Integer.parseInt(Objects.requireNonNull(binding.sum.getText()).toString());

                if (name.equals("") || name.matches("^\\s\\+")){
                    throw new NullPointerException();
                }

                if (comment.equals("") || comment.matches("^\\s\\+")){
                    throw new NullPointerException();
                }

                Note note = new Note(type, name, comment, sum, date + " " + time);
                new AddNoteToDatabaseTask(database).execute(note);

                mainActivity();
            } catch (NullPointerException e){
                Toast.makeText(this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e){
                Toast.makeText(this, "Поле 'Сумма' должно быть заполнено!", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void onClick(View v){
        mainActivity();
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
                new StringBuilder().append(dayOfMonth).append(".")
                        .append(month + 1).append(".").append(year));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar.getInstance().set(Calendar.HOUR_OF_DAY, hourOfDay);
        Calendar.getInstance().set(Calendar.MINUTE, minute);
        binding.time.setText(new StringBuilder().append(hourOfDay).append(":").append(minute));
    }
}
