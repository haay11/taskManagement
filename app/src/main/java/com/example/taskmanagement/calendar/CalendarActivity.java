package com.example.taskmanagement.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagement.R;
import com.example.taskmanagement.createbusiness.CreateNewBusinessActivity;
import com.marcohc.robotocalendar.RobotoCalendarView;

import java.util.Date;

public class CalendarActivity extends AppCompatActivity implements RobotoCalendarView.RobotoCalendarListener{
    private RobotoCalendarView robotoCalendarView;
    private Button calCheck, calCancel;
    private Intent intent;
    private String day;

    // 요청 코드 상수 정의
    public static final int REQUEST_CODE = 1;
    // 응답 코드 상수 정의
    public static final int RESULT_CODE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
        layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount= 0.5f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_calendar_pick);

        Intent intent1 = getIntent();
        if (intent1!=null) {
            String inputTitle = intent1.getStringExtra("title");
            String inputContents = intent1.getStringExtra("contents");
        }
        // Gets the calendar from the view
        robotoCalendarView = findViewById(R.id.robotoCalendarPicker);


        calCheck = findViewById(R.id.cal_check);
        calCancel = findViewById(R.id.cal_cancel);

        calCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CalendarActivity.this, CreateNewBusinessActivity.class);
                startActivity(intent);
                finish();
            }
        });
        calCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Date date = new Date();
                    intent = new Intent(CalendarActivity.this, CreateNewBusinessActivity.class);


                    if (day != null){
                        intent.putExtra("date", day);
                        setResult(CreateNewBusinessActivity.RESULT_CODE, intent);
                        finish();

                    } else {
                        Toast.makeText(CalendarActivity.this, "마감일을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        // Set listener, in this case, the same activity
        robotoCalendarView.setRobotoCalendarListener(this);

        robotoCalendarView.setShortWeekDays(false);

        robotoCalendarView.showDateTitle(true);

        robotoCalendarView.setDate(new Date());

};

    @Override
    public String onDayClick(Date date) {
        String[] month = new String[1];
        month = getMonth(String.valueOf(date));
        day = (date.getYear()+1900)+"."+month[0]+"."+month[1];
        return day;

    }

    public String[] getMonth(String date){

        String [] monthE = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int [] monthN = {1,2,3,4,5,6,7,8,9,10,11,12};
        String[] dayList1 = date.split(" ");
        String[] month = new String[2];
        month[0] = dayList1[1];
//        String monthName;
        for (int i = 0; i < monthE.length; i++){
            if (month[0].equals(monthE[i])){
                month[0]=String.valueOf(monthN[i]);
                break;
            }
        }
        month[1] = dayList1[2];




        return month;
    }



}