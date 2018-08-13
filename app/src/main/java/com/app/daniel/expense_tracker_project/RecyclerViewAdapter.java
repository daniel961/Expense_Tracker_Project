package com.app.daniel.expense_tracker_project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter"; //for debug

    private ArrayList<FinancialExpense> SpesificExpensesList = new ArrayList<FinancialExpense>();
    private ArrayList<FinancialExpense> FixedExpensesList = new ArrayList<FinancialExpense>();
    Context context;

    public RecyclerViewAdapter(Context context, List<FinancialExpense> spesificExpensesList, List<FinancialExpense> fixedExpensesList) {
        SpesificExpensesList = (ArrayList<FinancialExpense>) spesificExpensesList;
        FixedExpensesList = (ArrayList<FinancialExpense>) fixedExpensesList;
        this.context = context;
    }

    public ArrayList<FinancialExpense> getSpesificExpensesList() {
        return SpesificExpensesList;
    }

    public void setSpesificExpensesList(ArrayList<FinancialExpense> spesificExpensesList) {
        SpesificExpensesList = spesificExpensesList;
    }

    public ArrayList<FinancialExpense> getFixedExpensesList() {
        return FixedExpensesList;
    }

    public void setFixedExpensesList(ArrayList<FinancialExpense> fixedExpensesList) {
        FixedExpensesList = fixedExpensesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) { //this methode call for every object
        Log.d(TAG, "onBindViewHolder: called ");
        holder.name_tv.setText(SpesificExpensesList.get(position).getProductName());
        holder.price_tv.setText(Integer.toString(SpesificExpensesList.get(position).getExpenseAmount()));

        holder.Item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, SpesificExpensesList.get(position).getProductName() , Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        int counter=0;
        counter = SpesificExpensesList.size()+FixedExpensesList.size();
        return counter;
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView price_tv,name_tv;
        RelativeLayout Item_layout;


        public ViewHolder(View itemView) {
            super(itemView);
            price_tv = (TextView)itemView.findViewById(R.id.Price_tv);
            name_tv = (TextView)itemView.findViewById(R.id.Expense_name_tv);
            Item_layout = (RelativeLayout)itemView.findViewById(R.id.Item_layout);

        }


    }


}
