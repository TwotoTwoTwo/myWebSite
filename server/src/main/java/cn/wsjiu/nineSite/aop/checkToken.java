package cn.wsjiu.nineSite.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


@Aspect
public class checkToken {
    @Before(value = "execution(* cn.wsjiu.nineSite.controller..*.*(..))")
    public boolean check(){
        System.out.println("check");
        return false;
    }
}
