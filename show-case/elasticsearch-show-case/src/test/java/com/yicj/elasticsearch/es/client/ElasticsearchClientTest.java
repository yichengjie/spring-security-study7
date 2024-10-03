package com.yicj.elasticsearch.es.client;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Buckets;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.json.JsonData;
import com.yicj.elasticsearch.ElasticsearchApplication;
import com.yicj.elasticsearch.es.index.ContentsIndex;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = ElasticsearchApplication.class)
@AutoConfigureMockMvc
class ElasticsearchClientTest {

    @Autowired
    private ElasticsearchClient client ;

    @Test
    public void testSearch1 () throws IOException {
        // ID查询
        GetResponse<ContentsIndex> resp = client.get(
                getReq ->getReq.index("contents_index").id("7"), ContentsIndex.class);
        if (resp.found()){
            ContentsIndex contentsIndex = resp.source() ;
            System.out.println("contentsIndex-7："+contentsIndex);
        }
    }

    @Test
    public void testSearch2 () throws IOException {
        // 指定字段匹配
        SearchResponse<ContentsIndex> resp = client.search(searchReq -> searchReq.index("contents_index")
                .query(query -> query.match(field -> field
                        .field("createName").query("张三"))),ContentsIndex.class);
        printResp(resp);
    }

    @Test
    public void testSearch3 () throws IOException {
        // 组合查询：姓名和时间范围
        Query byName = MatchQuery.of(field -> field.field("createName").query("王五"))._toQuery();
        Query byTime = RangeQuery.of(field -> field.field("createTime")
                .gte(JsonData.of("2023-07-10T00:00:00"))
                .lte(JsonData.of("2023-07-12T00:00:00")))._toQuery();
        SearchResponse<ContentsIndex> resp = client.search(searchReq -> searchReq.index("contents_index")
                .query(query -> query.bool(boolQuery -> boolQuery.must(byName).must(byTime))),ContentsIndex.class);
        printResp(resp);
    }

    @Test
    public void testSearch4 () throws IOException {
        // 排序和分页，在14条数据中，根据ID倒序排列，从第5条往后取4条数据
        SearchResponse<ContentsIndex> resp = client.search(searchReq -> searchReq.index("contents_index")
                .from(5).size(4)
                .sort(sort -> sort.field(sortField -> sortField.field("id").order(SortOrder.Desc))),ContentsIndex.class);
        printResp(resp);
    }

    @Test
    public void testSearch5 () throws IOException {
        // 根据createId分组统计
        SearchResponse<ContentsIndex> resp = client.search(searchReq -> searchReq.index("contents_index")
                .aggregations("createIdGroup",agg -> agg.terms(term -> term.field("createId"))),ContentsIndex.class);
        Aggregate aggregate = resp.aggregations().get("createIdGroup");
        LongTermsAggregate termsAggregate = aggregate.lterms();
        Buckets<LongTermsBucket> buckets = termsAggregate.buckets();
        for (LongTermsBucket termsBucket : buckets.array()) {
            System.out.println(termsBucket.key() + " : " + termsBucket.docCount());
        }
    }

    @Test
    public void testSearch6 () throws IOException {
        // 查询最大的ID
        SearchResponse<ContentsIndex> resp = client.search(searchReq -> searchReq.index("contents_index")
                .aggregations("maxId",agg -> agg.max(field -> field.field("id"))),ContentsIndex.class);
        for (Map.Entry<String, Aggregate> entry : resp.aggregations().entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue().max().value());
        }
    }

    @Test
    public void testSearch7 () throws IOException {
        // 模糊查询title字段，允许1个误差
        Query byContent = FuzzyQuery.of(field -> field.field("title").value("设计").fuzziness("1"))._toQuery();
        SearchResponse<ContentsIndex> resp = client.search(
                searchReq -> searchReq.index("contents_index").query(byContent),ContentsIndex.class);
        printResp(resp);
    }

    private void printResp (SearchResponse<ContentsIndex> resp){
        TotalHits total = resp.hits().total();
        System.out.println("total："+total);
        List<Hit<ContentsIndex>> hits = resp.hits().hits();
        for (Hit<ContentsIndex> hit: hits) {
            ContentsIndex contentsIndex = hit.source();
            System.out.println(hit.id()+"："+contentsIndex);
        }
    }
}
