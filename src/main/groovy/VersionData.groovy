class VersionData {

    String competency;
    String publication;
    String validityStart;

    VersionData(String competency, String publication, String validityStart) {
        this.competency = competency
        this.publication = publication
        this.validityStart = validityStart
    }

    String getCompetency() {
        return competency
    }

    void setCompetency(String competency) {
        this.competency = competency
    }

    String getPublication() {
        return publication
    }

    void setPublication(String publication) {
        this.publication = publication
    }

    String getValidityStart() {
        return validityStart
    }

    void setValidityStart(String validityStart) {
        this.validityStart = validityStart
    }
}
