package hunnuse.wyc.dao;

import hunnuse.wyc.dataobject.WebGeoName;

import java.util.List;

public interface WebGeoNameMapper {
    List<WebGeoName> listAttraction();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table webgeoname
     *
     * @mbg.generated Tue Apr 14 18:19:59 CST 2020
     */
    int deleteByPrimaryKey(Integer webgeonameid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table webgeoname
     *
     * @mbg.generated Tue Apr 14 18:19:59 CST 2020
     */
    int insert(WebGeoName record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table webgeoname
     *
     * @mbg.generated Tue Apr 14 18:19:59 CST 2020
     */
    int insertSelective(WebGeoName record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table webgeoname
     *
     * @mbg.generated Tue Apr 14 18:19:59 CST 2020
     */
    WebGeoName selectByPrimaryKey(Integer webgeonameid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table webgeoname
     *
     * @mbg.generated Tue Apr 14 18:19:59 CST 2020
     */
    int updateByPrimaryKeySelective(WebGeoName record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table webgeoname
     *
     * @mbg.generated Tue Apr 14 18:19:59 CST 2020
     */
    int updateByPrimaryKey(WebGeoName record);
}