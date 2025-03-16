package com.ruoyi.websocket.mapper;

import com.ruoyi.websocket.domain.CexAnn;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2025-03-15
 */
@Mapper
@Repository
public interface CexAnnMapper {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public CexAnn selectCexAnnById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param cexAnn 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<CexAnn> selectCexAnnList(CexAnn cexAnn);

    /**
     * 新增【请填写功能名称】
     *
     * @param cexAnn 【请填写功能名称】
     * @return 结果
     */
    public int insertCexAnn(CexAnn cexAnn);

    /**
     * 修改【请填写功能名称】
     *
     * @param cexAnn 【请填写功能名称】
     * @return 结果
     */
    public int updateCexAnn(CexAnn cexAnn);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteCexAnnById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCexAnnByIds(Long[] ids);

    CexAnn selectCexAnnByExchangeIdAndChannelAndTimeMs(String exchangeId, String channel, long timeMs);
}
