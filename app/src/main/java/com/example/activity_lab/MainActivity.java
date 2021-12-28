package com.example.activity_lab;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView addressTextView;
    private TextView commentTextView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.options_exit, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean result = true;

        if (item.getItemId() == R.id.exit_option) {
            AlertDialog alertDialog = new AlertDialog
                    .Builder(MainActivity.this)
                    .setTitle(R.string.confirmation)
                    .setMessage(R.string.exit_confirmation)
                    .setPositiveButton(R.string.confirmation_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.this.finish();
                            MainActivity.this.moveTaskToBack(true);
                            System.exit(0);
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
        setContentView(R.layout.activity_main);

        this.nameTextView = (TextView) this.findViewById(R.id.nameValue);
        this.addressTextView = (TextView) this.findViewById(R.id.addressValue);
        this.commentTextView = (TextView) this.findViewById(R.id.commValue);

        Button editBtnName = (Button) this.findViewById(R.id.editBtnName);
        Button editBtnAddress = (Button) this.findViewById(R.id.editBtnAddress);
        Button editBtnComment = (Button) this.findViewById(R.id.editBtnComm);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        String nameString = data.getStringExtra("NAME_VALUE");
                        String addressString = data.getStringExtra("ADDRESS_VALUE");
                        String commentString = data.getStringExtra("COMMENT_VALUE");

                        if(nameString != null && !nameString.isEmpty()) {
                            nameTextView.setText(nameString);
                        }

                        if(addressString != null && !addressString.isEmpty()) {
                            addressTextView.setText(addressString);
                        }

                        if(commentString != null && !commentString.isEmpty()) {
                            commentTextView.setText(commentString);
                        }
                    }
                });


        editBtnName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditNameActivity.class);
                intent.putExtra("NAME_VALUE", nameTextView.getText());
                someActivityResultLauncher.launch(intent);
            }
        });

        editBtnAddress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditAddressActivity.class);
                intent.putExtra("ADDRESS_VALUE", addressTextView.getText());
                someActivityResultLauncher.launch(intent);
            }
        });

        editBtnComment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditCommentActivity.class);
                intent.putExtra("COMMENT_VALUE", commentTextView.getText());
                someActivityResultLauncher.launch(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("NAME_VALUE", String.valueOf(this.nameTextView.getText()));
        outState.putString("ADDRESS_VALUE", String.valueOf(this.addressTextView.getText()));
        outState.putString("COMMENT_VALUE", String.valueOf(this.commentTextView.getText()));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        this.nameTextView.setText(savedInstanceState.getString("NAME_VALUE"));
        this.addressTextView.setText(savedInstanceState.getString("ADDRESS_VALUE"));
        this.commentTextView.setText(savedInstanceState.getString("COMMENT_VALUE"));
    }
}