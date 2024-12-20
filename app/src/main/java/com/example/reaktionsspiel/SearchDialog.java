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

import com.google.android.material.snackbar.Snackbar;

public class SearchDialog {
    private Context context;
    private Dialog dialog;
    EditText nameInput;
    GameActivity activity;

    public SearchDialog(Context context, GameActivity activity) {
        this.context = context;
        this.activity = activity;

    }

    public void showDialog() {
        // Create a dialog
        dialog = new Dialog(context);
        dialog.setCancelable(false);

        // Create a LinearLayout to serve as the container
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(170, 50, 170, 200);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        layout.setBackgroundColor(Color.WHITE);

        layout.setOnClickListener(l ->{
            hideKeyboardFrom(context, layout);
        });

        // Add a TextView
        TextView search = new TextView(context);
        search.setText("search");
        search.setTextSize(40);
        search.setTextColor(R.color.black);
        search.setGravity(Gravity.CENTER_VERTICAL);
        layout.addView(search);

        TextView helpText = new TextView(context);
        helpText.setText("search for a name in the scoreboard");
        helpText.setTextSize(18);
        layout.addView(helpText);

        TextView inputIndicator = new TextView(context);
        inputIndicator.setText(R.string.username);
        inputIndicator.setTextSize(18);
        inputIndicator.setPadding(0, 50, 0, 0);
        layout.addView(inputIndicator);

        nameInput = new EditText(context);
        nameInput.setEms(10);
        nameInput.setTextSize(22);
        nameInput.setTextColor(R.color.black);
        layout.addView(nameInput);

        Button save = new Button(context);
        save.setText("search");
        save.setOnClickListener(v -> pressedSearch(nameInput.getText().toString().trim()));
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

    public void pressedSearch(String name) {
        activity.pressedSearch(name);
        dialog.dismiss();
    }
}
