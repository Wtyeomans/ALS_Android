package com.example.frosty.als_involve_v3;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    //get databasehelper
    DatabaseHelper myDb;

    //initialize the fragments
    ProfileFragment profileFragment = new ProfileFragment();
    HomeFragment homeFragment = new HomeFragment();
    HistoryFragment historyFragment = new HistoryFragment();

    //user
    public User user = new User();
    public String UID;
    public FingerTap fingerResults = new FingerTap();
    public ToeTap toeResults = new ToeTap();
    public ArmRotation lArmResults = new ArmRotation();
    public ArmRotation rArmResults = new ArmRotation();
    public ArmRotation lLegResults = new ArmRotation();
    public ArmRotation rLegResults = new ArmRotation();

    //static strings to store test results for display
    public String leftTapsStr, rightTapsStr, toeTapsStr, rotationResultsStr, rotationAppendage, rotationSide;


    //this sets "content" (an id of a layout in main_activity.xml) to one of the three main fragments
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            android.app.FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.content, homeFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_profile:
                    //replace with profile fragment
                    fragmentTransaction.replace(R.id.content, profileFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_history:
                    fragmentTransaction.replace(R.id.content, historyFragment);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onBackPressed(){
        //TODO do nothing?
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Bottom navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //initialize db helper
        myDb = new DatabaseHelper(this);

        Intent startLogin = new Intent(this, Login.class);

        final int loginRequest = 101;
        startActivityForResult(startLogin, loginRequest);

        fingerResults = fingerResults.createTestFingerTap();
        toeResults = toeResults.createTestToeTap();
        lArmResults = lArmResults.createTestArmRotationL();
        rArmResults = rArmResults.createTestArmRotationR();
        lLegResults = lLegResults.createTestArmRotationL();
        rLegResults = rLegResults.createTestArmRotationR();

    }


    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onResume() {
        super.onResume();

        //keeping finger tap results displayed after other activities, doesn't work for changing fragments
        try{
            TextView fingerTapTestResults = (TextView) findViewById(R.id.finger_tap_test_result_text_view);
            if(leftTapsStr!=null && rightTapsStr !=null){
                fingerTapTestResults.setText("L-" + leftTapsStr + " R-" + rightTapsStr);
            }

        } catch (Exception e){
            Log.e("onResume", e.getLocalizedMessage());
        }

        //keeping toe taps results displayed
        try{
            TextView toeTapTestResults = (TextView) findViewById(R.id.toe_tap_results_text_view);
            if(toeTapsStr!=null){
                toeTapTestResults.setText(toeTapsStr);
            }
        }catch(Exception e){
            Log.e("onResume", e.getLocalizedMessage());
        }

        //keeping rotation results displayed
        try{
            TextView rotationResuls = (TextView) findViewById(R.id.arm_rotation_results_text_view);
            if(rotationResultsStr!=null){
                rotationResuls.setText(rotationResultsStr);
            }
        }catch(Exception e){
            Log.e("onResume", e.getLocalizedMessage());
        }

    }

    /**
     * The Functions are called to start other activities
     */
    //start the arm rotation test
    //send request code 1
    public void startArmRotationTest(View view) {
        Intent startArmRotationTest = new Intent(this, GyroscopeActivity.class);

        final int result = 1;
        startActivityForResult(startArmRotationTest, result);
    }

    //start finger tap test
    //sends request code 2
    public void startFingerTapTest(View view) {
        Intent startFingerTapTest = new Intent(this, FingerTapTest.class);

        final int result = 2;
        startActivityForResult(startFingerTapTest, result);
    }

    //start toe tap test
    //sends request code 3
    public void startToeTapTest(View view) {
        Intent startToeTapTest = new Intent(this, ToeTapTest.class);

        final int result = 3;
        startActivityForResult(startToeTapTest, result);
    }

    public void startRegistration(View view) {
        Intent startRegistration = new Intent(this, NewUser.class);

        startActivity(startRegistration);
    }

    public void startVitals(View view) {
        Intent startVitals = new Intent(this, VitalsTest.class);


        startActivity(startVitals);
    }

    public void startSupplements(View view){
        Intent startSupplements = new Intent(this, SupplementsTest.class);

        startActivity(startSupplements);
    }

    public void startConsent(View view) {
        Intent startConsent = new Intent(this, ConsentForm.class);

        startActivity(startConsent);
    }

    public void shareFingerResults(View view){

        int[] rightFingerValues = historyFragment.getRightFingerValues();
        int[] leftFingerValues = historyFragment.getLeftFingerValues();

        String resultString = "Here is an overview of my finger tap performance during a small 40 second dexterity test: \n"+
                "\nRight Finger at 10 Seconds: " + rightFingerValues[0] +
                "\nRight Finger at 20 Seconds: " + rightFingerValues[1] +
                "\nRight Finger at 30 Seconds: " + rightFingerValues[2] +
                "\nRight Finger at 40 Seconds: " + rightFingerValues[3] +
                "\n\nLeft Finger at 10 Seconds: " + leftFingerValues[0] +
                "\nLeft Finger at 20 Seconds: " + leftFingerValues[1] +
                "\nLeft Finger at 30 Seconds: " + leftFingerValues[2] +
                "\nLeft Finger at 40 Seconds: " + leftFingerValues[3];

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"yourphysician@hospital.ie"});
        intent.putExtra(Intent.EXTRA_CC, new String[] {"datacenter@alsneversurrender.org"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "ALS iNVOLVE Finger Dexterity Results");
        intent.putExtra(Intent.EXTRA_TEXT, resultString);

        try {
            startActivity(Intent.createChooser(intent, "Share Your Finger Tap Results"));
        } catch (android.content.ActivityNotFoundException ex) {
            //do something else
        }



    }

    public void shareToeResults(View view){
        int[] toeTapValue = historyFragment.getToeTapValues();
        String resultString = "Here is an overview of my toe tap performance during a small 40 second dexterity test: \n" +
                "\nToe taps at 10 Seconds: " + String.valueOf(toeTapValue[0]) +
                "\nToe taps at 20 Seconds: " + String.valueOf(toeTapValue[1]) +
                "\nToe taps at 30 Seconds: " + String.valueOf(toeTapValue[2]) +
                "\nToe taps at 40 Seconds: " + String.valueOf(toeTapValue[3]);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"yourphysician@hospital.ie"});
        intent.putExtra(Intent.EXTRA_CC, new String[] {"datacenter@alsneversurrender.org"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "ALS iNVOLVE Toe Dexterity Results");
        intent.putExtra(Intent.EXTRA_TEXT, resultString);

        try {
            startActivity(Intent.createChooser(intent, "Share Your Toe Tap Results"));
        } catch (android.content.ActivityNotFoundException ex) {
            //do something else
        }
    }

    public void shareArmResults(View view){
        double lArmValue, rArmValue, lLegValue, rLegValue;

        lArmValue = historyFragment.getArmLvalue();
        rArmValue = historyFragment.getArmRvalue();
        lLegValue = historyFragment.getLegLvalue();
        rLegValue = historyFragment.getLegRvalue();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"yourphysician@hospital.ie"});
        intent.putExtra(Intent.EXTRA_CC, new String[] {"datacenter@alsneversurrender.org"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "ALS iNVOLVE Limb Mobility Results");
        String resultsString = "Here is an overview of my limb mobility performance recorded using my device's motion sensors: \n";

        if(lArmValue != 0){
            resultsString += "\nLeft Arm: " + lArmValue + " Degrees";
        }

        if(rArmValue != 0){
            resultsString += "\nRight Arm: " + rArmValue + " Degrees";
        }

        if(lLegValue !=0){
            resultsString += "\nLeft Leg: " + lLegValue + " Degrees";
        }

        if (rLegValue != 0) {
            resultsString += "\nRight Leg: " + rLegValue + " Degrees";
        }
        intent.putExtra(Intent.EXTRA_TEXT, resultsString);

        try {
            startActivity(Intent.createChooser(intent, "Share Your Limb Mobility Results"));
        } catch (android.content.ActivityNotFoundException ex) {
            //do something else
        }
    }
    //Getting results for each test

    /**
     * Activites that return data are processed here
     *
     * @param requestCode: code passed to function that starts the activity
     * @param resultCode
     * @param data: contains all data returned from a finished activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //user is logged in
        if(requestCode ==101){
            UID = data.getStringExtra("user");
            user = myDb.getUser(UID);

            android.app.FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, homeFragment);
            fragmentTransaction.commit();
        }

        //set results to display on home
        if(requestCode ==1) {

            TextView armRotationResults = (TextView) findViewById(R.id.arm_rotation_results_text_view);

            //getting and setting results for rotation test
            String[] rotationResults = data.getStringArrayExtra("rotationResult");

            if(Float.valueOf(rotationResults[0])== -10001){
                Toast.makeText(this, "Test Cancelled", Toast.LENGTH_SHORT).show();
                return;
            }

            rotationResultsStr = String.valueOf(rotationResults[0]);

            rotationAppendage = rotationResults[1];
            rotationSide = rotationResults[2];

            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            //set results on page
            armRotationResults.setText(rotationResultsStr);

            if(rotationResults[1].equals("Arm")){
                if(rotationResults[2].equals("Left")){
                    lArmResults = new ArmRotation(rotationResults[2], Float.valueOf(rotationResults[0]), currentDate);
                } else {
                    rArmResults = new ArmRotation(rotationResults[2], Float.valueOf(rotationResults[0]), currentDate);
                }
            } else {
                if(rotationResults[2].equals("Left")){
                    lLegResults = new ArmRotation(rotationResults[2], Float.valueOf(rotationResults[0]), currentDate);
                } else {
                    rLegResults = new ArmRotation(rotationResults[2], Float.valueOf(rotationResults[0]), currentDate);
                }
            }
        }

        //sets the results to display on the home fragment
        if(requestCode==2){

            TextView fingerTapTestResults = (TextView) findViewById(R.id.finger_tap_test_result_text_view);

            int[] tapResults = data.getIntArrayExtra("tapsResults");

            if(tapResults.length == 0){
                Toast.makeText(this, "Test Cancelled", Toast.LENGTH_SHORT).show();
                return;
            }

            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            fingerResults = new FingerTap(tapResults[0], tapResults[1], tapResults[2], tapResults[3], tapResults[4], tapResults[5], tapResults[6], tapResults[7], currentDate);

            leftTapsStr = String.valueOf(tapResults[6]);
            rightTapsStr = String.valueOf(tapResults[7]);

            fingerTapTestResults.setText("L-" + leftTapsStr + " R-" + rightTapsStr);
        }

        //set results to display on home fragment
        if(requestCode==3){
            TextView toeTapTestResults = (TextView) findViewById(R.id.toe_tap_results_text_view);

            //Contains taps_10, taps_20, taps_30, taps_total
            int[] tapResults = data.getIntArrayExtra("tapsResults");

            if(tapResults.length == 0){
                Toast.makeText(this, "Test Cancelled", Toast.LENGTH_SHORT).show();
                return;
            }

            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            toeResults = new ToeTap(tapResults[1], tapResults[2], tapResults[3], tapResults[4], currentDate);

            //set toe taps textview
            toeTapsStr = String.valueOf(tapResults[4]);
            toeTapTestResults.setText(toeTapsStr);
        }
    }

    public void testEncryption(View view) {
        EditText encryptString = (EditText) findViewById(R.id.test_encrypt_edittext);

        String toEncrypt = encryptString.getText().toString();

        ALSEncryption myencrypt = new ALSEncryption();

        String key = "ImAKey";

        byte[] array = myencrypt.encrypt(toEncrypt, key);

        Toast.makeText(getApplicationContext(), array.toString() + " Byte array", Toast.LENGTH_SHORT).show();

        String returned = myencrypt.decrypt(array);

        Toast.makeText(getApplicationContext(), returned, Toast.LENGTH_SHORT).show();

    }

    public String getRotationResultsStr(){
        return rotationResultsStr;
    }

    public String getLeftTapsStr(){
        return leftTapsStr;
    }

    public String getRightTapsStr(){
        return rightTapsStr;
    }

    public String getToeTapsStr(){
        return toeTapsStr;
    }
}