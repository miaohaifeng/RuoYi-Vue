package com.ruoyi.websocket.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.websocket.mapper.CexAnnMapper;
import com.ruoyi.websocket.domain.CexAnn;
import com.ruoyi.websocket.service.ICexAnnService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-15
 */
@Service
public class CexAnnServiceImpl implements ICexAnnService 
{
    @Autowired
    private CexAnnMapper cexAnnMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public CexAnn selectCexAnnById(Long id)
    {
        return cexAnnMapper.selectCexAnnById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param cexAnn 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<CexAnn> selectCexAnnList(CexAnn cexAnn)
    {
        return cexAnnMapper.selectCexAnnList(cexAnn);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param cexAnn 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertCexAnn(CexAnn cexAnn)
    {
        return cexAnnMapper.insertCexAnn(cexAnn);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param cexAnn 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateCexAnn(CexAnn cexAnn)
    {
        cexAnn.setUpdateTime(DateUtils.getNowDate());
        return cexAnnMapper.updateCexAnn(cexAnn);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCexAnnByIds(Long[] ids)
    {
        return cexAnnMapper.deleteCexAnnByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCexAnnById(Long id)
    {
        return cexAnnMapper.deleteCexAnnById(id);
    }
}
