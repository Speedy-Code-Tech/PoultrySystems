package com.example.poultryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SMSVerification extends AppCompatActivity {

    String phoneNumber;
    Long timeoutSeconds = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken  resendingToken;

    EditText otpInput;
    Button nextBtn;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    PhoneAuthProvider.ForceResendingToken frt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsverification);

        otpInput = findViewById(R.id.numberInput);
    nextBtn = findViewById(R.id.otpBTN);
        nextBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String num = otpInput.getText().toString();
                        Toast.makeText(SMSVerification.this, num, Toast.LENGTH_SHORT).show();
                        sendOtp(num,false);
                    }
                }
        );


    }

    void sendOtp(String phoneNumber,boolean isResend){

        //setInProgress(true);
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(10L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                signIn(phoneAuthCredential);
                              //  setInProgress(false);
                                Toast.makeText(SMSVerification.this, "Success", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
//                                AndroidUtil.showToast(getApplicationContext(),"OTP verification failed");
                                //setInProgress(false);
                                Toast.makeText(SMSVerification.this, "Failed", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
//                                AndroidUtil.showToast(getApplicationContext(),"OTP sent successfully");
                                //setInProgress(false);
                                Toast.makeText(SMSVerification.this, "Success Sent", Toast.LENGTH_SHORT).show();

                            }
                        });
        PhoneAuthProvider.verifyPhoneNumber(builder.build());
//        if(isResend){
//            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
//        }else{
//
//        }

    }
    void setInProgress(boolean inProgress){

    }


}