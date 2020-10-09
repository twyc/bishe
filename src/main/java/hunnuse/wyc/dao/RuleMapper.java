package hunnuse.wyc.dao;

import hunnuse.wyc.dataobject.Rule;
import hunnuse.wyc.dataobject.Travelogue;

import java.util.List;

public interface RuleMapper {
    List<Rule> listRule();
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    int insert(Rule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    int insertSelective(Rule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    Rule selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    int updateByPrimaryKeySelective(Rule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule
     *
     * @mbg.generated Tue May 12 14:49:31 CST 2020
     */
    int updateByPrimaryKey(Rule record);
}