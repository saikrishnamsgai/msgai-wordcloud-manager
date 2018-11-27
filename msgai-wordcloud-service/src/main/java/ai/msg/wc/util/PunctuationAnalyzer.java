package ai.msg.wc.util;

import lombok.Getter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.util.Version;

import java.util.regex.Pattern;

@Getter
public class PunctuationAnalyzer {

    public static PunctuationAnalyzer instance = new PunctuationAnalyzer();

    private Analyzer analyzer;

    private PunctuationAnalyzer() {
        this.analyzer = new EnglishAnalyzer();
        this.analyzer.setVersion(Version.LATEST);
    }

}
