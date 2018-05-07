package com.example.frosty.als_involve_v3;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by troybrown on 11/21/17.
 */

public class ArmRotation {
    private int UID;
    public String armSide;
    public Float armRotation;
    public String armDate;

    public ArmRotation(){

    }

    protected ArmRotation(String armSide, Float armRotation, String armDate){
        this.armSide = armSide;
        this.armRotation = armRotation;
        this.armDate = armDate;
    }



    public ArmRotation createTestArmRotationL() {

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        return new ArmRotation("", 0f, "");
    }

    public ArmRotation createTestArmRotationR() {

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        return new ArmRotation("", 0f, "");
    }
}
