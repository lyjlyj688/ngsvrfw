package person.lyjyy.common.boot;

/**
 * Created by yujie.li on 14-7-20.
 */
public class BootStart {
    public static void main(String[] arg) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        IBoot boot = (IBoot) BootStart.class.getClassLoader().loadClass(arg[1]).newInstance();
        boot.start();
    }
}
