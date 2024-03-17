package com.example.epinect.Activities.EpilepsyGuide;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.epinect.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class Instructions extends AppCompatActivity {
    private int stepNumber = 1;
    private ListView listViewInstructions;
    private ArrayList<String> instructionsList;
    private ArrayAdapter<String> instructionsAdapter;
    private FloatingActionButton fabAddInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        listViewInstructions = findViewById(R.id.listViewInstructions);
        fabAddInstruction = findViewById(R.id.fabAddInstruction);

        instructionsList = new ArrayList<>();
        instructionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, instructionsList);
        listViewInstructions.setAdapter(instructionsAdapter);

        fabAddInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddInstructionDialog();
            }
        });
    }

    private void showAddInstructionDialog() {
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.add_instruction, null);

        final EditText editTextInstruction = dialogView.findViewById(R.id.editTextDescription);
        Button btnAdd = dialogView.findViewById(R.id.addButton);
        Button btnCancel = dialogView.findViewById(R.id.cancelButton);

        final androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(this)
                .setView(dialogView)

                .create();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String instructionContent = editTextInstruction.getText().toString().trim();
                if (!instructionContent.isEmpty()) {
                    String instruction = "Step " + stepNumber + ": " + instructionContent;
                    instructionsList.add(instruction);
                    instructionsAdapter.notifyDataSetChanged();
                    stepNumber++; // Increment the step number for the next instruction
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}