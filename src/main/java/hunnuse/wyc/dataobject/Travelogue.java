package hunnuse.wyc.dataobject;

public class Travelogue {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column travelogue.doc_id
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    private Integer docId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column travelogue.source
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    private String source;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column travelogue.attractions
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    private String attractions;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column travelogue.atts_id
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    private String attsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column travelogue.keywords
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    private String keywords;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column travelogue.text
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    private String text;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column travelogue.doc_id
     *
     * @return the value of travelogue.doc_id
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    public Integer getDocId() {
        return docId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column travelogue.doc_id
     *
     * @param docId the value for travelogue.doc_id
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column travelogue.source
     *
     * @return the value of travelogue.source
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    public String getSource() {
        return source;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column travelogue.source
     *
     * @param source the value for travelogue.source
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column travelogue.attractions
     *
     * @return the value of travelogue.attractions
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    public String getAttractions() {
        return attractions;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column travelogue.attractions
     *
     * @param attractions the value for travelogue.attractions
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    public void setAttractions(String attractions) {
        this.attractions = attractions == null ? null : attractions.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column travelogue.atts_id
     *
     * @return the value of travelogue.atts_id
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    public String getAttsId() {
        return attsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column travelogue.atts_id
     *
     * @param attsId the value for travelogue.atts_id
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    public void setAttsId(String attsId) {
        this.attsId = attsId == null ? null : attsId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column travelogue.keywords
     *
     * @return the value of travelogue.keywords
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column travelogue.keywords
     *
     * @param keywords the value for travelogue.keywords
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column travelogue.text
     *
     * @return the value of travelogue.text
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    public String getText() {
        return text;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column travelogue.text
     *
     * @param text the value for travelogue.text
     *
     * @mbg.generated Thu Apr 23 21:49:41 CST 2020
     */
    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }
}