package com.ruoyi.websocket.service;

import java.util.List;
import com.ruoyi.websocket.domain.CexAnn;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2025-03-15
 */
public interface ICexAnnService 
{
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
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteCexAnnByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteCexAnnById(Long id);
}
