package de.uniba.androidspotifymusicdataapp.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import de.uniba.androidspotifymusicdataapp.model.CardItem;
import de.uniba.androidspotifymusicdataapp.R;


/**
 * Adapter class that works behind the scene to bind the data to the frontend
 * Created by chandan on 31/12/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{

    private List<CardItem> cardItemList;
    private LayoutInflater layoutInflater;
    private CardClickCallBack cardClickCallBack;


    /**
     * Constructor for the Adapter class
     * @param cardItemList
     * @param context
     */
    public CardAdapter(List<CardItem> cardItemList, Context context) {
        this.cardItemList = cardItemList;
        this.layoutInflater = layoutInflater.from(context);
    }

    /**
     * Callback interface
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
     * Need to implement methods
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
        CardItem cardItem = cardItemList.get(position);
        holder.textView_personNamne.setText(cardItem.getPersonName());
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }


    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        private ImageView imageView_Avatar;
        private TextView textView_personNamne;
        private Button button_learnMore;
        private View viewContainer;

        /**
         * Constructor for the ViewHolder class. Inside ths class we would specify the layout elements
         * @param itemView
         */
        public CardViewHolder(View itemView) {
            super(itemView);

            imageView_Avatar = (ImageView)itemView.findViewById(R.id.imageView_for_photo);
            textView_personNamne = (TextView)itemView.findViewById(R.id.textView_for_personName);
            button_learnMore = (Button)itemView.findViewById(R.id.button_for_learn_more);
            button_learnMore.setOnClickListener(this);
            viewContainer = itemView.findViewById(R.id.container_for_cardview);
            viewContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.button_for_learn_more){
                cardClickCallBack.onCardButtonClick(getAdapterPosition());
            }else{
                cardClickCallBack.onCardClick(getAdapterPosition());
            }
        }
    }
}
