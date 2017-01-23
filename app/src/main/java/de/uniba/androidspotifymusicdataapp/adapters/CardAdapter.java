package de.uniba.androidspotifymusicdataapp.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.uniba.androidspotifymusicdataapp.model.CardAlbum;
import de.uniba.androidspotifymusicdataapp.R;


/**
 * Adapter class that works behind the scene to bind the data to the frontend
 * Created by chandan on 31/12/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{

    private List<CardAlbum> cardAlbumList;
    private LayoutInflater layoutInflater;
    private CardClickCallBack cardClickCallBack;
    private Context context;


    /**
     * Constructor for the Adapter class
     * @param cardAlbumList
     * @param context
     */
    public CardAdapter(List<CardAlbum> cardAlbumList, Context context) {
        this.cardAlbumList = cardAlbumList;
        this.context = context;
        this.layoutInflater = layoutInflater.from(context);
    }

    /**
     * Callback interface for the MainActivity CardView
     */
    public interface CardClickCallBack{
        public void onCardClick(int position);
        public void onCardButtonClick(int position);
    }

    /**
     * Setter method for the Callback Interface.
     * @param cardClickCallBack
     */
    public void setCardClickCallBack(CardClickCallBack cardClickCallBack) {
        this.cardClickCallBack = cardClickCallBack;
    }

    /**
     * Methods to be implemented to bind the RecyclerView with data and inflate
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.card_item,parent,false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        CardAlbum cardAlbum = cardAlbumList.get(position);
        //Picasso Experiment Begin
        Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load(cardAlbum.getAlbumImageURL()).into(holder.imageView_Avatar);
        //Experiment Experiment End
        holder.textView_albumName.setText(cardAlbum.getAlbumName());
        holder.textView_artistName.setText(cardAlbum.getArtistName());
        holder.button_aboutArtist.setText("About "+cardAlbum.getArtistName());
    }

    @Override
    public int getItemCount() {
        return cardAlbumList.size();
    }


    /**
     * Class CardViewHolder is the ViewHolder class for the CardAdapter
     */
    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        private ImageView imageView_Avatar;
        private TextView textView_albumName;
        private TextView textView_artistName;
        private Button button_aboutArtist;
        private View viewContainer;

        /**
         * Constructor for the ViewHolder class. Inside ths class we would specify the layout elements
         * @param itemView
         */
        public CardViewHolder(View itemView) {
            super(itemView);

            imageView_Avatar = (ImageView)itemView.findViewById(R.id.imageView_for_album_photo);
            textView_albumName = (TextView)itemView.findViewById(R.id.textView_for_album_name);
            textView_artistName = (TextView)itemView.findViewById(R.id.textView_for_artist_names);
            button_aboutArtist = (Button)itemView.findViewById(R.id.button_for_about_artist);
            button_aboutArtist.setOnClickListener(this);
            viewContainer = itemView.findViewById(R.id.cardview_element);
            viewContainer.setOnClickListener(this);
        }

        /**
         * Overridden onClick method for the MainActivity CardView
         * @param view
         */
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.button_for_about_artist){
                cardClickCallBack.onCardButtonClick(getAdapterPosition());
            }else{
                cardClickCallBack.onCardClick(getAdapterPosition());
            }
        }
    }
}
