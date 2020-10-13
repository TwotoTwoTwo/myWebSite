package cn.wsjiu.nineSite.aop;

import cn.wsjiu.nineSite.dao.RedisCacheDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
public class LogAop{
    @Autowired
    private RedisCacheDao rcd;
    private static Logger logger = LogManager.getRootLogger();
    @EventListener
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof ContextRefreshedEvent) {
            logger.info("Server begin");
        }else if(applicationEvent instanceof ContextClosedEvent){
            logger.info("Server end");
        }
    }

    // 记录异常退出，方便溯源
    @AfterThrowing(value = "execution(* cn.wsjiu.nineSite.controller..*.*(..))",throwing = "e")
    public void logError(JoinPoint jp,Throwable e){
        MethodSignature ms=(MethodSignature) jp.getSignature();
        String mn= ms.getName();
        Method method=ms.getMethod();
        Class[] types=method.getParameterTypes();
        String error=mn+" 发生异常："+"参数类型和值如下";
        Object[] v = jp.getArgs();
        for(int i=0;i<v.length;i++){
            error = error +"\n"+types[i].getTypeName()+":"+v[i];
        }
        logger.error(error);
    }

    @After(value = "execution(* cn.wsjiu.nineSite.controller..InfoController.accessWebSite(..))&&args(request)")
    // 记录不同的ip（有一定误差，基于hyperloglog）
    // 记录访问量
    public void recordVistorsAndIp(HttpServletRequest request){
        String ip=request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        }
        // hyperloglog插入基数改变返回1，不改变返回0.记录ip
        long isChange=rcd.recordAccessIp(request.getRemoteAddr());
        if(isChange>0){
            logger.info(ip);
        }
        // 记录网站访问量
        long vistors=rcd.recordVistors();
    }


}
