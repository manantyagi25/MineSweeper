package com.example.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MineFieldViewHolder extends RecyclerView.ViewHolder {

    TextView valueTV;

    public MineFieldViewHolder(@NonNull View itemView) {
        super(itemView);

        valueTV = itemView.findViewById(R.id.cellValue);
    }

    public void bind(final Cell cell){
        itemView.setBackgroundColor(Color.GRAY);

    }
}
