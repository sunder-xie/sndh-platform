package modules;


import com.google.inject.Provides;
import com.google.inject.name.Names;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import play.db.Database;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import services.data.MessageMapper;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;

/**
 * Created by Administrator on 2016/4/26.
 */
public class AppModule extends org.mybatis.guice.MyBatisModule {
    @Override
    protected void initialize() {
        environmentId("development");
        bindConstant().annotatedWith(Names.named("mybatis.configuration.failFast")).to(true);
        bindDataSourceProviderType(PlayDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        addMapperClass(MessageMapper.class);
    }

    @Singleton
    public static class PlayDataSourceProvider implements Provider<DataSource> {
        final Database db;
        @Inject
        public PlayDataSourceProvider(final Database db) {
            this.db = db;
        }

        @Override
        public DataSource get() {
            return db.getDataSource();
        }
    }
}
