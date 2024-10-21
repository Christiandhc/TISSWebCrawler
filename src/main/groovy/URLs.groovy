enum URLs {

    LATEST_COMMUNICATION_COMPONENT("https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/PadroTISSComunicao202301.zip"),

    PRESTADORES_PAGE("https://www.gov.br/ans/pt-br/assuntos/prestadores"),
    TISS_PAGE("https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss"),
    TISS_LATEST_VERSION_PAGE("https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/setembro-2024"),
    HISTORICAL_VERSIONS_PAGE("https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-historico-das-versoes-dos-componentes-do-padrao-tiss"),
    RELATED_TABLES_PAGE("https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-tabelas-relacionadas");

    final String url

    URLs(String url) {
        this.url = url
    }

    String getUrl() {
        return url
    }
}
