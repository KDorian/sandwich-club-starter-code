package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich parsedSandwich;

        try {
            JSONObject sandwichJSON = new JSONObject(json);

            JSONObject sandwichNameJSON = sandwichJSON.getJSONObject("name");
            String sandwichMainName = sandwichNameJSON.getString("mainName");

            JSONArray sandwichAlsoKnownAsJSON = sandwichNameJSON.getJSONArray("alsoKnownAs");
            List<String> sandwichAlsoKnownAs = new ArrayList<>();
            for (int i = 0; i < sandwichAlsoKnownAsJSON.length(); i++) {
                String alternativeSandwichName = sandwichAlsoKnownAsJSON.getString(i);
                sandwichAlsoKnownAs.add(alternativeSandwichName);
            }

            String sandwichOrigin = sandwichJSON.getString("placeOfOrigin");

            String sandwichDescription = sandwichJSON.getString("description");

            String sandwichImage = sandwichJSON.getString("image");

            JSONArray sandwichIngredientsJSON = sandwichJSON.getJSONArray("ingredients");
            List<String> sandwichIngredients = new ArrayList<>();
            for (int i = 0; i < sandwichIngredientsJSON.length(); i++) {
                String ingredient = sandwichIngredientsJSON.getString(i);
                sandwichIngredients.add(ingredient);
            }
            
            parsedSandwich =
                    new Sandwich(sandwichMainName,
                            sandwichAlsoKnownAs,
                            sandwichOrigin,
                            sandwichDescription,
                            sandwichImage,
                            sandwichIngredients);

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }

        return parsedSandwich;
        }

    }
}
