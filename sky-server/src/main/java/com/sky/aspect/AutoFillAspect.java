package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * ClassName: AutoFilleAspect
 * Package: com.sky.aspect
 * Description:
 *自定义切面类，实现公共字段填充
 * @Author Kun Hu
 * @Create 6/6/24 13:08
 * @Version 1.0
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * aop point
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..))&& @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCUt(){}

    /**
     * notify:before
     */
    @Before("autoFillPointCUt()")
    public void autoFill(JoinPoint joinPoint){
        log.info(" begin autofill public properties......");
        // get the operation type
        MethodSignature joinPointSignature = (MethodSignature)joinPoint.getSignature();//get the signature
        AutoFill autoFill = joinPointSignature.getMethod().getAnnotation(AutoFill.class);//get the annotation object from method
        OperationType operationType = autoFill.value();//get the operation type
        //get the entity
        Object[] args = joinPoint.getArgs();
        if (args==null|| args.length==0){
            return;
        }
        Object entity = args[0];//get the entity
        // assign value to the public properties
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        // check
        if (operationType==OperationType.INSERT){
            // for 4 properties

            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                //use reflection to assign value
                setCreateTime.invoke(entity,now);
                setUpdateTime.invoke(entity,now);
                setCreateUser.invoke(entity,currentId);
                setUpdateUser.invoke(entity,currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        else if (operationType==OperationType.UPDATE){
            // for 2 properties

            try {

                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                //use reflection to assign value
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    }
}
