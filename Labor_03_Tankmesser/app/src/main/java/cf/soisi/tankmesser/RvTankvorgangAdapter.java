package cf.soisi.tankmesser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;

import cf.soisi.tankmesser.db.Tankvorgang;

public class RvTankvorgangAdapter extends RecyclerView.Adapter<RvTankvorgangAdapter.TankvorgangViewHolder> {

    private List<Tankvorgang> tankvorgangList;
    private BiConsumer<View, Tankvorgang> onClickHandler;

    public RvTankvorgangAdapter(List<Tankvorgang> tankvorgangList) {
        this.tankvorgangList = tankvorgangList;
    }

    public void setOnClickHandler(BiConsumer<View, Tankvorgang> onClickHandler) {
        this.onClickHandler = onClickHandler;
    }

    public List<Tankvorgang> getTankvorgangn() {
        return tankvorgangList;
    }

    @NonNull
    @Override
    public TankvorgangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_entry, parent, false);
        return new TankvorgangViewHolder(v, onClickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull TankvorgangViewHolder holder, int position) {
        holder.setTankvorgang(tankvorgangList.get(position));
    }

    @Override
    public int getItemCount() {
        return tankvorgangList.size();
    }

    public static class TankvorgangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CardView cardView;
        private final TextView textViewDate;
        private final TextView textViewKmOld;
        private final TextView textViewKmNew;
        private final TextView textViewAmount;
        private final TextView textViewPrice;
        private final BiConsumer<View, Tankvorgang> clickHandler;
        private Tankvorgang tankvorgang;

        public TankvorgangViewHolder(View itemView, BiConsumer<View, Tankvorgang> clickHandler) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewKmOld = itemView.findViewById(R.id.textViewKmOld);
            textViewKmNew = itemView.findViewById(R.id.textViewKmNew);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            this.clickHandler = clickHandler;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickHandler != null) {
                clickHandler.accept(v, tankvorgang);
            }
        }

        public void setTankvorgang(Tankvorgang tankvorgang) {
            this.tankvorgang = tankvorgang;
            textViewDate.setText(tankvorgang.getDate().format(DateTimeFormatter.ofPattern("dd. MMMM uuuu")));
            textViewKmOld.setText(String.format(Locale.GERMAN,"%dkm", tankvorgang.getKmOld()));
            textViewKmNew.setText(String.format(Locale.GERMAN,"%dkm", tankvorgang.getKmNew()));
            textViewAmount.setText(String.format("%sl", tankvorgang.getAmount()));
            textViewPrice.setText(String.format("%s€/l", tankvorgang.getPrice()));
        }
    }
}