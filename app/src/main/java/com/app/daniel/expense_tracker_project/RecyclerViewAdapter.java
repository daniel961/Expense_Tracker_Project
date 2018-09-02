package com.app.daniel.expense_tracker_project;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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
    View mView;





    //Constructor
    public RecyclerViewAdapter(Context context, List<FinancialExpense> spesificExpensesList, List<FinancialExpense> fixedExpensesList,View view) {
        SpesificExpensesList = (ArrayList<FinancialExpense>) spesificExpensesList;
        FixedExpensesList = (ArrayList<FinancialExpense>) fixedExpensesList;
        this.context = context;
        this.mView = view;
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
        Log.d(TAG, "onBindViewHolder: called " + position);


        //fix the date signature string to string humans can read
        StringBuilder fixedDateStr = new StringBuilder(SpesificExpensesList.get(position).getFullDateSignature());
        fixedDateStr.insert(2,'/');
        fixedDateStr.insert(5,'/');


        if(SpesificExpensesList.get(position).getProductName().toString().length() >= 13 ){
            holder.name_tv.setTextSize(12);
            holder.name_tv.setText(SpesificExpensesList.get(position).getProductName());

        }else{
            holder.name_tv.setText(SpesificExpensesList.get(position).getProductName());
        }

        holder.price_tv.setText(Integer.toString(SpesificExpensesList.get(position).getExpenseAmount()));
        holder.date_tv.setText(fixedDateStr);



        holder.Item_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                StringBuilder fixedDateStr = new StringBuilder(SpesificExpensesList.get(position).getFullDateSignature());
                fixedDateStr.insert(2,'/');
                fixedDateStr.insert(5,'/');

                LayoutInflater factory = LayoutInflater.from(context);
                final View removeExpenseDialogView = factory.inflate(R.layout.remove_expense_dialog_layout, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
                deleteDialog.setView(removeExpenseDialogView);
                deleteDialog.show();

                Button delete_dialog_btn = (Button)removeExpenseDialogView.findViewById(R.id.delete_dialog_btn);
                Button dismiss_dialog_btn = (Button)removeExpenseDialogView.findViewById(R.id.dismiss_dialog_btn);
                TextView expense_name_dialog_tv = (TextView)removeExpenseDialogView.findViewById(R.id.expense_name_dialog_tv);


                expense_name_dialog_tv.setText( SpesificExpensesList.get(position).getProductName() + " " +
                        fixedDateStr + " במחיר: " +
                        String.valueOf(SpesificExpensesList.get(position).getExpenseAmount()));


                dismiss_dialog_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                    }
                });

                delete_dialog_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "הוצאה בשם " + "''" + SpesificExpensesList.get(position).getProductName()+ "''" +  " נמחקה מהרשימה", Toast.LENGTH_SHORT).show();
                        SystemActivity.isArrayChanged = true;
                        SystemActivity.position = position;
                        deleteDialog.dismiss();
                    }
                });




                return true;
            }
        });






    }

    @Override
    public int getItemCount() {
        int counter=0;
        counter = SpesificExpensesList.size();
        return counter;
    }



    public void deleteSelectedItem(int position){
        SpesificExpensesList.remove(position);
        notifyDataSetChanged();

    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView price_tv,name_tv,date_tv;
        RelativeLayout Item_layout;


        public ViewHolder(View itemView) {
            super(itemView);
            price_tv = (TextView)itemView.findViewById(R.id.Price_tv);
            name_tv = (TextView)itemView.findViewById(R.id.Expense_name_tv);
            date_tv = (TextView)itemView.findViewById(R.id.date_tv);
            Item_layout = (RelativeLayout)itemView.findViewById(R.id.Item_layout);


        }


    }


}
