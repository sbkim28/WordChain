package com.ignited.wordchain.word;

import java.util.List;
import java.util.Set;

public interface WordFilter<T> {

    Set<String> filter(List<T> words);

}
