package cf.soisi.labor_01_lotto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView label;
    private RadioGroup radioGroup;
    private Button buttonGenerate;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        label = findViewById(R.id.seekBarNumber);
        radioGroup = findViewById(R.id.radioGroup);
        buttonGenerate = findViewById(R.id.button);
        result = findViewById(R.id.result);

        seekBar.setProgress(0);
        label.setText(String.valueOf(1));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                label.setText(String.valueOf(progress+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });



    }

    public void generate(View view) {
        int maxNumber = get45Or49();
        int numberOfTipps = seekBar.getProgress() + 1;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numberOfTipps; i++) {
            sb.append(String.format("Tipp %2d: ", i + 1));
            sb.append(getNumbers(maxNumber));
            sb.append("\n");
        }
        result.setText(sb.toString());
    }

    private String getNumbers(int maxNumber) {
        return IntStream.generate(() -> ThreadLocalRandom.current().nextInt(1, maxNumber + 1))
                .distinct()
                .limit(6)
                .sorted()
                .mapToObj(value -> String.format("%2d",value))
                .collect(Collectors.joining(" "));
    }

    private int get45Or49() {
        return radioGroup.getCheckedRadioButtonId() == R.id.radio_6_45 ? 45 : 49;
    }
}