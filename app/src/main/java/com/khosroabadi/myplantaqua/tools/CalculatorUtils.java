package com.khosroabadi.myplantaqua.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alireza on 12/26/2016.
 */

public class CalculatorUtils {

    private Map<String , Double> BOTTOM_PANLE = new HashMap<>();


    private static BigDecimal getMainPanleBeta(Double lh){
        Double beta =  0.0;
        if(lh <0.5) {
            beta= 0.085;
        }else if (lh < 0.7){
            beta=  0.085;
        } else if (lh < 1.0){
            beta=  0.116;
        }else if (lh < 1.5){
            beta=  0.16;
        }else if (lh < 2.0){
            beta=  0.26;
        }else if (lh < 2.5){
            beta=  0.32;
        }else if (lh < 3.0){
            beta=  0.35;
        }else if (lh >= 3.0){
            beta=  0.37;
        }
return new BigDecimal(beta);
    }

    private static BigDecimal getButtomPanleBeta(Double lw){
        Double beta =  0.0;
        if(lw <=1.0) {
            beta= 0.453;
        }else if (lw < 1.5){
            beta=  0.453;
        } else if (lw < 2.0){
            beta=  0.5172;
        }else if (lw < 2.5){
            beta=  0.5688;
        }else if (lw < 3.0){
            beta=  0.6102;
        }else if (lw >=3.0){
            beta=  0.7134;
        }
        return new BigDecimal(beta);
    }



    public static Map<String ,  String> getDIY(Double height , Double width , Double length){
        Map<String , String> result =new HashMap<>(0);


        int mainGlassTickness = getMainGlassThickness(new BigDecimal(height),new BigDecimal(length)).intValue();
        int buttomGlassThickness = getButtomGlassThickness(new BigDecimal(length),new BigDecimal(height),new BigDecimal(width)).intValue();

        height = height/10;
        length = length/10;
        width = width/10;

        Double aquariumVolumeInLitr = new BigDecimal((length*width*height)/ 1000).setScale(2 ,BigDecimal.ROUND_HALF_UP).doubleValue();
        Double glassArea = new BigDecimal(getGlassArea(height , width , length)).setScale(3 , BigDecimal.ROUND_HALF_UP).doubleValue();
        Double glassWeidth = new BigDecimal(getGlassWeight(length,height,width,mainGlassTickness,buttomGlassThickness)).setScale(3 ,BigDecimal.ROUND_HALF_UP).doubleValue();

        Double aquariumWeight = aquariumVolumeInLitr + glassWeidth;

        result.put(ConstantManager.CALCULATOR.MAIN_GLASS_THICKNESS , String.valueOf(mainGlassTickness));
        result.put(ConstantManager.CALCULATOR.BUTTOM_GLASS_THICKNESS , String.valueOf(buttomGlassThickness));
        result.put(ConstantManager.CALCULATOR.AQUARIUM_VOLUME_LITER , aquariumVolumeInLitr.toString());
        result.put(ConstantManager.CALCULATOR.GLASS_AREA , glassArea.toString());
        result.put(ConstantManager.CALCULATOR.GLASS_WEIGHT , glassWeidth.toString());
        result.put(ConstantManager.CALCULATOR.AQUARIUM_WEIGHT , aquariumWeight.toString());

        return result;


    }


    private static BigDecimal getMainGlassThickness(BigDecimal height, BigDecimal length){

            BigDecimal result = BigDecimal.ZERO;
            final Double constantNo = 19.2;
            final Double constantNo2 = 0.00001;
            final Double safetyFactory = 3.8;
            BigDecimal ratioLH = length.divide(height, 2, RoundingMode.HALF_UP);
            Double mainGlassBeta = getMainPanleBeta(ratioLH.doubleValue()).setScale(3, BigDecimal.ROUND_CEILING).doubleValue();
            Double bendingStress = constantNo / safetyFactory;
            BigDecimal hPow3 = height.pow(3);
            Double betaMulHeight = mainGlassBeta * hPow3.doubleValue();
            Double sqrt = betaMulHeight * constantNo2 / bendingStress;
            sqrt = Math.sqrt(sqrt);
            result = new BigDecimal(sqrt).setScale(0 , BigDecimal.ROUND_UP);
            return result;

    }

    private static BigDecimal getButtomGlassThickness(BigDecimal length, BigDecimal height, BigDecimal width){
        BigDecimal result = BigDecimal.ZERO;
        final Double constantNo= 19.2;
        final Double constantNo2 = 0.00001;
        final Double safetyFactory = 3.8;
        BigDecimal ratioLW = length.divide(width, 2, RoundingMode.HALF_UP);
        Double mainGlassBeta = getButtomPanleBeta(ratioLW.doubleValue()).setScale(3 , BigDecimal.ROUND_CEILING).doubleValue();
        Double bendingStress = constantNo/safetyFactory;
        BigDecimal hPow3 = height.pow(3);
        Double betaMulHeight = mainGlassBeta * hPow3.doubleValue();
        Double sqrt =  betaMulHeight * constantNo2/bendingStress;
        sqrt = Math.sqrt(sqrt);
        result = new BigDecimal(sqrt).setScale(0 , BigDecimal.ROUND_UP);
        return result;
    }


    private static Double getGlassArea(Double length, Double height, Double width){

        height = height/100;
        length = length/100;
        width = width/100;

       return  (length*width)+((length*height)*2)+((length*width)*2);

    }

    private static Double getGlassWeight(Double length, Double height, Double width, int mainGlassTichkness, int buttomGlassThichness){

        height = height/100;
        length = length/100;
        width = width/100;
        Double mainGlassWeight = ((length* height * mainGlassTichkness * 2 ) + (height * width * mainGlassTichkness * 2));
        Double buttomGlassWight = (length*width * buttomGlassThichness);
        BigDecimal weight = new BigDecimal(mainGlassWeight).add(new BigDecimal(buttomGlassWight)).setScale(2 , BigDecimal.ROUND_CEILING);
        return  weight.doubleValue();

    }



}
