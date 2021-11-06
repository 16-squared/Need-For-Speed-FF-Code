package org.firstinspires.ftc.teamcode.RobotComponents;

import com.arcrobotics.ftclib.util.LUT;

public class PowerLUT {

    LUT<Double, Double> percentScaling = new LUT<Double, Double>()
    {{
        //backwards
        add(-.1, 0.0);

        //forwards
        add(.1, 0.0);//todo tune
    }};

}
