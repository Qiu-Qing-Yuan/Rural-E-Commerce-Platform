package com.qfedu.fmmall;

import com.qfedu.fmmall.dao.ProductMapper;
import com.qfedu.fmmall.entity.ProductVO;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import java.util.List;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2023-01-15  8:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
public class ImportProductInfoIntoES {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testCreateIndex() throws IOException {
        //创建索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("fmmall_product_index");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.isAcknowledged());
    }

    @Test
    public void testImportData(){
        //1.从数据库查询数据
        List<ProductVO> productVOS = productMapper.selectProducts();
        System.out.println(productVOS.size());

        //2.将查询到的数据写入到ES
    }

}
