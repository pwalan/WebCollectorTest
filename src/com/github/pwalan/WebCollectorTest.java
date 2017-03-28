package com.github.pwalan;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Document;

/**
 * @author AlanP
 * @Date 2017/3/28
 */
public class WebCollectorTest extends BreadthCrawler {

    /**
     * 有参构造函数
     * @param crawlPath 维护爬虫信息的目录
     * @param autoParse 如果设为true，breadthcrawler会自动从进程中提取匹配规则的链接
     */
    public WebCollectorTest(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        this.addSeed("http://news.hfut.edu.cn/list-1-1.html");
        this.addRegex("http://news.hfut.edu.cn/show-.*html");
        this.addRegex("-.*\\.(jpg|png|gif).*");
        this.addRegex("-.*#.*");
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        String url = page.getUrl();
        if (page.matchUrl("http://news.hfut.edu.cn/show-.*html")) {
            Document doc = page.getDoc();

            String title = page.select("div[id=Article]>h2").first().text();
            String content = page.select("div#artibody", 0).text();

            System.out.println("URL:\n" + url);
            System.out.println("title:\n" + title);
            System.out.println("content:\n" + content);
        }
    }

    public static void main(String[] args) throws Exception {
        WebCollectorTest crawler = new WebCollectorTest("crawl", true);
        crawler.setThreads(50);
        crawler.setTopN(100);
        crawler.start(4);
    }

}
