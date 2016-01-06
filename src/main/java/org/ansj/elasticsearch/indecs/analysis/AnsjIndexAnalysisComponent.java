package org.ansj.elasticsearch.indecs.analysis;

import org.ansj.elasticsearch.index.config.AnsjElasticConfigurator;
import org.ansj.lucene.util.AnsjTokenizer;
import org.ansj.lucene5.AnsjIndexAnalysis;
import org.ansj.lucene5.AnsjQueryAnalysis;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.analysis.AnalyzerScope;
import org.elasticsearch.index.analysis.PreBuiltAnalyzerProviderFactory;
import org.elasticsearch.index.analysis.PreBuiltTokenizerFactoryFactory;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;

import static org.ansj.elasticsearch.index.config.AnsjElasticConfigurator.filter;
import static org.ansj.elasticsearch.index.config.AnsjElasticConfigurator.pstemming;

/**
 * Registers indices level analysis components so, if not explicitly configured,
 * will be shared among all indices.
 */
public class AnsjIndexAnalysisComponent extends AbstractComponent {

    private static final String INDEX_ANALYSIS_NAME = "ansj_index";
    private static final String QUERY_ANALYSIS_NAME = "ansj_query";
    private static final String INDEX_TOKEN_NAME = "ansj_index_token";
    private static final String QUERY_TOKEN_NAME = "ansj_query_token";

    @Inject
    public AnsjIndexAnalysisComponent(final Settings settings,
                                      IndicesAnalysisService indicesAnalysisService, Environment env) {
        super(settings);
        AnsjElasticConfigurator.init(env, settings);

        indicesAnalysisService.analyzerProviderFactories().put(INDEX_ANALYSIS_NAME,
                new PreBuiltAnalyzerProviderFactory(INDEX_ANALYSIS_NAME, AnalyzerScope.GLOBAL,
                        new AnsjIndexAnalysis(filter, pstemming)));

        indicesAnalysisService.analyzerProviderFactories().put(QUERY_ANALYSIS_NAME,
                new PreBuiltAnalyzerProviderFactory(QUERY_ANALYSIS_NAME, AnalyzerScope.GLOBAL,
                        new AnsjQueryAnalysis(filter, pstemming)));

        indicesAnalysisService.tokenizerFactories().put(INDEX_TOKEN_NAME,
                new PreBuiltTokenizerFactoryFactory(new TokenizerFactory() {
                    @Override
                    public String name() {
                        return INDEX_TOKEN_NAME;
                    }

                    @Override
                    public Tokenizer create() {
                        return new AnsjTokenizer(new IndexAnalysis(), filter, pstemming);
                    }
                }));

        indicesAnalysisService.tokenizerFactories().put(QUERY_TOKEN_NAME,
                new PreBuiltTokenizerFactoryFactory(new TokenizerFactory() {
                    @Override
                    public String name() {
                        return QUERY_TOKEN_NAME;
                    }

                    @Override
                    public Tokenizer create() {
                        return new AnsjTokenizer(new ToAnalysis(), filter, pstemming);
                    }
                }));
    }
}