package com.imran.btcinsightsdaily;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class BlogDetails extends AppCompatActivity {

    TextView title, description;
    ImageView imageCover;
    FloatingActionButton fabButton;

    public static String TITLE = "";
    public static String DESCRIPTION = "";
    public static Bitmap MY_BITMAP = null;

    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_blog_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        imageCover = findViewById(R.id.imageCover);
        fabButton = findViewById(R.id.fabButton);

        fabButton.setVisibility(View.GONE);

        title.setText(TITLE);
        description.setText(DESCRIPTION);
        if (MY_BITMAP != null) imageCover.setImageBitmap(MY_BITMAP);


        //Initialize text to speech

        textToSpeech = new TextToSpeech(BlogDetails.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    fabButton.setVisibility(View.VISIBLE);
                } else {
                    fabButton.setVisibility(View.GONE);
                }
            }
        });




        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //speak the description
                textToSpeech.setLanguage(Locale.US);
                textToSpeech.setSpeechRate(0.6f);
                textToSpeech.setPitch(1f);
                String text = description.getText().toString();
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null,null);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        textToSpeech.shutdown();
        //destroy activity
        finish();
    }
}