package com.udacity.sandwichclub.utils;

import android.support.annotation.NonNull;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME_KEY = "name";
    private static final String MAIN_NAME_KEY = "mainName";
    private static final String AKA_NAME_KEY = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    private static final String DESCRIPTION_KEY = "description";
    private static final String IMAGE_KEY = "image";
    private static final String INGREDIENTS_KEY = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;

        try {
            final JSONObject sandwichJson = new JSONObject(json);

            final JSONObject nameJson = sandwichJson.getJSONObject(NAME_KEY);
            final String mainName = nameJson.getString(MAIN_NAME_KEY);
            final List<String> aka = jsonArrayToStringList(nameJson.getJSONArray(AKA_NAME_KEY));

            final String placeOfOrigin = sandwichJson.getString(PLACE_OF_ORIGIN_KEY);
            final String description = sandwichJson.getString(DESCRIPTION_KEY);
            final String image = sandwichJson.getString(IMAGE_KEY);

            final JSONArray ingredientsJson = sandwichJson.getJSONArray(INGREDIENTS_KEY);
            final List<String> ingredients = jsonArrayToStringList(ingredientsJson);

            sandwich = new Sandwich(mainName, aka, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    @NonNull
    private static ArrayList<String> jsonArrayToStringList(final JSONArray jsonArray) {
        ArrayList<String> stringList = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                stringList.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return stringList;
    }
}
