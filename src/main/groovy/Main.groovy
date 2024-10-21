public class Main {

    static TISSWebCrawler tissCrawler = new TISSWebCrawler()

    public static void main(String[] args) {

        tissCrawler.requestHomePage()
                .navigateTo(URLs.PRESTADORES_PAGE)
                .navigateTo(URLs.TISS_PAGE)
                .navigateTo(URLs.TISS_LATEST_VERSION_PAGE)
                .downloadLatestCommunicationComponent()

        tissCrawler.requestHomePage()
                .navigateTo(URLs.PRESTADORES_PAGE)
                .navigateTo(URLs.TISS_PAGE)
                .navigateTo(URLs.HISTORICAL_VERSIONS_PAGE)
                .collectHistoricalData()
                .saveHistoricalDataToCsv()

        tissCrawler.requestHomePage()
                .navigateTo(URLs.PRESTADORES_PAGE)
                .navigateTo(URLs.TISS_PAGE)
                .navigateTo(URLs.RELATED_TABLES_PAGE)
                .downloadErrorTable()

    }
}
