package com.itheima.reggie.common;


/**
 *打包ThreadLocal的方法，易用与项目
 * @method setCurrentId
 * @Method getCurrentId
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal= new ThreadLocal<>();

    /**
     * 设置id
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取id
     */
    public static Long getCurrentId(){
         return threadLocal.get();
    }

}
