package person.lyjyy.core.boot;

/**
 * Created by yujie.li on 14-7-20.
 */
public class BootStart {
    public static void main(String[] arg) throws Throwable {
        IBoot boot = (IBoot) BootStart.class.getClassLoader().loadClass(arg[1]).newInstance();
        boot.start();
    }
}
