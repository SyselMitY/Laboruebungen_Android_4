package cf.soisi.labor_02_2_recycle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.BiConsumer;

public class RvSchuelerAdapter extends RecyclerView.Adapter<RvSchuelerAdapter.SchuelerViewHolder> {

    private List<Schueler> schueler;
    private BiConsumer<View, Schueler> onClickHandler;

    public RvSchuelerAdapter(List<Schueler> schueler) {
        this.schueler = schueler;
    }

    public void setOnClickHandler(BiConsumer<View, Schueler> onClickHandler) {
        this.onClickHandler = onClickHandler;
    }

    public List<Schueler> getSchuelern() {
        return schueler;
    }

    @NonNull
    @Override
    public SchuelerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_schueler, parent, false);
        return new SchuelerViewHolder(v, onClickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull SchuelerViewHolder holder, int position) {
        holder.setSchueler(schueler.get(position));
    }

    @Override
    public int getItemCount() {
        return schueler.size();
    }

    public static class SchuelerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        private TextView schuelerName;
        private TextView schuelerGeschlecht;
        private TextView schuelerKatnr;
        private BiConsumer<View, Schueler> clickHandler;
        private Schueler schueler;

        public SchuelerViewHolder(View itemView, BiConsumer<View, Schueler> clickHandler) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cvSchueler);
            schuelerName = itemView.findViewById(R.id.schuelerName);
            schuelerGeschlecht = itemView.findViewById(R.id.schuelerGeschlecht);
            schuelerKatnr = itemView.findViewById(R.id.schuelerKatnr);
            this.clickHandler = clickHandler;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickHandler != null) {
                clickHandler.accept(v, schueler);
            }
        }

        public CardView getCardView() {
            return cardView;
        }

        public void setSchueler(Schueler schueler) {
            this.schueler = schueler;
            schuelerName.setText(String.format("%s %s", schueler.getNachname(), schueler.getVorname()));
            schuelerGeschlecht.setText(String.valueOf(schueler.getGeschlecht()));
            schuelerKatnr.setText(String.valueOf(schueler.getNummer()));
        }
    }
}
