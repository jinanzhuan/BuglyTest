package com.ljn.buglysimple.calendar;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2017/12/11
 *     desc   : 作为图片等url的管理类
 *     modify :
 * </pre>
 */

public class ImageUrlManager {
    private static ImageUrlManager instance;
    private ImageUrlManager(){}
    public static synchronized ImageUrlManager getInstance(){
        if(instance == null) {
            instance = new ImageUrlManager();
        }
        return instance;
    }

    /**
     * 获得ecg缩略图的图片地址
     * @param userHuid
     * @param ecgHuid
     * @return
     */
    public String getEcgThumbUrl(String userHuid, String ecgHuid){
        return "http://dtr-test.oss-cn-beijing.aliyuncs.com/" + "ecg/" + userHuid + "/" + ecgHuid + ".jpg";
    }
}
