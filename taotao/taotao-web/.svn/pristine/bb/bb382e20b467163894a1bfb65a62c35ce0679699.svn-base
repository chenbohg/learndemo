package com.taotao.web.threadlocal;

import com.taotao.web.pojo.User;

public class UserThreadLocal{
    
    //存在当前线程中的user对象
    private static ThreadLocal<User> USER = new ThreadLocal<User>();
    
    /**
     * 将对象放到ThreadLocal中
     */

    public static void set(User user){
        USER.set(user);
    }
    
    /**
     * 从ThreadLocal中拿出user对象
     */
    public static User get(){
       return USER.get();
    }
}
