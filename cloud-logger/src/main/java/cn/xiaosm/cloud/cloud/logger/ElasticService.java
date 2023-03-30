package cn.xiaosm.cloud.cloud.logger;

import cn.hutool.core.util.IdUtil;
import cn.xiaosm.cloud.cloud.logger.entity.EsLog;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public record ElasticService(ElasticsearchClient client) {

    public void query(String index) throws IOException {
        CreateIndexResponse indexResponse = client.indices().create(c -> c.index(index));
        System.out.println(indexResponse);
    }

    public boolean insert(EsLog esLog) throws IOException {
        IndexResponse indexResponse = client.index(i -> i
            .index("log_operate")
            //设置id
            .id(IdUtil.simpleUUID())
            //传入user对象
            .document(esLog));
        System.out.println(indexResponse);
        return true;
    }

}