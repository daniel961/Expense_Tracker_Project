package com.app.daniel.expense_tracker_project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class SystemActivity extends AppCompatActivity {


    EditText fullname_ET,password_ET,Income_ET,ProductName_ET,ProductAmount_ET;
    Button Submit_BTN,AddNewOutcome,AppSettings,UserSettings,AddnewOutcomeSubmitButton,exit_NEW_OUTCOME_WINDOWE_Btn,
    DeleteAllExpenses_BTN,exitGuide_btn,nextGuide_btn,prevGuide_btn;
    LinearLayout NewUserLayout,MainSystem,newExpenseLayout;
    RelativeLayout appGuideLayout;
    TextView NameDisplay_TV,OutcomeDisplay_TV,IncomeDisplay_TV,MoneyLeft_TV;

    View mView;


    Thread thread;
    Handler handler = new Handler();
    static boolean isArrayChanged = false;
    boolean threadRun = true;
    static int position;

    String full_name,password;
    int incomePerMonth;


    public static final String NAME_KEY="name",PASSWORD_KEY="password",INCOME_KEY="income",SharedPreferencesName = "First_Opening_User_Data"; //constant for the keys AND name of data
    public static final String Expenses_Lists_data = "Expenses_lists",Monthly_Expense_list = "Monthly_Expense_list";

    profile CurrentProfile = new profile();
    RecyclerViewAdapter adapter;


    //vars for ViewPager
    LinearLayout introduction_layout;
    ViewPager mSlideViewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    Button intro_close_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);

        //vars for ViewPager refs
        introduction_layout = (LinearLayout) findViewById(R.id.introduction_layout);
        mSlideViewPager = (ViewPager) findViewById(R.id.viewPagerIntro);
        intro_close_btn = (Button)findViewById(R.id.intro_close_btn);
        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);






        mView = getLayoutInflater().inflate(R.layout.remove_expense_dialog_layout,null);



        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesName,MODE_PRIVATE);
        NewUserLayout = (LinearLayout)findViewById(R.id.NewUserLayout);
        fullname_ET =(EditText)findViewById(R.id.FullName_ET);
        password_ET =(EditText)findViewById(R.id.CODE_ET);
        Income_ET =(EditText)findViewById(R.id.Income_ET);
        Submit_BTN =(Button) findViewById(R.id.Submit_BTN);



        //appGuideLayout
        appGuideLayout = (RelativeLayout)findViewById(R.id.appGuideLayout);





        //mainSystemLayout
        MainSystem = (LinearLayout)findViewById(R.id.MainSystem);
        NameDisplay_TV = (TextView)findViewById(R.id.NameDisplay_TV);
        OutcomeDisplay_TV = (TextView)findViewById(R.id.OutcomeDisplay_TV);
        IncomeDisplay_TV = (TextView)findViewById(R.id.IncomeDisplay_TV);
        MoneyLeft_TV = (TextView)findViewById(R.id.MoneyLeft_TV);
        //Buttons
        AddNewOutcome = (Button)findViewById(R.id.AddNewOutcome);
        AppSettings = (Button)findViewById(R.id.AppSettings);
        UserSettings = (Button)findViewById(R.id.UserSettings);
        DeleteAllExpenses_BTN = (Button)findViewById(R.id.DeleteAllExpenses_BTN);

        //newExpenseLayout
        newExpenseLayout = (LinearLayout)findViewById(R.id.newExpenseLayout);
        AddnewOutcomeSubmitButton = (Button) findViewById(R.id.AddnewOutcomeSubmitButton);
        exit_NEW_OUTCOME_WINDOWE_Btn = (Button) findViewById(R.id.exit_NEW_OUTCOME_WINDOWE_Btn);
        ProductName_ET = (EditText) findViewById(R.id.ProductName_ET);
        ProductAmount_ET = (EditText) findViewById(R.id.ProductAmount_ET);
        final Animation fade_in = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        final Animation downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);


        //This Thread Wating for Change in Variable isArrayChanged boolean var
        //wating to get postion item to delete from Array
        //and call to the function to delete
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(threadRun == true) { //always waiting for change
                    Log.i("Thread","thread running...");
                    while (isArrayChanged != true) {
                        //Log.i("Thread","Waiting for Change in the Array");
                    }
                    Log.i("Thread", "Change Found" + position);
                    //call function to delete the Array in the current position
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            deleteSelectedItem(position);
                        }
                    });
                    isArrayChanged = false;
                }
            }
        });
         thread.start();






        if(!(sharedPreferences.contains(NAME_KEY))){ //return true if exist
            //show NewUserLayout
            Toast.makeText(this, "לא נמצא יוזר", Toast.LENGTH_SHORT).show();
            NewUserLayout.setVisibility(View.VISIBLE);
        }else{
            //todo add password layout for security (in the appSettings you can add bolean var for check if the user wants to add password each enterence)
            //upload profile
            MainSystem.setVisibility(View.VISIBLE);
            MainSystem.setAnimation(fade_in);
            load_data();
            Load_List_Data_NormalExpenses();//load list to the Profile Object if there is no list its will be null
            updateTVboxes();

            Toast.makeText(this, "יוזר קיים עובר לאקטיביטי הבא", Toast.LENGTH_SHORT).show();
        }





        //Submit Button for new user first entered
        Submit_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(
                   fullname_ET.getText().toString().length()>15||   //checks its not too long
                   Income_ET.getText().toString().length()>7||
                   password_ET.getText().toString().length()>15||
                   fullname_ET.getText().toString().isEmpty()|| //checks its not empty
                   Income_ET.getText().toString().isEmpty()||
                   password_ET.getText().toString().isEmpty()||
                   Integer.parseInt(Income_ET.getText().toString())>50000){
                    //show dialog introductions about the fill
                    AlertDialog.Builder IntroductionsFields = new AlertDialog.Builder(SystemActivity.this);
                    IntroductionsFields.setTitle("שים לב הפרטים לא נכונים").setMessage("1. משכורת חודשית מקסימלית 50 אלף ש''ח" + "\n" +
                     "2. ודא שכל השדות מלאים"
                    +"\n"  +
                      "3. מקסימום 15  תווים (עבור כל שדה)"
                    ).setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

                }else{
                    //profile profile = new profile(fullname_ET.getText().toString(),password_ET.getText().toString(),Double.parseDouble(Income_ET.getText().toString()),getApplicationContext());
                    //save data
                    incomePerMonth = Integer.parseInt(Income_ET.getText().toString());
                    password = password_ET.getText().toString();
                    full_name = fullname_ET.getText().toString();
                    save_data();            //save data for first time (First time enter app save)
                    NewUserLayout.setVisibility(View.GONE);
                    MainSystem.setVisibility(View.VISIBLE);
                    MainSystem.setAnimation(fade_in);
                    load_data();
                    updateTVboxes();

                }

            }
        });




        AppSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainSystem.setVisibility(View.GONE); //is he gone?
                introduction_layout.setVisibility(View.VISIBLE);
                introduction_layout.bringToFront();


            }
        });

        intro_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainSystem.setVisibility(View.VISIBLE); //is he gone?
                introduction_layout.setVisibility(View.GONE);

            }
        });




        //----------------------------------------SystemActivity Program Start HERE!--------------------------------------------------




        AddnewOutcomeSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ProductAmount_ET.getText().toString().isEmpty() || ProductName_ET.getText().toString().isEmpty()){
                    Toast.makeText(SystemActivity.this, "וודא שמילאת את כל הפרטים הדרושים", Toast.LENGTH_SHORT).show();

                }else{
                    newExpenseLayout.setVisibility(View.GONE);
                    CurrentProfile.AddNormalExpense(Integer.parseInt(ProductAmount_ET.getText().toString()),ProductName_ET.getText().toString());
                    updateTVboxes();


                    ProductName_ET.setText("");
                    ProductAmount_ET.setText("");
                    //todo SAVE LIST on local device
                    Save_List_Data_NormalExpenses(); //Save list with SharePreferences
                    //todo load new list after save
                    Load_List_Data_NormalExpenses(); //Load list with SharePreferences


                    updateRecyclerViewList();

                }



            }
        });


        AddNewOutcome.setOnClickListener(new View.OnClickListener() { //Add new Outcome Button
            @Override
            public void onClick(View v) {
                int result;



                newExpenseLayout.setVisibility(View.VISIBLE);
                newExpenseLayout.bringToFront();
                newExpenseLayout.setAnimation(fade_in);
                newExpenseLayout.setAnimation(downtoup);


            }
        });


        exit_NEW_OUTCOME_WINDOWE_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newExpenseLayout.setVisibility(View.GONE);
            }
        });



        DeleteAllExpenses_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder IntroductionsFields = new AlertDialog.Builder(SystemActivity.this);
                IntroductionsFields.setTitle("האם אתה בטוח שאתה רוצה למחוק את כל ההוצאות?").setMessage("שים לב שלא תוכל לשחזר נתונים לאחר המחיקה" +
                        "").setPositiveButton("כן מחק הכל", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CurrentProfile.DeleteAllExpenses(); //delete Expense lists in the profile Object
                        Save_List_Data_NormalExpenses(); //save the new cleared lists to File
                        Load_List_Data_NormalExpenses(); //Load From File (NOT NECCECERY)
                        updateTVboxes(); //Update TextsVIEWS
                        updateRecyclerViewList();



                    }
                }).setNegativeButton("לא מעוניין", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();




            }
        });





        init_recycler_view(); //initialize recycler view




    }




    //-------------------------------------------------------------------------------------------
    public void deleteSelectedItem(int position){ //delete selected item from the list inside the profile and refresh all
        CurrentProfile.deleteExpenseByPosition(position); //delete from the profile list
        Save_List_Data_NormalExpenses(); //save list to device
        updateTVboxes(); //update ui
        updateRecyclerViewList(); //update the RecyclerList with the new List
        //here is the bug! IllegalThreadStateException
        //thread.start(); //reStart the Waiting for Changes Thread



    }

    //-------------------------------------------------------------------------------------------
    public void updateRecyclerViewList(){

        adapter.setSpesificExpensesList((ArrayList<FinancialExpense>) CurrentProfile.getCurrentMonthExpensesList());
        adapter.notifyDataSetChanged();

    }

    //-------------------------------------------------------------------------------------------



    public void save_data(){ //save data for first time (First time enter app save)
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesName,MODE_PRIVATE); //private means that no other app can change our preferences
        SharedPreferences.Editor editor = sharedPreferences.edit(); //the editor will save our variables

        //put the variables
        editor.putString(NAME_KEY,full_name); //key/value AND ITS SAVE!
        editor.putString(PASSWORD_KEY,password);
        editor.putInt(INCOME_KEY,incomePerMonth);

        editor.apply(); //must do this for take action
        Toast.makeText(this, "Saveed Succsesfully!", Toast.LENGTH_SHORT).show();


    }

    //-------------------------------------------------------------------------------------------
    public void Save_List_Data_NormalExpenses(){
        SharedPreferences sharedPreferences = getSharedPreferences(Expenses_Lists_data,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(CurrentProfile.getCurrentMonthExpensesList()); //todo do the same for the FixedExpenses list
        editor.putString(Monthly_Expense_list,json);
        editor.apply();
        //Toast.makeText(this, "שמירת נתונים במכשיר בוצעה בהצלחה", Toast.LENGTH_SHORT).show();

        }


    //-------------------------------------------------------------------------------------------
    public void init_recycler_view(){

        adapter = new RecyclerViewAdapter(this, CurrentProfile.getCurrentMonthExpensesList(),CurrentProfile.getFixedMonthlyExpenseList(),mView);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view_Widget);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }




    //-------------------------------------------------------------------------------------------

    public void Load_List_Data_NormalExpenses(){
        SharedPreferences sharedPreferences = getSharedPreferences(Expenses_Lists_data,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Monthly_Expense_list,null);
        Type type = new TypeToken<ArrayList<FinancialExpense>>() {}.getType(); //to know which kind is the array we get from json string
        CurrentProfile.setCurrentMonthExpensesList((List<FinancialExpense>) gson.fromJson(json,type));
        //todo the same operation for the fixedExpense List
        Toast.makeText(this, "טעינת נתונים מהמכשיר בוצעה בהצלחה ", Toast.LENGTH_SHORT).show();




    }


    //-------------------------------------------------------------------------------------------
    public void load_data(){
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesName,MODE_PRIVATE);


        full_name = sharedPreferences.getString(NAME_KEY,"");
        password = sharedPreferences.getString(PASSWORD_KEY,"000000");
        incomePerMonth = sharedPreferences.getInt(INCOME_KEY,0);
        //CREATE AND set arguments for profile
        CurrentProfile.setName(full_name);
        CurrentProfile.setSecurityCode(password);
        CurrentProfile.setIncome(incomePerMonth);


    }

    //-------------------------------------------------------------------------------------------

    public void updateTVboxes(){ //This Methode Updates ALL TV BOXES
        //todo update recyclerView
        //recycler_view_Update();

        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesName,MODE_PRIVATE);

        NameDisplay_TV.setText("שלום " + CurrentProfile.getName());
        IncomeDisplay_TV.setText("הכנסות: " + CurrentProfile.getIncome());
        OutcomeDisplay_TV.setText("הוצאות: " + CurrentProfile.ExpensesCalculator());

        if(CurrentProfile.getMoneyLeft()>=0) {
            MoneyLeft_TV.setText("נותרו החודש " + CurrentProfile.getMoneyLeft() + " שקלים");
        }else{
            MoneyLeft_TV.setText("אתה במינוס " + CurrentProfile.getMoneyLeft() + " שקלים");
        }


    }

}
