package com.taotao.service.impl;/**
 * Created by lenovo on 2018/2/28.
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author lenovo
 * @create 2018-02-28 9:07
 **/
@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    TbItemParamMapper itemParamMapper;

    @Override
    public EasyUIDataGridResult getItemParamList(int page, int rows) {
        // 分页处理
        PageHelper.startPage(page,rows);
        // 执行查询
        List<TbItemParam> itemList = itemParamMapper.selectItemParam();
        // 取分页信息
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(itemList);
        // 返回处理结果
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @Override
    public TaotaoResult getItemParamById(Long id) {
        TbItemParamExample itemParamExample = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = itemParamExample.createCriteria();
        criteria.andItemCatIdEqualTo(id);
        List<TbItemParam> itemParamsList = itemParamMapper.selectByExampleWithBLOBs(itemParamExample);
        // 判断是否查询到结构
        if(itemParamsList != null && itemParamsList.size() > 0){
            TbItemParam itemParam = itemParamsList.get(0);
            return TaotaoResult.ok(itemParam);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult saveItemParam(Long id, String paramData) {
        Date date = new Date();
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(id);
        itemParam.setParamData(paramData);
        itemParam.setCreated(date);
        itemParam.setUpdated(date);
        int insert = itemParamMapper.insert(itemParam);
        if(insert == 1){
            return TaotaoResult.ok();
        }
        return TaotaoResult.build(500,"添加失败");
    }

    @Override
    public TaotaoResult deleteItemParam(Long[] ids) {
        // 逆向工程查询条件对象
        TbItemParamExample itemParamExample = new TbItemParamExample();
        // 条件对象
        TbItemParamExample.Criteria criteria = itemParamExample.createCriteria();
        // 添加对应条件
        criteria.andIdIn(Arrays.asList(ids));
        // 执行删除
        int total = itemParamMapper.deleteByExample(itemParamExample);
        // 返回对应信息
        return TaotaoResult.ok(total);
    }
}
