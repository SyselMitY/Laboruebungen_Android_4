package cf.soisi.tankmesser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import cf.soisi.tankmesser.db.Tankvorgang;
import cf.soisi.tankmesser.db.TankvorgangDataSource;

public class CreateEntryActivity extends AppCompatActivity {

    private LocalDate selectedDate;
    private TankvorgangDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);
        dataSource = new TankvorgangDataSource(this);

        DatePickerFragment datePicker = new DatePickerFragment(this::listenOnDateSelected);
        findViewById(R.id.inputDate).setOnClickListener(
                v -> datePicker.show(getSupportFragmentManager(), "datePicker"));

        findViewById(R.id.button_create_save).setOnClickListener(this::saveEntry);

        try {
            dataSource.open();
            Integer lastKm = dataSource.getLatestKmValue();
            ((EditText) findViewById(R.id.inputKmOld)).setText(String.valueOf(lastKm));
        } finally {
            dataSource.close();
        }
    }

    private void saveEntry(View view) {

        try {
            dataSource.open();
            int kmAlt = Integer.parseInt(getStringFromViewId(R.id.inputKmOld));
            int kmNeu = Integer.parseInt(getStringFromViewId(R.id.inputKmNew));
            double menge = Double.parseDouble(getStringFromViewId(R.id.inputFuelAmount));
            double preis = Double.parseDouble(getStringFromViewId(R.id.inputFuelPrice));

            if (kmAlt > kmNeu) {
                showErrorToast("Neuer Kilometerstand muss kleiner als alter sein");
                return;
            } else if (kmAlt < 0 || kmNeu < 0) {
                showErrorToast("Kilometerstand darf nicht negativ sein");
                return;
            } else if (menge <= 0) {
                showErrorToast("Menge muss größer 0 sein");
                return;
            } else if (preis <= 0) {
                showErrorToast("Preis muss größer 0 sein");
                return;
            }

            Tankvorgang tankvorgang = new Tankvorgang(selectedDate, kmAlt, kmNeu, menge, preis);
            dataSource.insertTankvorgang(tankvorgang);
            navigateUpTo(new Intent(this, MainActivity.class));
        } catch (NumberFormatException e) {
            showErrorToast("Felder dürfen nicht leer sein");
        } finally {
            dataSource.close();
        }
    }

    private void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private String getStringFromViewId(int id) {
        return ((EditText) findViewById(id)).getText().toString();
    }


    private void listenOnDateSelected(DatePicker datePicker, int year, int month, int day) {
        selectedDate = LocalDate.of(year, month + 1, day);
        updateDateField();
    }

    private void updateDateField() {
        EditText dateInput = findViewById(R.id.inputDate);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d. MMMM uuuu");
        dateInput.setText(dateFormat.format(selectedDate));
    }

    public static class DatePickerFragment extends DialogFragment {

        private final DatePickerDialog.OnDateSetListener listener;

        public DatePickerFragment(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }
    }


}