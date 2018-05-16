package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

public class PortalItem extends TbItem {

    public String[] getImages(){
        String image = this.getImage();
        if(image != null && !image.equals("")){
            String[] imgs = image.split(",");
            return imgs;
        }
        return null;
    }
}
