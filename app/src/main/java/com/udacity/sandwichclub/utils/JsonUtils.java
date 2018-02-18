package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        if (json == null || TextUtils.isEmpty(json)) {
            return null;
        }

        Sandwich sandwichObject = new Sandwich();
        try {
            JSONObject sandwichJson = new JSONObject(json);

            // Set the main name
            JSONObject sandwichName = sandwichJson.getJSONObject("name");
            sandwichObject.setMainName(sandwichName.getString("mainName"));

            JSONArray alsoKnownAsJSONArray = sandwichName.getJSONArray("alsoKnownAs");
            // Make list of names
            ArrayList<String> alsoKnownAsList = new ArrayList<String>();
            for (int i = 0; i < alsoKnownAsJSONArray.length(); i++) {
                alsoKnownAsList.add(alsoKnownAsJSONArray.getString(i));
            }
            // Add list of names to the object
            sandwichObject.setAlsoKnownAs(alsoKnownAsList);

            // Set place of origin
            sandwichObject.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));
            // Set description
            sandwichObject.setDescription(sandwichJson.getString("description"));
            // Set URL to image
            sandwichObject.setImage(sandwichJson.getString("image"));

            JSONArray ingredientsJSONArray = sandwichJson.getJSONArray("ingredients");
            // Make list of ingredients
            ArrayList<String> ingredientsList = new ArrayList<String>();
            for (int i = 0; i < ingredientsJSONArray.length(); i++) {
                ingredientsList.add(ingredientsJSONArray.getString(i));
            }
            // Add ingredients to the object
            sandwichObject.setIngredients(ingredientsList);
        } catch (JSONException e) {
            Log.d(JsonUtils.class.getSimpleName(), Log.getStackTraceString(new Exception()));
        }

        return sandwichObject;
    }
}
