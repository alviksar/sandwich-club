package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich mSandwich;

    // Sandwich detail TextViews
    private TextView mAlsoKnownAs;
    private TextView mIngredients;
    private TextView mPlaceOfOrigin;
    private TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mAlsoKnownAs = findViewById(R.id.also_known_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        mPlaceOfOrigin = findViewById(R.id.origin_tv);
        mDescription = findViewById(R.id.description_tv);

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
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private String populateFromList(List<String> list) {

        StringBuilder result = new StringBuilder();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                result.append(list.get(i));
                if (i < list.size() - 1) {
                    result.append(", ");
                } else {
                    if (list.size() > 1) result.append(".");
                }
            }
        }
        return result.toString();
    }

    private void populateUI() {

        if (mSandwich == null) {
            closeOnError();
            return;
        }

        mPlaceOfOrigin.setText(mSandwich.getPlaceOfOrigin());
        mDescription.setText(mSandwich.getDescription());

        // Make list of names
        String alsoKnownAs = populateFromList(mSandwich.getAlsoKnownAs());
        if (TextUtils.isEmpty(alsoKnownAs)) {
            mAlsoKnownAs.setVisibility(View.GONE);
            findViewById(R.id.also_known_label_tv).setVisibility(View.GONE);

        } else {
            mAlsoKnownAs.setText(alsoKnownAs);
        }

        // Make list of ingredients
        mIngredients.setText(populateFromList(mSandwich.getIngredients()));
    }
}
