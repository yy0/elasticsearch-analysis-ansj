package org.ansj.elasticsearch.index;

import org.ansj.elasticsearch.index.config.AnsjElasticConfigurator;
import org.ansj.lucene.util.AnsjTokenizer;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;

import static org.ansj.elasticsearch.index.config.AnsjElasticConfigurator.filter;
import static org.ansj.elasticsearch.index.config.AnsjElasticConfigurator.pstemming;

public class AnsjTokenizerFactory extends AbstractTokenizerFactory {

    @Inject
    public AnsjTokenizerFactory(Index index, Settings indexSettings, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        AnsjElasticConfigurator.init(indexSettings, settings);
    }

    @Override
    public Tokenizer create() {
        return new AnsjTokenizer(new IndexAnalysis(), filter, pstemming);
    }
}
