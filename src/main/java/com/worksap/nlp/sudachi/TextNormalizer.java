/*
 * Copyright (c) 2024 Works Applications Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.worksap.nlp.sudachi;

import java.util.List;

import com.worksap.nlp.sudachi.dictionary.Grammar;

/**
 * Text normalizer that is equivalent to the one applied in the
 * JapaneseTokenizer.
 */
public class TextNormalizer {
    Grammar grammar;
    List<InputTextPlugin> inputTextPlugins;

    /**
     * Create TextNormalizer based on the JapaneseDictionary.
     */
    public TextNormalizer(JapaneseDictionary dictionary) {
        this(dictionary.getGrammar(), dictionary.inputTextPlugins);
    }

    /**
     * Create TextNormalizer from a grammar and input text plugins.
     * 
     * Grammar must have CharCategory.
     */
    public TextNormalizer(Grammar grammar, List<InputTextPlugin> inputTextPlugins) {
        this.grammar = grammar;
        this.inputTextPlugins = inputTextPlugins;
    }

    /** Normalize given text */
    public String normalize(CharSequence text) {
        UTF8InputTextBuilder builder = new UTF8InputTextBuilder(text, grammar);
        for (InputTextPlugin plugin : inputTextPlugins) {
            plugin.rewrite(builder);
        }
        UTF8InputText input = builder.build();
        return input.getText();
    }
}
