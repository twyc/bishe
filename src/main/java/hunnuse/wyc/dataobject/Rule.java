package hunnuse.wyc.dataobject;

public class Rule {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rule.id
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rule.atts
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    private String atts;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rule.occurence
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    private Integer occurence;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rule.size
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    private Integer size;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rule.id
     *
     * @return the value of rule.id
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rule.id
     *
     * @param id the value for rule.id
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rule.atts
     *
     * @return the value of rule.atts
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    public String getAtts() {
        return atts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rule.atts
     *
     * @param atts the value for rule.atts
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    public void setAtts(String atts) {
        this.atts = atts == null ? null : atts.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rule.occurence
     *
     * @return the value of rule.occurence
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    public Integer getOccurence() {
        return occurence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rule.occurence
     *
     * @param occurence the value for rule.occurence
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    public void setOccurence(Integer occurence) {
        this.occurence = occurence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rule.size
     *
     * @return the value of rule.size
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    public Integer getSize() {
        return size;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rule.size
     *
     * @param size the value for rule.size
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    public void setSize(Integer size) {
        this.size = size;
    }
}