package com.example.stressless.database.entities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stressless.R;

import java.util.List;

public class JournalEntriesAdapter extends RecyclerView.Adapter<JournalEntriesAdapter.ViewHolder> {

    private List<JournalEntry> journalEntries;

    public JournalEntriesAdapter(List<JournalEntry> journalEntries) {
        this.journalEntries = journalEntries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_journal_entry_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JournalEntry entry = journalEntries.get(position);
        holder.entryTextView.setText(entry.getEntryText());
    }

    @Override
    public int getItemCount() {
        return journalEntries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView entryTextView;

        public ViewHolder(View view) {
            super(view);
            entryTextView = view.findViewById(R.id.entryTextView);
        }
    }
}

