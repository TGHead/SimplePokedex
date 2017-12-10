package tse.simplepokedex;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView RecyclerView;
    private GridLayoutManager GridLayoutManager;
    private PokemonCardAdapter PokedexAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            GridLayoutManager = new GridLayoutManager(this, 3);
        }
        else { GridLayoutManager = new GridLayoutManager(this, 4); }

        RecyclerView.setLayoutManager(GridLayoutManager);
        RecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        try {
            new RequestPokemonCardsTask().execute();
        }

        catch(Exception e) {
        }
    }

    private class RequestPokemonCardsTask extends AsyncTask<Void, Integer, List<PokemonCard>> {

        List<PokemonCard> pokemonCardList = new ArrayList<>();

        protected List<PokemonCard> doInBackground(Void... params) {

            try {
                JSONArray pokemonCardArray = new JSONArray(loadJSONFromAsset());

                for (int i=0; i< pokemonCardArray.length(); i++) {
                    JSONObject pokemonCard = (JSONObject) pokemonCardArray.get(i);

                    int id = pokemonCard.getInt("id");
                    int hp = pokemonCard.getInt("hp");
                    int attack = pokemonCard.getInt("attack");
                    int defense = pokemonCard.getInt("defense");
                    int specialAttack = pokemonCard.getInt("special_attack");
                    int specialDefense = pokemonCard.getInt("special_defense");
                    int speed = pokemonCard.getInt("speed");
                    String name = pokemonCard.getString("name");
                    String description = pokemonCard.getString("description");
                    String sprite = "@drawable/p"+id;

//                    JSONArray movesJson = pokemonCard.getJSONArray("moves");
//                    List<Move> moves = new ArrayList<>();
//                    for (int j = 0; j < movesJson.length(); j++) {
//                        JSONObject moveJson = (JSONObject) movesJson.get(j);
//                        moves.add(new Move(
//                                moveJson.getString("name"),
//                                Integer.toString(moveJson.getInt("method")),
//                                Integer.valueOf(moveJson.getString("power").equals("null") ? "0" : moveJson.getString("power")),
//                                Integer.valueOf(moveJson.getString("accuracy").equals("null") ? "0": moveJson.getString("accuracy")),
//                                Integer.valueOf(moveJson.getString("pp").equals("null") ? "0" : moveJson.getString("pp")),
//                                Type.valueOf(moveJson.getString("type")),
//                                Move.Category.valueOf(moveJson.getString("category"))
//                        ));
//                    }

                    JSONArray evolutionsJson = pokemonCard.getJSONArray("evolutions");
                    Map<Integer, String> evolutions = new HashMap<>();
                    for (int k = 0; k < evolutionsJson.length(); k++) {
                        JSONObject evolutionJson = (JSONObject) evolutionsJson.get(k);
                        evolutions.put(
                                Integer.valueOf(evolutionJson.getString("id")),
                                evolutionJson.getString("name")
                        );
                    }

                    JSONArray typesJson = pokemonCard.getJSONArray("types");
                    Type[] types;
                    if(typesJson.length() > 1) {
                        types = new Type[]{ Type.valueOf(typesJson.getString(0)), Type.valueOf(typesJson.getString(1)) };
                    } else {
                        types = new Type[]{ Type.valueOf(typesJson.getString(0)), null };
                    }

//                    pokemonCardList.add(new PokemonCard(id, hp, attack, defense, specialAttack, specialDefense, speed, name, description, types, sprite, moves, evolutions));
                    pokemonCardList.add(new PokemonCard(id, hp, attack, defense, specialAttack, specialDefense, speed, name, description, types, sprite, evolutions));
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return pokemonCardList;
        }

        @Override
        protected void onPostExecute(List<PokemonCard> pokemonCardList) {
            super.onPostExecute(pokemonCardList);
            PokedexAdapter = new PokemonCardAdapter(pokemonCardList);
            RecyclerView.setAdapter(PokedexAdapter);
        }

        public String loadJSONFromAsset() {

            String json = null;
            try {
                InputStream is = getAssets().open("pokedex.json");

                int size = is.available();
                byte[] buffer = new byte[size];

                is.read(buffer);
                is.close();

                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }
    }
}
