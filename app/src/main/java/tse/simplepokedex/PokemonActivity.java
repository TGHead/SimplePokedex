package tse.simplepokedex;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by TGHead on 2017/12/10.
 */
public class PokemonActivity extends AppCompatActivity {

    private PokemonCard pokemonCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        Bundle bundle = getIntent().getExtras();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (bundle != null) {
            pokemonCard = (PokemonCard) bundle.getSerializable("pokemon");
            getSupportActionBar().setTitle(pokemonCard.getName());
//            Log.i("HELP","got stuff");
        }

        int hp = pokemonCard.getHp();
        ProgressBar barHp = (ProgressBar)findViewById(R.id.barHp);
        barHp.setProgress(statToPercentage(hp));
        TextView valueHp = (TextView)findViewById(R.id.valueHp);
        valueHp.setText(Integer.toString(hp));

        int attack = pokemonCard.getAttack();
        ProgressBar barAttack = (ProgressBar) findViewById(R.id.barAttack);
        barAttack.setProgress(statToPercentage(attack));
        TextView valueAttack = (TextView) findViewById(R.id.valueAttack);
        valueAttack.setText(Integer.toString(attack));

        int defense = pokemonCard.getDefense();
        ProgressBar barDefense = (ProgressBar) findViewById(R.id.barDefense);
        barDefense.setProgress(statToPercentage(defense));
        TextView valueDefense = (TextView) findViewById(R.id.valueDefense);
        valueDefense.setText(Integer.toString(defense));

        int specialAttack = pokemonCard.getSpecialAttack();
        ProgressBar barSpecialAttack = (ProgressBar) findViewById(R.id.barSpecialAttack);
        barSpecialAttack.setProgress(statToPercentage(specialAttack));
        TextView valueSpecialAttack = (TextView) findViewById(R.id.valueSpecialAttack);
        valueSpecialAttack.setText(Integer.toString(specialAttack));

        int specialDefense = pokemonCard.getSpecialDefense();
        ProgressBar barSpecialDefense = (ProgressBar) findViewById(R.id.barSpecialDefense);
        barSpecialDefense.setProgress(statToPercentage(specialDefense));
        TextView valueSpecialDefense = (TextView) findViewById(R.id.valueSpecialDefense);
        valueSpecialDefense.setText(Integer.toString(specialDefense));

        int speed = pokemonCard.getSpeed();
        Log.i("HELP","speed :"+Integer.toString(speed)+"//");
        ProgressBar barSpeed = (ProgressBar) findViewById(R.id.barSpeed);
        barSpeed.setProgress(statToPercentage(speed));
        TextView valueSpeed = (TextView) findViewById(R.id.valueSpeed);
        valueSpeed.setText(Integer.toString(speed));


        ImageView ivSprite = (ImageView) findViewById(R.id.pokemon_sprite);
        String uri = pokemonCard.getSprite();
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        ((ImageView) findViewById(R.id.pokemon_sprite)).setImageDrawable(res);
//        Picasso.with(getContext()).load(uriSprite).into(ivSprite);

        TextView textName = (TextView) findViewById(R.id.pokemon_name);
        textName.setText(pokemonCard.getName());
        textName = (TextView) findViewById(R.id.pokemon_height);
        textName.setText(pokemonCard.getHeight());
        textName = (TextView) findViewById(R.id.pokemon_weight);
        textName.setText(pokemonCard.getWeight() + " lbs");


        TextView textType1 = (TextView) findViewById(R.id.pokemon_type1);
        textType1.setBackgroundResource(pokemonCard.getTypes()[0].color());
        textType1.setText(pokemonCard.getTypes()[0].type());

        if (pokemonCard.getTypes()[1] != null) {
            TextView textType2 = (TextView) findViewById(R.id.pokemon_type2);
            textType2.setVisibility(View.VISIBLE);
            textType2.setBackgroundResource(pokemonCard.getTypes()[1].color());
            textType2.setText(pokemonCard.getTypes()[1].type());
        }

        TextView textDescription = (TextView) findViewById(R.id.pokemon_description);
        textDescription.setText(pokemonCard.getDescription());
        int[][] ids = new int[][]{
                { R.id.pokemon_evolution1_name, R.id.pokemon_evolution1_img, R.id.pokemon_evolution1 },
                { R.id.pokemon_evolution2_name, R.id.pokemon_evolution2_img, R.id.pokemon_evolution2 },
                { R.id.pokemon_evolution3_name, R.id.pokemon_evolution3_img, R.id.pokemon_evolution3 },
                { R.id.pokemon_evolution4_name, R.id.pokemon_evolution4_img, R.id.pokemon_evolution4 }
        };

        Map<Integer, String> evolutions = pokemonCard.getEvolutions();

//        LinearLayout pokemon_layout = (LinearLayout) findViewById(R.id.pokemon_evolution_layout);
//        pokemon_layout.setWeightSum(evolutions.size());
//        System.out.println(evolutions.size());
        int i = 0;
        for (Integer key : evolutions.keySet()) {
            TextView textEvolution = (TextView) findViewById(ids[i][0]);
            textEvolution.setVisibility(View.VISIBLE);
            textEvolution.setText(evolutions.get(key));
            ImageView ivEvolution = (ImageView) findViewById(ids[i][1]);
            ivEvolution.setVisibility(View.VISIBLE);
//            LinearLayout layout = (LinearLayout) findViewById(ids[i][2]);
//            layout.setWeightSum(evolutions.size());
//            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    1.0f
//            );
//            layout.setLayoutParams(param);
//            layout.setVisibility(View.VISIBLE);

            String uri_evo = "@drawable/p" + key;
            int imageResource_evo = getResources().getIdentifier(uri_evo, null, getPackageName());
            Drawable res_evo = getResources().getDrawable(imageResource_evo);
            ivEvolution.setImageDrawable(res_evo);
            i++;
        }
    }

    public static int statToPercentage(int stat) {
        return (100 * stat) / 255;
    }

}
