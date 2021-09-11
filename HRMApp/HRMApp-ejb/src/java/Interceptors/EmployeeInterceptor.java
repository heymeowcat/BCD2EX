/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interceptors;

import EmployeeInfo.Beans.EmployeesFacade;
import EmployeeInfo.Beans.LoginFacade;
import EmployeeInfo.Entity.Employees;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author heymeowcat
 */
@Interceptor
public class EmployeeInterceptor {

    @EJB
    private EmployeesFacade employeesFacade;


    @AroundInvoke
    public Object aroundInvoke(InvocationContext context) throws Exception {
        Object[] or = context.getParameters();

        if (context.getMethod().getName().equals("saveEmployee")) {
            Employees empup = (Employees) or[0];

            List<Employees> searchEmployeesNIC = employeesFacade.searchEmployeesNIC(empup.getNic());
            if (searchEmployeesNIC.size() == 0) {  
                return context.proceed();
            } else {
                return "Invalid NIC";
            }

        }

        System.out.println("Employee Function Executed");
        return "Invalid Employee Details";

    }

}
