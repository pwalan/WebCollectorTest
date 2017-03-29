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
     * @param autoParse 如果设为true，爬虫会自动从进程中提取匹配规则的链接
     */
    public WebCollectorTest(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        //种子页面
        this.addSeed("http://news.hfut.edu.cn/list-1-1.html");

        //正则爬取规则的设置
        //爬取符合下面这条规则的URL
        this.addRegex("http://news.hfut.edu.cn/show-.*html");
        //不爬取jpg、png、gif
        this.addRegex("-.*\\.(jpg|png|gif).*");
        //不爬取带#的URL
        this.addRegex("-.*#.*");
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        String url = page.getUrl();
        if (page.matchUrl("http://news.hfut.edu.cn/show-.*html")) {
            Document doc = page.getDoc();

            String title = page.select("div[id=Article]>h2").first().text();
            String content = page.select("div#artibody", 0).text();

            System.out.println("URL:\n" + url);
            System.out.println("title:\n" + title);
            System.out.println("content:\n" + content);

            //添加新的爬取任务
            //next.add("http://xxxxxx.com");
        }
    }

    public static void main(String[] args) throws Exception {
        WebCollectorTest crawler = new WebCollectorTest("crawl", true);
        //设置线程数
        crawler.setThreads(50);
        //设置每次迭代中爬取数量的上限
        crawler.setTopN(100);
        /*设置是否为断点爬取，如果设置为false，任务启动前会清空历史数据。
           如果设置为true，会在已有crawlPath(构造函数的第一个参数)的基础上继
           续爬取。对于耗时较长的任务，很可能需要中途中断爬虫，也有可能遇到
           死机、断电等异常情况，使用断点爬取模式，可以保证爬虫不受这些因素
           的影响，爬虫可以在人为中断、死机、断电等情况出现后，继续以前的任务
           进行爬取。断点爬取默认为false*/
        //crawler.setResumable(true);
        //设置爬取深度
        crawler.start(4);
    }

}
