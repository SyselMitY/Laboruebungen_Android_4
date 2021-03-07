package cf.soisi.labor_02_2_recycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String KLASSE_EXTRA = "cf.soisi.labor_02_1_listview.main.extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Klassenliste");

        List<Klasse> klassen = Klasse.readKlassen(this.getResources().openRawResource(R.raw.schueler));
        RecyclerView rv = findViewById(R.id.rvKlasse);

        klassen.sort(null);
        rv.setLayoutManager(new LinearLayoutManager(this));
        RvKlasseAdapter adapter = new RvKlasseAdapter(klassen);
        adapter.setOnClickHandler(this::listenToClick);
        rv.setAdapter(adapter);
    }

    private void listenToClick(View view, Klasse klasse) {
        Intent intent = new Intent(this,SchuelerActivity.class);
        intent.putExtra(KLASSE_EXTRA,klasse);
        startActivity(intent);
    }
}