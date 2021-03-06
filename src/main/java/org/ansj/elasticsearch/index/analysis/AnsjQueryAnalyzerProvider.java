package org.ansj.elasticsearch.index.analysis;

import org.ansj.elasticsearch.index.config.AnsjElasticConfigurator;
import org.ansj.lucene5.AnsjQueryAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;

import static org.ansj.elasticsearch.index.config.AnsjElasticConfigurator.filter;
import static org.ansj.elasticsearch.index.config.AnsjElasticConfigurator.pstemming;

public class AnsjQueryAnalyzerProvider extends AbstractIndexAnalyzerProvider<Analyzer> {
    private final Analyzer analyzer;

    @Inject
    public AnsjQueryAnalyzerProvider(Index index, Settings indexSettings,
                                     Environment env, @Assisted String name,
                                     @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        AnsjElasticConfigurator.init(env, settings);
        analyzer = new AnsjQueryAnalysis(filter, pstemming);
    }

    public AnsjQueryAnalyzerProvider(Index index, Settings indexSettings, String name,
                                     Settings settings) {
        super(index, indexSettings, name, settings);
        AnsjElasticConfigurator.init(indexSettings, settings);
        analyzer = new AnsjQueryAnalysis(filter, pstemming);
    }

    @Override
    public Analyzer get() {
        return this.analyzer;
    }
}
