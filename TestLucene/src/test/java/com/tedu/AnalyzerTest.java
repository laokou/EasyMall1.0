package com.tedu;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

import java.io.StringReader;

public class AnalyzerTest {
    //测试方法：传入一个分词器和 一个字符串内容，输出分词结果
    public void  printTerm(Analyzer analyzer,String msg) throws Exception{
        //1.获取String流
        StringReader reader = new StringReader(msg);
        //2.tokenStream  分词器的计算结果
        //fields  某个doucument的域属,/KJ;.*-+9L,k:>{"?
        TokenStream tokenStream = analyzer.tokenStream("test", reader);
        tokenStream.reset();
        CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
        while(tokenStream.incrementToken()){
            System.out.println(attribute.toString());
        }
    }

    @Test
    public void test() throws Exception {
        String msg = "Hello world！本文版权归作者和博客园共有，欢迎转载，但未经作者同意必须保留此段声明";
        Analyzer a1 = new StandardAnalyzer(); //标准分词器，根据单词和单字切分
        Analyzer a2 = new SimpleAnalyzer(); //简单分词器，根据标点符号切分字词
        Analyzer a3 = new WhitespaceAnalyzer(); //空格分词器，空格切分空格句子
        Analyzer a4 = new SmartChineseAnalyzer(); //标准分词器，切分中文字词
        Analyzer a5 = new IKAnalyzer6x(); //IK分词器，切分英文单词
        System.out.println("---------------标准分词------------------");
        printTerm(a1,msg);
        System.out.println("---------------简单分词------------------");
        printTerm(a2,msg);
        System.out.println("---------------空格分词------------------");
        printTerm(a3,msg);
        System.out.println("---------------智能中文分词------------------");
        printTerm(a4,msg);
        System.out.println("---------------IK中文分词------------------");
        printTerm(a5,msg);
    }
}
