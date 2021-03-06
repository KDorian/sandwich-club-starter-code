package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView alsoKnownAsTv;
    private TextView placeOfOriginTv;
    private TextView descriptionTv;
    private TextView ingredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //Adding description to the UI
        String sandwichDescription = sandwich.getDescription();
        descriptionTv.setText(sandwichDescription);

        //Adding place of origin to the UI
        String sandwichPlaceOfOrigin = sandwich.getPlaceOfOrigin();
        placeOfOriginTv.setText(sandwichPlaceOfOrigin);

        //Adding other names of sandwich to the UI
        List<String> sandwichAlsoKnownAsStrings = sandwich.getAlsoKnownAs();
        if (!sandwichAlsoKnownAsStrings.isEmpty()) {
            StringBuilder sandwichAlsoKnownAsStringBuilder = new StringBuilder();
            for (String sandwichAlsoKnowAs : sandwichAlsoKnownAsStrings) {
                sandwichAlsoKnownAsStringBuilder.append(sandwichAlsoKnowAs);
                if (sandwichAlsoKnownAsStrings.size() > 1) {
                    sandwichAlsoKnownAsStringBuilder.append("\n");
                }
            }
            alsoKnownAsTv.setText(sandwichAlsoKnownAsStringBuilder.toString());
        }

        //Adding ingredients to the UI
        List<String> sandwichIngredientStrings = sandwich.getIngredients();
        if (!sandwichIngredientStrings.isEmpty()) {
            StringBuilder sandwichIngredientsStringBuilder = new StringBuilder();
            for (String sandwichIngredients : sandwichIngredientStrings) {
                sandwichIngredientsStringBuilder.append(sandwichIngredients);
                sandwichIngredientsStringBuilder.append("\n");
            }
            ingredientsTv.setText(sandwichIngredientsStringBuilder.toString());
        }
    }
}