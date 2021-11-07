package org.firstinspires.ftc.teamcode.RobotComponents;

import com.arcrobotics.ftclib.util.LUT;

public class PowerLUT {

    double percentScaling(double power){
        if(power>0){
            if(power-.2<=.05) return 1.35;
            if(power-.3<=.05) return 1.26;
            if(power-.4<=.05) return 1.25;
            if(power-.5<=.05) return 1.20;
            if(power-.6<=.05) return 1.17;
            if(power-.7<=.05) return 1.15;
            if (power-.8<=.05) return 1.14;
            else return 1.14;
        }

        if(power<0){
            power= Math.abs(power);

            if(power-.2<=.05) return 1.36;
            if(power-.3<=.05) return 1.30;
            if(power-.4<=.05) return 1.23;
            if(power-.5<=.05) return 1.19;
            if(power-.6<=.05) return 1.19;
            if(power-.7<=.05) return 1.19;
            if (power-.8<=.05) return 1.19 ;
            if (power-.9 <= .05) return 1.19 ;
            else return 1.19;


        }
        return 1;
    }

}
