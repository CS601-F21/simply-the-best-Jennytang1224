package YelpSearch;

import HTTP.HTTPServerConstants;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Class to construct and run sentiment analysis
 */
public class SentimentAnalysis {
    static StanfordCoreNLP pipeline;
    static List<String> stopwords;

    public static void init() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(properties);
    }

    /**
     * load stopwords
     */
    public static void loadStopWords() throws IOException {
        stopwords = Files.readAllLines(Paths.get(HTTPServerConstants.STOPWORDS));
    }


    /**
     * remove stopwords
     * @param strArr
     * @return list of tokens
     */
    public static List<String> removeStopWords(String[] strArr){
        List<String> tokens = new LinkedList<String>(Arrays.asList(strArr));
        tokens.removeAll(stopwords);
        return tokens;
    }

    /**
     * analyze sentiment
     * @param review
     * @return type
     */
    public static String analyze(String review) {
        String type = "None";
        if (review != null && review.length() > 0) {
            Annotation annotation = pipeline.process(review);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                type = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            }
        }
        return type;
    }

    public static void main(String[] args) {
        init();
        System.out.println(analyze("big portions and some of the tastiest mahi mahi fish n chips I've had yet"));

    }

}