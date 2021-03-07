package cf.soisi.labor_02_2_recycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SchuelerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schueler);

        Klasse klasse = (Klasse) getIntent().getSerializableExtra(MainActivity.KLASSE_EXTRA);
        setTitle(klasse.getName());
        RecyclerView rv = findViewById(R.id.rvSchueler);
        List<Schueler> schueler = new ArrayList<>(klasse.getSchueler());
        schueler.sort(null);

        rv.setLayoutManager(new LinearLayoutManager(this));
        RvSchuelerAdapter adapter = new RvSchuelerAdapter(schueler);
        rv.setAdapter(adapter);
    }
}