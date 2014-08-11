package person.lyjyy.core.factory;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.lyjyy.core.exception.FrameException;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by yujie.li on 14-7-21.
 */
public class BeanFactory {

    private final static Logger log = LoggerFactory.getLogger(BeanFactory.class);

    private Map<String,Object> beanMap = new HashMap<String, Object>();
    protected Around around;



    public BeanFactory(String dir,Around around) throws Exception{
        this.around = around;
        File[] list = new File(dir).listFiles();
        for(File file : list) {
            Properties p = new Properties();
            p.load(new FileInputStream(file));
            for(Object key : p.keySet()) {
                Object obj = around.around(Class.forName(p.getProperty((String) key)));
                beanMap.put((String) key,obj);
            }
            for(Object bean : beanMap.values()) {
                fixProperty(bean);
                checkCycSet(bean);
            }
        }
    }

    private void fixProperty(Object bean) throws IllegalAccessException {
        Field[] fieldList = bean.getClass().getDeclaredFields();
        for(Field f : fieldList) {
            if(beanMap.containsKey(f.getType().getName())) {
                f.set(bean,beanMap.get(f.getType().getName()));
            }
        }
    }

    private void checkCycSet(Object bean) {
        Field[] fieldList = bean.getClass().getDeclaredFields();
        for(Field f : fieldList) {
            Object obj = beanMap.get(f.getType().getName());
            if(obj == null) {
                log.warn("存在没有实例化的bean：" + f.getType().getName());
                continue;
            }
            Field[] fs = obj.getClass().getDeclaredFields();
            for(Field f2 : fs) {
                if(f2.getType().getName().equals(bean.getClass().getName())) {
                    throw new FrameException("存在循环依赖的bean：" + bean.getClass().getName());
                }
            }
        }
    }

    public Object getBean(String key) {
        return beanMap.get(key);
    }
}
