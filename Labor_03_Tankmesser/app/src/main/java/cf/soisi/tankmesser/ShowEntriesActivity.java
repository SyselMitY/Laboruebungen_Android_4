package cf.soisi.tankmesser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import cf.soisi.tankmesser.db.Tankvorgang;
import cf.soisi.tankmesser.db.TankvorgangDataSource;

public class ShowEntriesActivity extends AppCompatActivity {

    private List<Tankvorgang> tankvorgangList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TankvorgangDataSource dataSource = null;
        try {
            dataSource = new TankvorgangDataSource(this);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_entries);
            dataSource.open();

            tankvorgangList = dataSource.getAll();
            RvTankvorgangAdapter adapter = new RvTankvorgangAdapter(tankvorgangList);
            adapter.setOnClickHandler(this::listenOnItemClick);

            RecyclerView rv = findViewById(R.id.rv_tankvorgang);
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setAdapter(adapter);
        } finally {
            if (dataSource != null) {
                dataSource.close();
            }
        }
    }

    private void listenOnItemClick(View view, Tankvorgang tankvorgang) {
        Toast.makeText(
                this,
                String.format(Locale.GERMAN, "%.2f l/100km", getMileage(tankvorgang)),
                Toast.LENGTH_SHORT)
                .show();
    }

    private double getMileage(Tankvorgang tankvorgang) {
        return tankvorgang.getAmount() / (tankvorgang.getKmNew() - tankvorgang.getKmOld())*100;
    }
}