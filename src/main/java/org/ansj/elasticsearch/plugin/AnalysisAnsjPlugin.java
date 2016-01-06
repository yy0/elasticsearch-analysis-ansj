package org.ansj.elasticsearch.plugin;

import org.ansj.elasticsearch.indecs.analysis.AnsjAnalysisModule;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.Plugin;

import java.util.Collection;
import java.util.Collections;

public class AnalysisAnsjPlugin extends Plugin {

    @Override
    public String name() {
        return "analysis-ansj";
    }


    @Override
    public String description() {
        return "ansj analysis";
    }

    @Override
    public Collection<Module> nodeModules() {
        return Collections.<Module>singletonList(new AnsjAnalysisModule());
    }

}