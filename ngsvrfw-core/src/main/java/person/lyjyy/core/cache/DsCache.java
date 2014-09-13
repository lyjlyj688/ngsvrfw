package person.lyjyy.core.cache;

import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.client.SqlMapSession;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import person.lyjyy.core.domain.DsCommand;
import person.lyjyy.core.domain.DsCommandBatch;
import person.lyjyy.core.domain.RemoteObj;
import person.lyjyy.core.util.PathUtil;
import person.lyjyy.core.worker.DbSynWorker;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by yujie.li on 14-8-10.
 */
public class DsCache {
    private DataSource dataSource;
    private SqlMapClientImpl sqlMapClient;
    private DbSynWorker worker;
    private BaseCache<RemoteObj> baseCache;

    public static DsCache cache;





    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public MappedStatement getMappedStatement(String sqlId) {
        return sqlMapClient.getMappedStatement(sqlId);
    }

    public SqlMapSession getSession() throws SQLException {
        return sqlMapClient.openSession(dataSource.getConnection());
    }

    public void updateCache(DsCommandBatch batch) {
        List<DsCommand> list = batch.getCommandList();
        for(DsCommand command : list) {
            baseCache.updateObj(command.getObj());
        }
        worker.add(list);
    }


    public DsCache(String sqlMapConfig, String dbConfig) throws Exception {
        sqlMapClient = (SqlMapClientImpl) SqlMapClientBuilder.buildSqlMapClient(new FileInputStream(new File(sqlMapConfig)));
        initDataSource(dbConfig);
        baseCache = new BaseCache<RemoteObj>();
        worker = new DbSynWorker();
        Thread t = new Thread(worker);
        t.setDaemon(true);
        t.setName("dbSynWorker");
        t.start();
    }

    /**
     * 初始化连接池
     * @param configFile
     */
    private void initDataSource(String configFile) throws Exception {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        Properties p = new Properties();
        p.load(new FileInputStream(new File(PathUtil.getFilePath(configFile))));
        dataSource = dataSourceFactory.createDataSource(p);
    }
}
