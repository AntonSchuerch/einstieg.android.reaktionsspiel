package com.example.reaktionsspiel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class FinishedDialog {
    private Context context;
    private Button startButton;

    public FinishedDialog(Context context, Button startButton) {
        this.context = context;
        this.startButton = startButton;
    }

    public void showDialog(GameActivity activity) {
        // Create a dialog
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);

        // Create a LinearLayout to serve as the container
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(170, 50, 170, 200);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        layout.setBackgroundColor(Color.WHITE);

        layout.setOnClickListener(l ->{
            hideKeyboardFrom(context, layout);
        });

        // Add a TextView
        TextView finished = new TextView(context);
        finished.setText("Finished");
        finished.setTextSize(60);
        finished.setTextColor(R.color.black);
        finished.setGravity(Gravity.CENTER_VERTICAL);
        layout.addView(finished);

        TextView helpText = new TextView(context);
        helpText.setText("Create a name to save your score");
        helpText.setTextSize(18);
        layout.addView(helpText);

        TextView inputIndicator = new TextView(context);
        inputIndicator.setText(R.string.username);
        inputIndicator.setTextSize(18);
        inputIndicator.setPadding(0, 50, 0, 0);
        layout.addView(inputIndicator);

        EditText nameInput = new EditText(context);
        nameInput.setEms(10);
        nameInput.setTextSize(22);
        nameInput.setTextColor(R.color.black);
        layout.addView(nameInput);

        TextView scoreIndicator = new TextView(context);
        scoreIndicator.setText("your Score!");
        scoreIndicator.setTextSize(18);
        layout.addView(scoreIndicator);

        TextView endScore = new TextView(context);
        endScore.setText("10000000");
        endScore.setTextSize(28);
        layout.addView(endScore);

        Button save = new Button(context);
        save.setText("save");
        save.setOnClickListener(v -> dialog.dismiss());
        layout.addView(save);

        Button nope = new Button(context);
        nope.setText("Close");
        nope.setOnClickListener(v -> dialog.dismiss());
        layout.addView(nope);

        // Set the custom view to the dialog
                dialog.setContentView(layout);
//
//        // Show the dialog
        dialog.show();
    }

    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}