/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author heymeowcat
 */

@Interceptor
public class EmployeeTrackingInterceptor {
    
    
    @AroundInvoke
    public Object aroundInvoke(InvocationContext context) throws Exception{
        
        System.out.println("Employee Function Executed");
        return context.proceed();
        
    }
    
}
