package cn.sijiu.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
public class checkToken {
    @Before(value = "execution(* cn.sijiu.controller..*.*(..))")
    public boolean check(){
        System.out.println("check");
        return false;
    }
}
