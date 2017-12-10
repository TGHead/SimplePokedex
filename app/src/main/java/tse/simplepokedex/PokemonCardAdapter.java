package tse.simplepokedex;

/**
 * Created by TGHead on 2017/12/9.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class PokemonCardAdapter extends  RecyclerView.Adapter<PokemonCardAdapter.PokemonCardViewHolder> {

    //base list for search (filter(""))
    private List<PokemonCard> pokemonCardList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class PokemonCardViewHolder extends RecyclerView.ViewHolder {
        CardView pokemonCard;
        ImageView sprite;
        TextView pokemonName;
        View primaryTypeColor;
        View secondaryTypeColor;

        PokemonCardViewHolder(final View itemView) {
            super(itemView);
            pokemonCard = (CardView) itemView.findViewById(R.id.pokemon_card);
            pokemonName = (TextView) itemView.findViewById(R.id.pokemon_name);
            sprite = (ImageView) itemView.findViewById(R.id.sprite);
            primaryTypeColor = itemView.findViewById(R.id.primary_type_color);
            secondaryTypeColor = itemView.findViewById(R.id.secondary_type_color);
        }
    }

    public PokemonCardAdapter(List<PokemonCard> pokemonCardList) {
        this.pokemonCardList = pokemonCardList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PokemonCardViewHolder onCreateViewHolder(ViewGroup parent, final int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_card, parent, false);
        PokemonCardViewHolder pcvh = new PokemonCardViewHolder(v);

        return pcvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    // used when contents come into view
    // card info relative to position is treated here
    @Override
    public void onBindViewHolder(final PokemonCardViewHolder pokemonHolder, final int position) {
        String name = pokemonCardList.get(position).getName();
        pokemonHolder.pokemonName.setText(name.substring(0,1).toUpperCase() + name.substring(1));

        Type[] types = pokemonCardList.get(position).getTypes();

        String primaryType = types[0].type();
        Integer primaryColorResource = Type.valueOf(primaryType).color();
        pokemonHolder.primaryTypeColor.setBackgroundResource(primaryColorResource);

        if (types[1] != null) {
            String secondaryType = types[1].type();
            Integer secondaryColorResource = Type.valueOf(secondaryType).color();
            pokemonHolder.secondaryTypeColor.setBackgroundResource(secondaryColorResource);
        } else {
            pokemonHolder.secondaryTypeColor.setBackgroundResource(primaryColorResource);
        }

        String uri = pokemonCardList.get(position).getSprite();
        Context context = pokemonHolder.sprite.getContext();
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);
        pokemonHolder.sprite.setImageDrawable(res);

        pokemonHolder.pokemonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                //Toast.makeText(context,.getName(),Toast.LENGTH_SHORT).show();
                //handle click
                PokemonCard clicked = pokemonCardList.get(pokemonHolder.getAdapterPosition());
                Intent i = new Intent();
                Bundle b = new Bundle();
                b.putSerializable("pokemon", clicked);
                i.putExtras(b);
                i.setClass(context, PokemonActivity.class);
                context.startActivity(i);
            }
        });
        /*pokemonHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Toast.makeText(context,Integer.toString(pokemonHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                Log.i("HELP","onCreateviewholder");
                Log.i("HELP",Integer.toString(position));

                /*Intent pokeIntent = new Intent(context, PokemonActivity.class);
                v.getChil


            }
        });*/
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pokemonCardList.size();
    }

}