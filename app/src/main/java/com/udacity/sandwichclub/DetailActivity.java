package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Iterator;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";

    private static final int DEFAULT_POSITION = -1;
    private static final String DELIMITER = ", ";
    private static final String LAST_DELIMITER = " and ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

        setTitle(sandwich.getMainName());
        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        final ImageView sandwichImageIv = findViewById(R.id.image_iv);
        final TextView placeOfOriginTv = findViewById(R.id.origin_tv);
        final TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
        final TextView descriptionTv = findViewById(R.id.description_tv);
        final TextView ingredientsTv = findViewById(R.id.ingredients_tv);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichImageIv);
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        descriptionTv.setText(sandwich.getDescription());
        populateTextFromList(alsoKnownAsTv, sandwich.getAlsoKnownAs());
        populateTextFromList(ingredientsTv, sandwich.getIngredients());
    }

    private void populateTextFromList(TextView textView, List<String> stringList) {
        if (stringList.isEmpty()) {
            return;
        }

        if (stringList.size() > 1) {
            textView.append(makeStringFromList(stringList));
        } else {
            textView.append(stringList.get(0));
        }
    }

    private String makeStringFromList(List<String> stringList) {
        final String lastString = LAST_DELIMITER + stringList.get(stringList.size() - 1);
        final StringBuilder formattedText = new StringBuilder();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formattedText.append(String.join(DELIMITER, stringList));
            formattedText.insert(formattedText.lastIndexOf(DELIMITER), lastString);
            formattedText.delete(formattedText.lastIndexOf(DELIMITER), formattedText.length());
        } else {
            final Iterator<String> itemIterator = stringList.iterator();
            String nextItem;

            while (itemIterator.hasNext()) {
                nextItem = itemIterator.next();
                if (!itemIterator.hasNext()) {
                    formattedText.append(nextItem);
                } else {
                    formattedText.append(nextItem).append(DELIMITER);
                }
            }

            formattedText.insert(formattedText.lastIndexOf(DELIMITER), lastString);
            formattedText.delete(formattedText.lastIndexOf(DELIMITER), formattedText.length());
        }

        return formattedText.toString();
    }
}
