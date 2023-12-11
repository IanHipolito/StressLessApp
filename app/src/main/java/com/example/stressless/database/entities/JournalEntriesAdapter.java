// Package declaration
package com.example.stressless.database.entities;

// Import statements
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stressless.database.OnJournalEntryDeleteListener;
import com.example.stressless.R;
import java.util.List;

// Reference: I took inspiration for the following code from:
// Lab 6B - Demo (Lecture Notes)
// Reference complete

// Adapter class for RecyclerView that displays journal entries
public class JournalEntriesAdapter extends RecyclerView.Adapter<JournalEntriesAdapter.ViewHolder> {

    // List of journal entries
    private List<JournalEntry> journalEntries;
    // Listener for delete events
    private OnJournalEntryDeleteListener deleteListener;

    // Constructor for the adapter
    public JournalEntriesAdapter(List<JournalEntry> journalEntries, OnJournalEntryDeleteListener listener) {
        this.journalEntries = journalEntries;
        this.deleteListener = listener;
    }

    // Method to create new ViewHolder for each item
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_journal_entry_item, parent, false);
        return new ViewHolder(view);
    }

    // Method to bind data to each ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the journal entry at the given position
        JournalEntry entry = journalEntries.get(position);
        // Set the text of the TextView to the entry's text
        holder.entryTextView.setText(entry.getEntryText());

        // Set a touch listener for the delete button
        holder.deleteButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Get the current position of the ViewHolder
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int currentPosition = holder.getBindingAdapterPosition();
                    // Check if the position is valid and call the delete listener
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        deleteListener.onDeleteJournalEntry(currentPosition);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    // Method to get the total number of items in the adapter
    @Override
    public int getItemCount() {
        return journalEntries.size();
    }

    // Static inner class for ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView entryTextView;
        ImageButton deleteButton;

        // Constructor for ViewHolder
        public ViewHolder(View view) {
            super(view);
            // Initialize TextView and ImageButton from the layout
            entryTextView = view.findViewById(R.id.entryTextView);
            deleteButton = view.findViewById(R.id.deleteButton);
        }
    }
}

