package cf.soisi.labor_02_1_listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.soisi.labor_02_1_listview.R;

import java.util.ArrayList;
import java.util.List;

public class SchuelerListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schueler_list);

        ListView lv = findViewById(R.id.listViewSchueler);
        Klasse klasse = (Klasse) getIntent().getSerializableExtra(MainActivity.KLASSE_EXTRA);
        List<Schueler> schueler = new ArrayList<>(klasse.getSchueler());
        schueler.sort(null);
        ArrayAdapter<Schueler> mAdapter = new ArrayAdapter<Schueler>(this, android.R.layout.simple_list_item_1, schueler);

        lv.setAdapter(mAdapter);
        this.setTitle("Klasse " + klasse.getName());
    }
}