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

    private static final String FALLBACK = "Information not available";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;

        try {
            final JSONObject sandwichJson = new JSONObject(json);

            final JSONObject nameJson = sandwichJson.optJSONObject(NAME_KEY);
            final String mainName = nameJson.optString(MAIN_NAME_KEY, FALLBACK);
            final List<String> aka = jsonArrayToStringList(nameJson.optJSONArray(AKA_NAME_KEY));

            final String placeOfOrigin = sandwichJson.optString(PLACE_OF_ORIGIN_KEY, FALLBACK);
            final String description = sandwichJson.optString(DESCRIPTION_KEY, FALLBACK);
            final String image = sandwichJson.optString(IMAGE_KEY, FALLBACK);

            final JSONArray ingredientsJson = sandwichJson.optJSONArray(INGREDIENTS_KEY);
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
