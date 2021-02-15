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

public class RvKlasseAdapter extends RecyclerView.Adapter<RvKlasseAdapter.KlasseViewHolder> {

    private List<Klasse> klassen;
    private BiConsumer<View,Klasse> onClickHandler;

    public RvKlasseAdapter(List<Klasse> klassen) {
        this.klassen = klassen;
    }

    public void setOnClickHandler(BiConsumer<View,Klasse> onClickHandler) {
        this.onClickHandler = onClickHandler;
    }

    public List<Klasse> getKlassen() {
        return klassen;
    }

    @NonNull
    @Override
    public KlasseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_klasse, parent, false);
        return new KlasseViewHolder(v, onClickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull KlasseViewHolder holder, int position) {
        holder.setKlasse(klassen.get(position));
    }

    @Override
    public int getItemCount() {
        return klassen.size();
    }

    public static class KlasseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        private TextView klasseName;
        private TextView klasseAnzahl;
        private BiConsumer<View,Klasse> clickHandler;
        private Klasse klasse;

        public KlasseViewHolder(View itemView, BiConsumer<View, Klasse> clickHandler) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cvSchueler);
            klasseName = itemView.findViewById(R.id.klasseName);
            klasseAnzahl = itemView.findViewById(R.id.klasseAnzahl);
            this.clickHandler = clickHandler;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickHandler.accept(v,klasse);
        }

        public CardView getCardView() {
            return cardView;
        }

        public void setKlasse(Klasse klasse) {
            this.klasse = klasse;
            klasseName.setText(klasse.getName());
            klasseAnzahl.setText(String.format("(%d)",klasse.getSchueler().size()));
        }
    }
}
