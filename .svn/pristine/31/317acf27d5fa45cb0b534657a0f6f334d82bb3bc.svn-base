package com.mws.phoenix.web.functions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Beans {

    public static String getString(Object object, String property) {
        Object result = null;
        Class<?> clazz = object.getClass();
        try {
            Method m = clazz.getMethod("get" + property.substring(0, 1).toUpperCase() + property.substring(1), (Class<?>[])null);
            result = m.invoke(object, (Object[])null);
        } catch (SecurityException e) {
            result = "SecurityException";
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            result = "IllegalArgumentException";
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            result = "NoSuchMethodException";
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            result = "IllegalAccessException";
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            result = "InvocationTargetException";
            e.printStackTrace();
        }
         
        if (result == null) {
            return "null";
        } else {
            return result.toString();
        }
    }
    
    public static String toString(Object obj) {
        return obj.toString();
    }
    
    public static String classOf(Object obj) {
        return obj.getClass().getName();
    }
}
