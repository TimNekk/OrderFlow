package timnekk.orderflow.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import timnekk.orderflow.auth.dto.AuthenticationResponse;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("within(timnekk.orderflow..*) && within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {
    }

    @Pointcut("within(timnekk.orderflow..*) && within(@org.springframework.stereotype.Service *)")
    public void serviceMethods() {
    }

    @Before("controllerMethods()")
    public void logBeforeControllerMethod(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        log.info("Entering into Controller method: {} of Class: {}", methodName, className);
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfterControllerMethod(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        boolean containJwtToken = result instanceof ResponseEntity || result instanceof AuthenticationResponse;
        if (containJwtToken) {
            return;
        }

        log.info("Controller method: {} of Class: {} returned: {}", methodName, className, result);
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "error")
    public void logAfterControllerThrows(JoinPoint joinPoint, Throwable error) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        log.error("Exception in Controller method: {} of Class: {} Exception Message: {}", methodName, className, error.getMessage());
    }

    @Before("serviceMethods()")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        log.info("Entering into Service method: {} of Class: {}", methodName, className);
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterServiceMethod(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        boolean containJwtToken = methodName.equals("generateToken") || result instanceof AuthenticationResponse;
        if (containJwtToken) {
            return;
        }

        log.info("Service method: {} of Class: {} returned: {}", methodName, className, result);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "error")
    public void logAfterServiceThrows(JoinPoint joinPoint, Throwable error) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        log.error("Exception in Service method: {} of Class: {} Exception Message: {}", methodName, className, error.getMessage());
    }

}