import com.opencsv.CSVWriter
import groovyx.net.http.optional.Download
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.nio.file.Paths

import static groovyx.net.http.HttpBuilder.configure

class TISSWebCrawler {

    private Document currentPage;
    private Element currentLink;
    private List<VersionData> versionDataList = new ArrayList<>();

    TISSWebCrawler requestHomePage() {
        String homePageURL = "https://www.gov.br/ans/pt-br"
        setCurrentPage(Jsoup.connect(homePageURL).get())
        return this
    }

    TISSWebCrawler navigateTo(URLs url) {
        setCurrentLink(getCurrentPage().getElementsByAttributeValue("href", url.getUrl()).first())
        setCurrentPage(Jsoup.connect(currentLink.attr("href")).get())
        return this
    }

    TISSWebCrawler downloadLatestCommunicationComponent() {
        File downloadDir = Paths.get("output/downloads").toFile()
        if (!downloadDir.exists()) downloadDir.mkdirs()

        String fileName = getCurrentLink().attr("href").substring(getCurrentLink().attr("href").lastIndexOf('/') + 1)
        File fileToDownload = new File(downloadDir, fileName)

        File downloadedFile = configure({
            request.uri = getCurrentLink().attr("href")
        }).get({
            Download.toFile(delegate, fileToDownload)
        }) as File

        downloadedFile.createNewFile()
        return this
    }

    TISSWebCrawler collectHistoricalData() {
        Element tableBody = getCurrentPage().select("table tbody").first()
        Elements rows = tableBody.getElementsByTag("tr")

        for (Element row : rows) {
            Elements columns = row.getElementsByTag("td")
            String competency = getTextFromElement(columns.get(0), "span")
            if (isBefore2016(competency)) continue

            String publication = getTextFromElement(columns.get(1), "span")
            String validityStart = getTextFromElement(columns.get(2), "span")
            versionDataList.add(new VersionData(competency, publication, validityStart))
        }

        return this
    }

    TISSWebCrawler saveHistoricalDataToCsv() {
        try (CSVWriter writer = new CSVWriter(new FileWriter("output/csv/VersoesHistoricas.csv"))) {
            String[] header = ["Competência", "Publicação", "Início de Vigência"]
            writer.writeNext(header)

            versionDataList.forEach { version ->
                String[] data = [version.getCompetency(), version.getPublication(), version.getValidityStart()]
                writer.writeNext(data)
            }
        } catch (IOException e) {
            e.printStackTrace()
        }

        return this
    }

    TISSWebCrawler downloadErrorTable() {
        File downloadDir = Paths.get("output/downloads").toFile()
        if (!downloadDir.exists()) downloadDir.mkdirs()

        String downloadLink = getCurrentPage().select('a[href$=".xlsx"]').first().attr("href")
        File fileToDownload = new File(downloadDir, "Tabela_Erros_Envio_ANS.xlsx")

        File downloadedFile = configure({
            request.uri = downloadLink
        }).get({
            Download.toFile(delegate, fileToDownload)
        }) as File

        downloadedFile.createNewFile()
        return this
    }

    private String getTextFromElement(Element element, String tag) {
        return Optional.ofNullable(element.getElementsByTag(tag).first()).orElse(element).text()
    }

    private boolean isBefore2016(String date) {
        try {
            String year = date.replaceAll("[^0-9]", "")
            return Integer.parseInt(year) < 2016
        } catch (NumberFormatException e) {
            throw new RuntimeException("Erro ao processar data: " + e.getMessage())
        }
    }

    Document getCurrentPage() {
        return currentPage
    }

    void setCurrentPage(Document page) {
        this.currentPage = page
    }

    Element getCurrentLink() {
        return currentLink
    }

    void setCurrentLink(Element link) {
        this.currentLink = link
    }

    List<VersionData> getVersionDataList() {
        return versionDataList
    }

    void setVersionDataList(List<VersionData> versionDataList) {
        this.versionDataList = versionDataList
    }
}
