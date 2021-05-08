package com.example.minesweeper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterForMineFieldRV extends RecyclerView.Adapter<AdapterForMineFieldRV.MineFieldViewHolder> {

    Context context;

    List<Cell> cells;
    private CellClickListener listener;

    public AdapterForMineFieldRV(List<Cell> cells, CellClickListener listener, Context context) {
        this.cells = cells;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public MineFieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_layout, parent, false);
        return new MineFieldViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MineFieldViewHolder holder, int position) {
        holder.bind(cells.get(position));
        //holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return cells.size();
    }

    public void setCells(List<Cell> cells){
        this.cells = cells;
        notifyDataSetChanged();
    }

    public class MineFieldViewHolder extends RecyclerView.ViewHolder {

        TextView valueTV;

        public MineFieldViewHolder(@NonNull View itemView) {
            super(itemView);

            valueTV = itemView.findViewById(R.id.cellValue);
        }

        public void bind(final Cell cell){
            //itemView.setBackgroundColor(Color.GRAY);
            itemView.setBackgroundResource(R.drawable.cell_bg_unrevealed);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCellClick(cell);
                }
            });

            if(cell.isRevealed()) {
                if (cell.getValue() == Cell.BOMB) {
                    valueTV.setText(R.string.bomb);
                } else if (cell.getValue() == Cell.BLANK) {
                    valueTV.setText("");
                    itemView.setBackgroundColor(Color.BLACK);
                } else {
                    valueTV.setText(String.valueOf(cell.getValue()));

                    if (cell.getValue() == 1)
                        valueTV.setTextColor(ContextCompat.getColor(context, R.color.count1));
                    else if (cell.getValue() == 2)
                        valueTV.setTextColor(ContextCompat.getColor(context, R.color.count2));
                    else
                        valueTV.setTextColor(ContextCompat.getColor(context, R.color.count3));
                }
            }
            else if(cell.isFlagged()){
                valueTV.setText(R.string.flag);
            }
        }
    }
}

