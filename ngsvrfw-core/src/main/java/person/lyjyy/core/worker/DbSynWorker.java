package person.lyjyy.core.worker;

import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.lyjyy.core.cache.DsCache;
import person.lyjyy.core.domain.DsCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yujie.li on 14-8-10.
 * db同步线程  此服务目前只有一个
 * 可以进行横向扩展
 * 需要按表散列 避免锁冲突
 */
public class DbSynWorker implements Runnable{

    private final static Logger log = LoggerFactory.getLogger(DbSynWorker.class);

    private Connection connection;

    private List<DsCommand> list = new ArrayList<DsCommand>();

    private Map<String,PreparedStatement> statMap = new ConcurrentHashMap<String, PreparedStatement>();

    private Object lock = new Object();

    private String test = "select 1";

    private Statement testState;

    private int maxLength = 100000;//最多堆积100000个事物

    public void add(List<DsCommand> batch) {
        synchronized (lock) {
            if(list.size() >= maxLength) {//读同步
                return;
            }
            list.addAll(batch);
        }
    }


    @Override
    public void run() {
        while(true) {
           try {
               boolean flag = testSql();
               if(!flag) {
                    resetSession();
               }
               if(connection != null) {
                   writeDb();
               }
               Thread.sleep(500);
           }catch (Exception e) {
                log.error("exec db syn error",e);
           }
        }
    }

    /**
     * 进行写入操作 写入失败即丢弃
     */
    private void writeDb() throws SQLException {
        List<DsCommand> tlist = null;
        synchronized (lock) {
            tlist = new ArrayList<DsCommand>(list.size());
            tlist.addAll(list);
            list.clear();
        }
        int count = 0;
        connection.setAutoCommit(false);
        for(DsCommand comm : tlist) {
            try {
                count++;
                MappedStatement mst = DsCache.cache.getMappedStatement(comm.getSql());
                Object[] params = mst.getParameterMap().getParameterObjectValues(null,comm.getObj());
                PreparedStatement ps = getPreparedStatement(comm.getSql(),mst.getSql().getSql(null,params));
                exetueOne(ps,params);
                if(count > 5000) {
                    connection.commit();
                }
            }catch (Exception e) {
                log.error("exec sql error:" + comm,e);
            }
        }
    }

    private PreparedStatement getPreparedStatement(String sqlId,String sql) throws SQLException {
        if(statMap.containsKey(sqlId)) {
            return statMap.get(sqlId);
        }
        PreparedStatement ps = connection.prepareStatement(sql);
        statMap.put(sqlId,ps);
        return ps;
    }

    private void exetueOne(PreparedStatement ps,Object[] obj) throws SQLException {
        for(int i = 0; i < obj.length; i++) {
            if(obj[i] instanceof String) {
                ps.setString(i + 1, (String) obj[i]);
            }else if(obj[i] instanceof Integer || obj[i].getClass().equals(int.class)) {
                ps.setInt(i + 1, ((Integer)obj[i]).intValue());
            }else if(obj[i] instanceof Short || obj[i].getClass().equals(short.class)) {
                ps.setShort(i + 1, ((Short)obj[i]).shortValue());
            }else if(obj[i] instanceof Long || obj[i].getClass().equals(long.class)) {
                ps.setLong(i + 1, ((Long)obj[i]).longValue());
            }else if(obj[i] instanceof Byte || obj[i].getClass().equals(byte.class)) {
                ps.setByte(i + 1,((Byte)obj[i]).byteValue());
            }else if(obj[i] instanceof Float || obj[i].getClass().equals(float.class)) {
                ps.setFloat(i + 1,((Float)obj[i]).floatValue());
            }else if(obj[i] instanceof Double || obj[i].getClass().equals(double.class)) {
                ps.setDouble(i + 1, ((Double)obj[i]).doubleValue());
            }else if(obj[i] instanceof byte[]) {
                ps.setBytes(i + 1, (byte[]) obj[i]);
            }else {
                throw new IllegalArgumentException("错误的数据类型" + obj[i]);
            }
            ps.execute();
        }
    }

    private boolean testSql() {
        try {
            if(testState == null) {
                testState = connection.createStatement();
            }
            testState.execute(test);
            return true;
        }catch (Exception e) {
            testState = null;
            log.error("test db error",e);
            return false;
        }
    }

    private void resetSession() {
        try {
            connection = DsCache.cache.getConnection();
        } catch (SQLException e) {
            log.error("create db conn error",e);
            connection = null;
        }
    }

    public static void main(String[] arg) {
        Object[] obj = new Object[10];
        obj[0] = 1;
        obj[1] = Integer.valueOf(1);
        System.out.println(((Integer)obj[0]).intValue());
        System.out.println(int.class);
    }
}
