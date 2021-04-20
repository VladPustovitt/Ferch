package com.finalproject.financeapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.finalproject.financeapp.database.AddNoteToDatabaseTask;
import com.finalproject.financeapp.database.AppDatabase;
import com.finalproject.financeapp.database.Note;
import com.finalproject.financeapp.database.TypeNote;
import com.finalproject.financeapp.databinding.AddPostActivityBinding;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;

public class AddPostActivity extends AppCompatActivity {
    private AddPostActivityBinding binding;
    private AppDatabase database;
    private final AtomicReference<String> typeAtomic = new AtomicReference<>();

    private final DatePickerDialog.OnDateSetListener dateListener =
            (view, year1, month1, dayOfMonth) -> {
        Calendar.getInstance().set(Calendar.YEAR, year1);
        Calendar.getInstance().set(Calendar.MONTH, month1);
        Calendar.getInstance().set(Calendar.DAY_OF_MONTH, dayOfMonth);
        binding.date.setText(
                new StringBuilder().append(dayOfMonth).append(".")
                        .append(month1 + 1).append(".").append(year1));
    };

    private final TimePickerDialog.OnTimeSetListener timeListener = (view, hourOfDay, minute1) -> {
        Calendar.getInstance().set(Calendar.HOUR_OF_DAY, hourOfDay);
        Calendar.getInstance().set(Calendar.MINUTE, minute1);
        binding.time.setText(new StringBuilder().append(hourOfDay).append(":").append(minute1));
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();

        database = Room.databaseBuilder(this,
                AppDatabase.class, MainActivity.dataName).build();
        binding.success.setOnClickListener(v -> {
            String type = typeAtomic.get();
            if (type == null){
                type = TypeNote.INCOME.getName();
            }
            String name = binding.name.getText().toString();
            String comment = binding.comment.getText().toString();
            int sum = Integer.parseInt(binding.sum.getText().toString());
            String date =
                    binding.date.getText().toString() + ' ' + binding.time.getText().toString();
            Note note = new Note(type, name, comment, sum, date);
            new AddNoteToDatabaseTask(database).execute(note);
            Intent intent = new Intent(AddPostActivity.this, MainActivity.class);
            startActivity(intent);
        });
        setContentView(binding.getRoot());
    }

    private void initBinding(){
        binding = AddPostActivityBinding.inflate(getLayoutInflater());
        binding.date.setOnClickListener(v -> {
            DatePickerDialog datePicker = new DatePickerDialog(
                    AddPostActivity.this, dateListener,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });

        binding.time.setOnClickListener(v ->{
            TimePickerDialog timePicker = new TimePickerDialog(
                    AddPostActivity.this,
                    timeListener,
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE),
                    true);
            timePicker.show();
        });

        binding.lastButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddPostActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        binding.type.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                typeAtomic.set(TypeNote.CONSUMPTION.getName());
            } else {
                typeAtomic.set(TypeNote.INCOME.getName());
            }
        });
    }
}
