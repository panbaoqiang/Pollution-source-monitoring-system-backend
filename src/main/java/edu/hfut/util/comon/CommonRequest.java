package edu.hfut.util.comon;
/**
 * @author panbaoqiang
 * @Description 公共请求体
 * @create 2020-04-27 22:48
 */
public class CommonRequest <T>{
    /**
     * 操作系统平台：navigator.platform
     */
    private String platform;
    /**
     * 浏览器名称：navigator.appName
     */
    private String browserType;
    /**
     *  操作的用户id日志
     **/
    private String operatorId;
    private Integer startPage;
    private Integer pageSize;
    private T data;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonRequest{" +
                "platform='" + platform + '\'' +
                ", browserType='" + browserType + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", startPage=" + startPage +
                ", pageSize=" + pageSize +
                ", data=" + data +
                '}';
    }
}
