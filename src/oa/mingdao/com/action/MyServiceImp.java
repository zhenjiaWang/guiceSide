package oa.mingdao.com.action;

/**
 * Created by zhenjiaWang on 16/6/22.
 */
public class MyServiceImp implements MyService {
    @Override
    public void echo(String msg) {
        System.out.println("hello "+msg);
    }
}
