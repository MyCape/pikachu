package com.pp.pikachu.ui.activities.home.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.pp.pikachu.R;
import com.pp.pikachu.models.Airports;
import com.pp.pikachu.ui.activities.home.HomeMapPresenter;
import com.pp.pikachu.ui.utils.OnBindViewListener;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by bry1337 on 22/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public class HomeListFragmentAdapter extends RecyclerView.Adapter<HomeListFragmentAdapter.ViewHolder> {

  private final Context context;
  private List<Airports> airportsList;
  private HomeMapPresenter listener;
  private int lasPos;

  public HomeListFragmentAdapter(Context context, List<Airports> airportsList, HomeMapPresenter listener) {
    this.context = context;
    this.airportsList = airportsList;
    this.listener = listener;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_airport_detail, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Airports airports = airportsList.get(position);
    holder.onBind(airports);
    handleFavorite(holder, airports, position);
  }

  private void handleFavorite(ViewHolder holder, Airports airports, int pos) {
    if (listener.getFavorite() != null && StringUtils.isNotEmpty(listener.getFavorite())) {
      if (listener.getFavorite().equals(airports.getName())) {
        holder.setAirportFavorite();
        lasPos = pos;
      }
    }
  }

  @Override public int getItemCount() {
    return airportsList.size();
  }

  @Override public int getItemViewType(int position) {
    return position;
  }

  @Override public long getItemId(int position) {
    return position;
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements OnBindViewListener {

    @BindView(R.id.tvAirportName) TextView tvAirportName;

    View itemView;

    public ViewHolder(View itemView) {
      super(itemView);
      this.itemView = itemView;
      ButterKnife.bind(this, itemView);
    }

    @Override public void onBind(Object object) {
      Airports airports = (Airports) object;

      displayAirportData(airports);

      itemView.setOnClickListener(view -> {
        processAlertDialog(airports);
      });
    }

    private void displayAirportData(Airports airports) {
      if (airports != null) {
        tvAirportName.setText(airports.getName());
      }
    }

    private void processAlertDialog(Airports airports) {

      AlertDialog alertDialog = new AlertDialog.Builder(context).create();
      alertDialog.setMessage(context.getString(R.string.what_would_you_like));
      alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
          String.format("%s %s", context.getString(R.string.look_for), airports.getName()),
          (dialog, which) -> listener.onItemClick(airports));
      if (!airports.getName().equals(listener.getFavorite())) {
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.save_this_airport),
            (dialog, which) -> handleSaveToFavorite(airports));
      } else {
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.remove_this_airport),
            (dialog, which) -> handleRemoveToFavorite());
      }
      alertDialog.show();
    }

    public void setAirportFavorite() {
      itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
    }

    public void removeAirportFavorite() {
      itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
      notifyItemChanged(lasPos);
      lasPos = getAdapterPosition();
    }

    private void handleSaveToFavorite(Airports airports) {
      listener.onRemove(getAdapterPosition());
      notifyItemChanged(getAdapterPosition());
      listener.onSave(airports.getName(), getAdapterPosition());
      if (lasPos != getAdapterPosition()) {
        removeAirportFavorite();
      }
    }

    private void handleRemoveToFavorite() {
      itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
      listener.onRemove(getAdapterPosition());
    }
  }
}
