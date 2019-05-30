package webint.woodpecker.acquisition.springconfig

//
//
//class ElasticsearchConfig {
//
//    @Value("\${spring.elasticsearch.host}")
//    private val elasticsearchHost: String? = null
//
//    @Bean(destroyMethod = "close")
//    fun client(): RestHighLevelClient {
//        return RestHighLevelClient(
//                RestClient.builder(HttpHost(elasticsearchHost!!)))
//
//    }
//
//}
