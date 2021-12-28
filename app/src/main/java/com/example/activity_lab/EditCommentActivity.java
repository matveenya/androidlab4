package com.example.activity_lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditCommentActivity extends AppCompatActivity {

    private EditText editCommentInput;

    public void cancel() {
        Intent returnIntent = new Intent();
        EditCommentActivity.this.setResult(Activity.RESULT_CANCELED, returnIntent);

        Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT).show();

        EditCommentActivity.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.options_cancel, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean result = true;

        if (item.getItemId() == R.id.cancel_option) {
            AlertDialog alertDialog = new AlertDialog
                    .Builder(EditCommentActivity.this)
                    .setTitle(R.string.confirmation)
                    .setMessage(R.string.cancel_confirmation)
                    .setPositiveButton(R.string.confirmation_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cancel();
                        }
                    })
                    .setNegativeButton(R.string.confirmation_no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .create();

            alertDialog.show();

        } else {
            result = super.onOptionsItemSelected(item);
        }

        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comment);

        Bundle extras = this.getIntent().getExtras();

        Button saveBtn = (Button) this.findViewById(R.id.saveBtn);
        Button cancelBtn = (Button) this.findViewById(R.id.cancelBtn);
        this.editCommentInput = (EditText) this.findViewById(R.id.editCommentInput);

        this.editCommentInput.setText(extras.getString("COMMENT_VALUE"));

        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String comment = EditCommentActivity.this.editCommentInput.getText().toString().trim();

                if(comment.isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog
                            .Builder(EditCommentActivity.this)
                            .setMessage(R.string.validation_title)
                            .create();

                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                    return;
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra("COMMENT_VALUE", comment);

                EditCommentActivity.this.setResult(Activity.RESULT_OK, returnIntent);
                EditCommentActivity.this.finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancel();
            }
        });
    }
}