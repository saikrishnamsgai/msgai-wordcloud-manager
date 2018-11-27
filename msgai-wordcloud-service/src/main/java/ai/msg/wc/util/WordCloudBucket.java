package ai.msg.wc.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WordCloudBucket {

    private String word;
    private Long count;

    public WordCloudBucket(String word, Long count){
        this.word = word;
        this.count = count;
    }

}
