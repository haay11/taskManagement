package com.example.taskmanagement.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.taskmanagement.R;
import com.example.taskmanagement.data.TaskUserInfo;
import com.example.taskmanagement.main.MainActivity;
import com.example.taskmanagement.signin.SignInActivity;
import com.example.taskmanagement.storage.TaskDataFirebase;
import com.example.taskmanagement.storage.TaskStorage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle, emailWarningTv, pwWarningTv, pwCheckWarningTv;
    private EditText signUpEmailEd, signUpPwEd,signUpCheckPwEd, signUpNameEd,
            signUpDepartmentEd, signUpPositionEd, signUpCompanyEd;
    private Button emailCheckBtn, signUpCancelBtn, signUpBtn;
    private ImageView emailWarningImg,pwWarningImg, pwCheckWarningImg, titleImg;
    private Intent intent;
    private TaskStorage taskStorage;


    public String email = "";
    public String password = "" ;
    public String passwordCheck = "" ;
    public String nameEd = "" ;
    public String companyEd = "" ;
    public String departmentEd = "" ;
    public String positionEd = "" ;

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,16}$");

    // 이메일 정규식
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{4,16}$", Pattern.CASE_INSENSITIVE);

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // toolbar 설정
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbarTitle.setText("회원가입");
        toolbar.setNavigationIcon(null);
        toolbarTitle.setVisibility(View.VISIBLE);
        titleImg = findViewById(R.id.toolbar_title_img);
        titleImg.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        emailWarningTv = findViewById(R.id.email_warning_tv);
        pwWarningTv = findViewById(R.id.pw_warning_tv);
        pwCheckWarningTv = findViewById(R.id.pw_check_warning_tv);
        emailWarningImg = findViewById(R.id.email_warning_img);
        pwWarningImg = findViewById(R.id.pw_warning_img);
        pwCheckWarningImg = findViewById(R.id.pw_check_warning_img);

        signUpEmailEd = findViewById(R.id.sign_up_email_ed);
        signUpPwEd = findViewById(R.id.sign_up_pw_ed);
        signUpCheckPwEd = findViewById(R.id.sign_up_check_pw_ed);
        signUpNameEd = findViewById(R.id.sign_up_name_ed);
        signUpDepartmentEd = findViewById(R.id.sign_up_department_ed);
        signUpPositionEd = findViewById(R.id.sign_up_position_ed);
        signUpCompanyEd = findViewById(R.id.sign_up_company_ed);
        signUpCancelBtn = findViewById(R.id.sign_up_cancel_btn);

        signUpBtn = findViewById(R.id.sign_up_btn2);
        emailCheckBtn = findViewById(R.id.email_check_btn);

        email = signUpEmailEd.getText().toString().trim();
        password = signUpPwEd.getText().toString().trim();


        passwordCheck = signUpCheckPwEd.getText().toString().trim();
        nameEd = signUpNameEd.getText().toString().trim();
        companyEd = signUpCompanyEd.getText().toString().trim();
        departmentEd = signUpDepartmentEd.getText().toString().trim();
        positionEd = signUpPositionEd.getText().toString().trim();


        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        taskStorage = new TaskDataFirebase();

        taskStorage.getUserList(new TaskStorage.OnDataReceivedListener() {
            @Override
            public void OnDataReceived(List<String> userEmailList) {


            }
        });


        emailCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = signUpEmailEd.getText().toString().trim();
                getUserEmail(email);
                }

            });


        signUpEmailEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                emailCheck(v);
                if (hasFocus){

                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        emailWarningImg.setVisibility(View.INVISIBLE);
                        emailWarningTv.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        signUpPwEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pwCheck(v);
                if (hasFocus) {
                    if (Patterns.EMAIL_ADDRESS.matcher(password).matches()) {
                        pwWarningImg.setVisibility(View.INVISIBLE);
                        pwWarningTv.setVisibility(View.INVISIBLE);
                    } else if (isValidCheckPassword()==true){
                        pwWarningImg.setVisibility(View.INVISIBLE);
                        pwWarningTv.setVisibility(View.INVISIBLE);
                    }
                }
            }

        });

        signUpCheckPwEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    pwDoubleCheck(v);
                } else if (isValidCheckPassword()==true){
                    pwCheckWarningImg.setVisibility(View.INVISIBLE);
                    pwCheckWarningTv.setVisibility(View.INVISIBLE);
                }
            }
        });

        // 회원가입
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(v);

            }
        });

            signUpPositionEd.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if(event.getAction() == KeyEvent.ACTION_DOWN){
                        if(keyCode == KeyEvent.KEYCODE_ENTER){

                            try {
                                signUp(v);
                                return true;
                            }catch (NullPointerException e){
                                getCurrentFocus();
                            }
                        }
                    }
                    return false;
                }
            });

            signUpCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

    }

    public void emailCheck(View view){

        signUpEmailEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email = s.toString().trim();
                if (!isValidEmail()||emailCheckBtn.getText().equals("확인")){
                    emailWarningImg.setVisibility(View.VISIBLE);
                    emailWarningTv.setVisibility(View.VISIBLE);
                } else if  (emailCheckBtn.getText().equals("인증")){
                    emailWarningImg.setVisibility(View.INVISIBLE);
                    emailWarningTv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void pwCheck(View view){

        signUpPwEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s.toString().trim();
                if (!isValidCheckPassword()&&(password.length()<6)){
                    pwWarningImg.setVisibility(View.VISIBLE);
                    pwWarningTv.setVisibility(View.VISIBLE);
                }else {
                    pwWarningImg.setVisibility(View.INVISIBLE);
                    pwWarningTv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void pwDoubleCheck(View view){

        signUpCheckPwEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordCheck = s.toString().trim();
                if ((!isValidCheckPassword())){
                    pwCheckWarningTv.setVisibility(View.VISIBLE);
                    pwCheckWarningImg.setVisibility(View.VISIBLE);
                } else {
                    pwCheckWarningImg.setVisibility(View.INVISIBLE);
                    pwCheckWarningTv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void signUp(View view){
        email = signUpEmailEd.getText().toString().trim();
        password = signUpPwEd.getText().toString().trim();

        passwordCheck = signUpCheckPwEd.getText().toString().trim();
        nameEd = signUpNameEd.getText().toString().trim();
        companyEd = signUpCompanyEd.getText().toString().trim();
        departmentEd = signUpDepartmentEd.getText().toString().trim();
        positionEd = signUpPositionEd.getText().toString().trim();

         createUser(email, password);

        if (!emailCheckBtn.getText().equals("인증")){
            Toast.makeText(SignUpActivity.this, "사용 가능한 이메일인지 확인해주세요.", Toast.LENGTH_SHORT).show();
        }else if (isValidEmail() && isValidPassword() && isValidCheckPassword() && (nameEd != "")
                && (companyEd != "") && (departmentEd != "") && (positionEd != "") ){
            createUser(email, password);
        } else if (!isValidEmail() || !isValidPassword() || !isValidCheckPassword() || (nameEd == "")
                || (companyEd == "") || (departmentEd == "") || (positionEd == "")){
            Toast.makeText(SignUpActivity.this, "입력창을 모두 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }

    }

    // 비밀번호 유효성 검사
    private boolean isValidPassword() {
        if (password.isEmpty()){
            // 비밀번호 공백
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()){
            // 비밀번호 형식 불일치
            return false;
        }
            else return true;
    }

    // 비밀번호 체크
    private boolean isValidCheckPassword(){
        if(passwordCheck.isEmpty()){
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordCheck).matches()){
            return false;
        } else if (!password.equals(passwordCheck)){
            return false;
        } else return true;
    }

    // 이메일 유효성 검사
    private boolean isValidEmail() {
        if (email.isEmpty()){
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            // 이메일 형식 불일치
            return false;
        } else return true;
    }

    // 회원가입
    private void createUser(String email, String Password){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        passwordCheck = signUpCheckPwEd.getText().toString().trim();
                        nameEd = signUpNameEd.getText().toString().trim();
                        companyEd = signUpCompanyEd.getText().toString().trim();
                        departmentEd = signUpDepartmentEd.getText().toString().trim();
                        positionEd = signUpPositionEd.getText().toString().trim();
                        if (task.isSuccessful()) {
                            // 회원 가입 성공
                            TaskStorage taskStorage = new TaskDataFirebase();
                            Toast.makeText(SignUpActivity.this, R.string.success_signup, Toast.LENGTH_SHORT).show();
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            taskStorage.insertUserInfo(new TaskUserInfo(uid, email, nameEd, companyEd, positionEd, departmentEd));

                            intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (!task.isSuccessful()){
                            // 회원 가입 실패
                            Toast.makeText(SignUpActivity.this, R.string.failed_signup, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }



    // 이메일 중복 확인
    public void getUserEmail(String email){
        taskStorage = new TaskDataFirebase();

        taskStorage.getUserList(new TaskStorage.OnDataReceivedListener() {

            @Override
            public void OnDataReceived(List<String> userEmailList) {

                    for (String userEmail : userEmailList) {
                        if (String.valueOf(userEmail).equals(String.valueOf(email))) {
                            Toast.makeText(SignUpActivity.this, "이미 사용중인 이메일 입니다.", Toast.LENGTH_LONG).show();
                            return ;
                        }

                    }
                if (email == null || email.equals("")){
                    Toast.makeText(SignUpActivity.this, "이메일을 인증해주세요.", Toast.LENGTH_LONG).show();
                    return;

                } else if (!isValidEmail()){
                    Toast.makeText(SignUpActivity.this, "이메일 주소를 입력해주세요.", Toast.LENGTH_SHORT ).show();
                    return;
                }else{

                    Toast.makeText(SignUpActivity.this, "사용 가능한 이메일 입니다.", Toast.LENGTH_LONG).show();
                    emailCheckBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.certified_btn));
                    emailCheckBtn.setText("인증");
                    emailWarningImg.setVisibility(View.INVISIBLE);
                    emailWarningTv.setVisibility(View.INVISIBLE);

                    return;
                }
            }
        });

        }
}
