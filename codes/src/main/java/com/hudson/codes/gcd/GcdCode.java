package com.hudson.codes.gcd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2020/6/13.
 */
public class GcdCode {

    public static void main(String[] args){
        int gcd = findGCD3(7, -5);
        System.out.print("result: "+gcd);
    }

    //解法一
    public static int findGCD(int a, int b){
        if(a == 0 || b == 0){
            throw new IllegalArgumentException("a="+a + "and b="+b+" is invalid");
        }
        a = Math.abs(a);
        b = Math.abs(b);
        int gcd = Math.min(a,b);
        while(gcd > 1){
            if(a % gcd == 0 && b % gcd == 0){
                return gcd;
            }
            gcd --;
        }
        return gcd;
    }

    // 解法二
    public static int findGCD2(int a, int b){
        if(a == 0 || b == 0){
            throw new IllegalArgumentException("a="+a + "and b="+b+" is invalid");
        }
        a = Math.abs(a);
        b = Math.abs(b);
        List<Integer> first = getPrimeFactors(a);
        List<Integer> second = getPrimeFactors(b);

        //找到公共因数，例如
        //36和 9
        // 36 = 2 * 2 * 3 * 3
        // 9 = 3 * 3
        //公共部分是 3 * 3，因此最大公约数是9
        //因此每找到一个，累积该因数，并且两个集合同时排除该因数
        int gcd = 1;
        int tmpJ = 0;
        for (int i = 0; i < first.size(); i++) {
            for (int j = tmpJ; j < second.size(); j++) {
                if(first.get(i) == second.get(j)){
                    gcd *= first.get(i);

                    //由于外层的循环往后会直接排除前面的因数，因此只需要移除后一个集合
                    second.remove(j);
                    //由于我们因数是从小到大排列的，因此后续从此位置开始找开始找
                    tmpJ = j;
                    break;
                }
            }
        }
        return gcd;
    }

    //拆分质因数，因数是从小到大插入有序集合中
    private static List<Integer> getPrimeFactors(int target){
        List<Integer> factors = new ArrayList<>();
        int factor = 2;
        while(factor <= target){
            if(target % factor == 0){
                factors.add(factor);
                target /= factor;//去除该因数

                factor = 2;//重新从2开始计算。
            }else{
                factor ++;
            }
        }
        return factors;
    }

    //解法三
    public static int findGCD3(int a, int b){
        if(a == 0 || b == 0){
            throw new IllegalArgumentException("a="+a + "and b="+b+" is invalid");
        }
        a = Math.abs(a);
        b = Math.abs(b);
        int min = Math.min(a,b);
        int max = Math.max(a,b);
        int mod;
        while(true){
            mod = max % min;
            if(mod == 0){
                return min;
            }
            max = min;
            min = mod;
        }
    }

}
