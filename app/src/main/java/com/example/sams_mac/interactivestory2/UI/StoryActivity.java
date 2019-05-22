package com.example.sams_mac.interactivestory2.UI;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sams_mac.interactivestory2.R;
import com.example.sams_mac.interactivestory2.model.Page;
import com.example.sams_mac.interactivestory2.model.Story;

public class StoryActivity extends AppCompatActivity {

    public static final String TAG = StoryActivity.class.getSimpleName();

    private String name;
    private Story story;
    private ImageView storyImageView;
    private TextView storyTextView;
    private Button choice1Button;
    private Button choice2Button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyImageView =(ImageView)findViewById(R.id.storyImageView);
        storyTextView = (TextView)findViewById(R.id.storyTextView);
        choice1Button = (Button)findViewById(R.id.choice1Button);
        choice2Button = (Button)findViewById(R.id.choice2Button);


        Intent intent = getIntent();
        name = intent.getStringExtra(getString(R.string.key_name));
        if (name == null || name.isEmpty()) {
            name = "friend";

        }
        Log.d(TAG, name);

        story = new Story();
        loadPage(0);

    }

    // how to load page
    private void loadPage(int pageNumber) {
        final Page page = story.getPage(pageNumber);

        Drawable image = ContextCompat.getDrawable(this, page.getImageId());
        storyImageView.setImageDrawable(image);

        String pageText = getString(page.getTextId());
        // Add name if place holder is included. wont add if not.
        pageText = String.format(pageText, name);
        storyTextView.setText(pageText);

        if (page.isFinalPage()){
            choice1Button.setVisibility(View.INVISIBLE);
            choice2Button.setText(R.string.playAgainButtonText);
            choice2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // finish();
                    loadPage(0);
                }
            });

        }
        else {
            loadButtons(page);
        }
    }

    private void loadButtons(final Page page) {
        choice1Button.setVisibility(View.VISIBLE);
        choice1Button.setText(page.getChoice1().getTextId());
        choice1Button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int nextPage = page.getChoice1().getNextPage();
            loadPage(nextPage);
        }
        });

        choice2Button.setVisibility(View.VISIBLE);
        choice2Button.setText(page.getChoice2().getTextId());
        choice2Button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int nextPage = page.getChoice2().getNextPage();
            loadPage(nextPage);
        }
         });
    }
}










