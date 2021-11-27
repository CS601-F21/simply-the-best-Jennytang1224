
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;


public class SentimentAnalysis {
    static StanfordCoreNLP pipeline;
    static List<String> stopwords;

    public static void init() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(properties);
    }


    public static void loadStopWords() throws IOException {
        stopwords = Files.readAllLines(Paths.get("data/stopwords.txt"));
    }

    public static List<String> removeStopWords(List<String> tokens){
        tokens.removeAll(stopwords);
        return tokens;
    }


    public static String analyze(String review) {
        int mainSentiment = 0;
        String type = "None";
        if (review != null && review.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(review);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
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