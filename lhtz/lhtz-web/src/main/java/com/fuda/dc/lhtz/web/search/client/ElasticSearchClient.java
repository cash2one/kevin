package com.fuda.dc.lhtz.web.search.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;

import com.fuda.dc.lhtz.web.vo.IcbIndexInfo;

public class ElasticSearchClient {

    public static final String INDICES = "icbinfos";

    public static final String INDEX_TYPE = "icbinfo";

    public static final String HOST = "localhost";

    public static final int PORT = 9300;

    private final String host;

    private final int port;

    private TransportClient client;

    public ElasticSearchClient() {
        this(HOST, PORT);
    }

    public ElasticSearchClient(String host, int port) {
        this.host = host;
        this.port = port;
        init();
    }

    public void init() {
        client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(host, port));
        /*client = NodeBuilder.nodeBuilder().node().client();
        client = NodeBuilder.nodeBuilder().clusterName("yourclustername").node().client(); */
    }

    public void close() {
        client.close();
    }

    public void createMapping() throws IOException {
        client.admin().indices().prepareCreate(INDICES).execute().actionGet();
        XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject(INDICES)
                .startObject("properties").startObject("regNum").field("type", "string").field("store", "yes")
                .endObject().startObject("legalName").field("type", "string").field("store", "yes").endObject()
                .startObject("companyName").field("type", "string").field("store", "yes").endObject()
                .startObject("address").field("type", "string").field("store", "yes").endObject()
                .startObject("businessScope").field("type", "string").field("store", "yes").endObject().endObject()
                .endObject().endObject();

        PutMappingRequest mappingRequest = Requests.putMappingRequest(INDICES).type(INDEX_TYPE).source(mapping);
        client.admin().indices().putMapping(mappingRequest).actionGet();
    }

    public boolean createIndex(IcbIndexInfo icbIndexInfo) {
        try {
            client.prepareIndex(INDICES, INDEX_TYPE, icbIndexInfo.getRegNum())
                    .setSource(
                            XContentFactory.jsonBuilder().startObject().field("regNum", icbIndexInfo.getRegNum())
                                    .field("legalName", icbIndexInfo.getLegalName())
                                    .field("companyName", icbIndexInfo.getCompanyName())
                                    .field("address", icbIndexInfo.getAddress())
                                    .field("businessScope", icbIndexInfo.getBusinessScope()).endObject()).execute()
                    .actionGet();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean createIndices(List<IcbIndexInfo> icbIndexInfos) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (IcbIndexInfo icbIndexInfo : icbIndexInfos) {
            try {
                bulkRequest.add(client.prepareIndex(INDICES, INDEX_TYPE, icbIndexInfo.getRegNum()).setSource(
                        XContentFactory.jsonBuilder().startObject().field("regNum", icbIndexInfo.getRegNum())
                                .field("legalName", icbIndexInfo.getLegalName())
                                .field("companyName", icbIndexInfo.getCompanyName())
                                .field("address", icbIndexInfo.getAddress())
                                .field("businessScope", icbIndexInfo.getBusinessScope()).endObject()));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            System.out.println("create indices failed!");
            return false;
        }
        return true;
    }

    public List<String> search(String query) {
        List<String> searchResults = new ArrayList<String>();

        SearchRequestBuilder srBuilder = client.prepareSearch(INDICES);
        srBuilder.setTypes(INDEX_TYPE)
        //set indices
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                //search type: prisition search
                //.setQuery(QueryBuilders.termQuery("name", query))
                // Query words
                .setQuery(QueryBuilders.queryString(query))
                //.setPostFilter(FilterBuilders.rangeFilter("age").from(20).to(30)) // Filter
                .setFrom(0).setSize(20) //filter: search range, page
                .setExplain(true); //order by search match

        srBuilder.addHighlightedField("name").setHighlighterPreTags("<span style=\"color:red\">")
                .setHighlighterPostTags("</span>");

        SearchResponse response = srBuilder.execute().actionGet();

        SearchHits hits = response.getHits();

        for (int i = 0; i < hits.getHits().length; i++) {
            SearchHit hit = hits.getHits()[i];
            Map<String, SearchHitField> map = hit.fields();
            searchResults.add(hit.getSourceAsString());
            /*            Map<String, HighlightField> result = hit.getHighlightFields();
                        HighlightField titleField = result.get("name");
                        Text[] texts = titleField.fragments();
                        String title = "";
                        for (Text text : texts) {
                            title += text;
                        }*/
        }
        return searchResults;
    }

    public void delete(String indexId) {
        DeleteResponse response = client.prepareDelete(INDICES, INDEX_TYPE, indexId).setOperationThreaded(false)
                .execute().actionGet();
    }

    public void changeSetting(Map<String, String> settingMap) {
        Settings settings = ImmutableSettings.settingsBuilder().put(settingMap).build();
        NodeBuilder.nodeBuilder().settings(settings).node();
    }

}
