package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface StaticPageService {

    TaotaoResult getItemHtml(Long itemId) throws IOException, TemplateException;
}
